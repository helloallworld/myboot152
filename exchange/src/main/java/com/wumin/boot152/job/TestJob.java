package com.wumin.boot152.job;

import com.wumin.boot152.common.util.DateUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class TestJob extends BaseJobService {
    @Resource
    MongoTemplate mongoTemplate;
    //定时任务执行时间
    @Scheduled(cron = "${testJob}")
    public void beginTestJob(){
    String key= DateUtils.format(new Date(), "yyyyMMdd") + ":testJob";
    if(lockByRedis(key)){
        //业务处理

    }
    //释放锁
        resetRedis(key);
    }
}
