package com.wumin.boot152.redisson;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
  public class RedisDistributionLockPlus {

              /**
   6     * 加锁超时时间，单位毫秒， 即：加锁时间内执行完操作，如果未完成会有并发现象
   7     */
              private static final long DEFAULT_LOCK_TIMEOUT = 30;

              private static final long TIME_SECONDS_FIVE = 5 ;

             /**
  13     * 每个key的过期时间 {@link LockContent}
  14     */
             private Map<String, LockContent> lockContentMap = new ConcurrentHashMap<>(512);
    /**
  18     * redis执行成功的返回
  19     */
             private static final Long EXEC_SUCCESS = 1L;

             /**
  23     * 获取锁lua脚本， k1：获锁key, k2：续约耗时key, arg1:requestId，arg2：超时时间
  24     */
             private static final String LOCK_SCRIPT = "if redis.call('exists', KEYS[2]) == 1 then ARGV[2] = math.floor(redis.call('get', KEYS[2]) + 10) end " +
                        "if redis.call('exists', KEYS[1]) == 0 then " +
                           "local t = redis.call('set', KEYS[1], ARGV[1], 'EX', ARGV[2]) " +
                           "for k, v in pairs(t) do " +
                             "if v == 'OK' then return tonumber(ARGV[2]) end " +
                           "end " +
                        "return 0 end";

             /**
  34     * 释放锁lua脚本, k1：获锁key, k2：续约耗时key, arg1:requestId，arg2：业务耗时 arg3: 业务开始设置的timeout
  35     */
             private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "local ctime = tonumber(ARGV[2]) " +
                        "local biz_timeout = tonumber(ARGV[3]) " +
                        "if ctime > 0 then  " +
                           "if redis.call('exists', KEYS[2]) == 1 then " +
                               "local avg_time = redis.call('get', KEYS[2]) " +
                               "avg_time = (tonumber(avg_time) * 8 + ctime * 2)/10 " +
                               "if avg_time >= biz_timeout - 5 then redis.call('set', KEYS[2], avg_time, 'EX', 24*60*60) " +
                               "else redis.call('del', KEYS[2]) end " +
                           "elseif ctime > biz_timeout -5 then redis.call('set', KEYS[2], ARGV[2], 'EX', 24*60*60) end " +
                        "end " +
                        "return redis.call('del', KEYS[1]) " +
                        "else return 0 end";
     /**
  50     * 续约lua脚本
  51     */
             private static final String RENEW_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('expire', KEYS[1], ARGV[2]) else return 0 end";


             private final StringRedisTemplate redisTemplate;

             public RedisDistributionLockPlus(StringRedisTemplate redisTemplate) {
                this.redisTemplate = redisTemplate;
                ScheduleTask task = new ScheduleTask(this, lockContentMap);
                // 启动定时任务
                ScheduleExecutor.schedule(task, 1, 1, TimeUnit.SECONDS);
            }

             /**
  65     * 加锁
  66     * 取到锁加锁，取不到锁一直等待知道获得锁
  67     *
  68     * @param lockKey
  69     * @param requestId 全局唯一
  70     * @param expire   锁过期时间, 单位秒
  71     * @return
  72     */
            public boolean lock(String lockKey, String requestId, long expire) {
                log.info("开始执行加锁, lockKey ={}, requestId={}", lockKey, requestId);
                for (; ; ) {
                        // 判断是否已经有线程持有锁，减少redis的压力
                        LockContent lockContentOld = lockContentMap.get(lockKey);
                        boolean unLocked = null == lockContentOld;
                        // 如果没有被锁，就获取锁
                        if (unLocked) {
                                long startTime = System.currentTimeMillis();
                                // 计算超时时间
                                long bizExpire = expire == 0L ? DEFAULT_LOCK_TIMEOUT : expire;
                                String lockKeyRenew = lockKey + "_renew";

                                RedisScript<Long> script = RedisScript.of(LOCK_SCRIPT, Long.class);
                                List<String> keys = new ArrayList<>();
                                keys.add(lockKey);
                                keys.add(lockKeyRenew);
                                Long lockExpire = redisTemplate.execute(script, keys, requestId, Long.toString(bizExpire));
                                if (null != lockExpire && lockExpire > 0) {
                                       // 将锁放入map
                                       LockContent lockContent = new LockContent();
                                       lockContent.setStartTime(startTime);
                                       lockContent.setLockExpire(lockExpire);
                                       lockContent.setExpireTime(startTime + lockExpire * 1000);
                                       lockContent.setRequestId(requestId);
                                       lockContent.setThread(Thread.currentThread());
                                       lockContent.setBizExpire(bizExpire);
                                        lockContent.setLockCount(1);
                                        lockContentMap.put(lockKey, lockContent);
                                        log.info("加锁成功, lockKey ={}, requestId={}", lockKey, requestId);
                                        return true;
                                    }
                            }
                        // 重复获取锁，在线程池中由于线程复用，线程相等并不能确定是该线程的锁
                        if (Thread.currentThread() == lockContentOld.getThread()
                                  && requestId.equals(lockContentOld.getRequestId())){
                                // 计数 +1
                                lockContentOld.setLockCount(lockContentOld.getLockCount()+1);
                                return true;
                            }

                        // 如果被锁或获取锁失败，则等待100毫秒
                        try {
                                TimeUnit.MILLISECONDS.sleep(100);
                            } catch (InterruptedException e) {
                                // 这里用lombok 有问题
                                log.error("获取redis 锁失败, lockKey ={}, requestId={}", lockKey, requestId, e);
                                return false;
                            }
                    }
            }


            /**
 127     * 解锁
 128     *
 129     * @param lockKey
 130     * @param lockValue
 131     */
            public boolean unlock(String lockKey, String lockValue) {
                String lockKeyRenew = lockKey + "_renew";
                LockContent lockContent = lockContentMap.get(lockKey);

                long consumeTime;
                if (null == lockContent) {
                        consumeTime = 0L;
                    } else if (lockValue.equals(lockContent.getRequestId())) {
                        int lockCount = lockContent.getLockCount();
                        // 每次释放锁， 计数 -1，减到0时删除redis上的key
                        if (--lockCount > 0) {
                                lockContent.setLockCount(lockCount);
                                return false;
                            }
                        consumeTime = (System.currentTimeMillis() - lockContent.getStartTime()) / 1000;
                    } else {
                        log.info("释放锁失败，不是自己的锁。");
                        return false;
                    }

                // 删除已完成key，先删除本地缓存，减少redis压力, 分布式锁，只有一个，所以这里不加锁
                lockContentMap.remove(lockKey);

                RedisScript<Long> script = RedisScript.of(UNLOCK_SCRIPT, Long.class);
                List<String> keys = new ArrayList<>();
                keys.add(lockKey);
                keys.add(lockKeyRenew);

                Long result = redisTemplate.execute(script, keys, lockValue, Long.toString(consumeTime),
                161                Long.toString(lockContent.getBizExpire()));
                return EXEC_SUCCESS.equals(result);

            }

            /**
 167     * 续约
 168     *
 169     * @param lockKey
 170     * @param lockContent
 171     * @return true:续约成功，false:续约失败（1、续约期间执行完成，锁被释放 2、不是自己的锁，3、续约期间锁过期了（未解决））
 172     */
            public boolean renew(String lockKey, LockContent lockContent) {

                // 检测执行业务线程的状态
                Thread.State state = lockContent.getThread().getState();
                if (Thread.State.TERMINATED == state) {
                        log.info("执行业务的线程已终止,不再续约 lockKey ={}, lockContent={}", lockKey, lockContent);
                        return false;
                    }

                String requestId = lockContent.getRequestId();
                long timeOut = (lockContent.getExpireTime() - lockContent.getStartTime()) / 1000;

                RedisScript<Long> script = RedisScript.of(RENEW_SCRIPT, Long.class);
                List<String> keys = new ArrayList<>();
                keys.add(lockKey);

                Long result = redisTemplate.execute(script, keys, requestId, Long.toString(timeOut));
                log.info("续约结果，True成功，False失败 lockKey ={}, result={}", lockKey, EXEC_SUCCESS.equals(result));
                return EXEC_SUCCESS.equals(result);
            }


            static class ScheduleExecutor {

                public static void schedule(ScheduleTask task, long initialDelay, long period, TimeUnit unit) {
                        long delay = unit.toMillis(initialDelay);
                        long period_ = unit.toMillis(period);
                        // 定时执行
                        new Timer("Lock-Renew-Task").schedule(task, delay, period_);
                    }
    }

            static class ScheduleTask extends TimerTask {

                private final RedisDistributionLockPlus redisDistributionLock;
        private final Map<String, LockContent> lockContentMap;

                public ScheduleTask(RedisDistributionLockPlus redisDistributionLock, Map<String, LockContent> lockContentMap) {
                        this.redisDistributionLock = redisDistributionLock;
                        this.lockContentMap = lockContentMap;
                    }

                @Override
        public void run() {
                        if (lockContentMap.isEmpty()) {
                                return;
                            }
                        Set<Map.Entry<String, LockContent>> entries = lockContentMap.entrySet();
                        for (Map.Entry<String, LockContent> entry : entries) {
                                String lockKey = entry.getKey();
                                LockContent lockContent = entry.getValue();
                                long expireTime = lockContent.getExpireTime();
                                // 减少线程池中任务数量
                                if ((expireTime - System.currentTimeMillis())/ 1000 < TIME_SECONDS_FIVE) {
                                        //线程池异步续约
                                        ThreadPool.submit(() -> {
                                                boolean renew = redisDistributionLock.renew(lockKey, lockContent);
                                                if (renew) {
                                                        long expireTimeNew = lockContent.getStartTime() + (expireTime - lockContent.getStartTime()) * 2 - TIME_SECONDS_FIVE * 1000;
                                                        lockContent.setExpireTime(expireTimeNew);
                                                    } else {
                                                        // 续约失败，说明已经执行完 OR redis 出现问题
                                                        lockContentMap.remove(lockKey);
                                                    }
                                            });
                                    }
                            }
                    }
    }
}