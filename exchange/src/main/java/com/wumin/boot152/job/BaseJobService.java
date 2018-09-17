package com.wumin.boot152.job;

import com.wumin.boot152.common.redis.template.RedisClientTemplate;
import com.wumin.boot152.common.util.DateUtils;
import com.wumin.boot152.redisson.RedissLockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * JOB基础类，主要为 集群环境 定时任务加锁使用
 */
@Service
public class BaseJobService {
    public Logger logger = LoggerFactory.getLogger(BaseJobService.class);
    @Resource
    private RedisClientTemplate redisClientTemplate;

    /**
     * @param @return 参数
     * @return boolean 返回类型 true 已占用; false 未占用
     * @throws
     * @Title: lockByRedis
     * @Description: TODO(集群环境调度, 返回false, 不执行此次操作)
     */
    public boolean lockByRedis(String key) {
        //加锁
        RedissLockUtil.lock("Redisson:" + key);
        // 1为已占用,0 未占用
        boolean value = redisClientTemplate.exists(key, 1);
        if (value) {
            return false;
        } else {
            redisClientTemplate.setex(key, 1, DateUtils.getRedisExpireNextDay().intValue() / 1000 + 1, 1);
            return true;
        }
    }

    /**
     * @param @param key 参数
     * @return void 返回类型
     * @throws
     * @Title: resetRedis
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public void resetRedis(String key) {
        //释放锁
        RedissLockUtil.unlock("Redisson:" + key);
    }
}
