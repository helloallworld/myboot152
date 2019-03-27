package com.wumin.boot152.nginxTest.scheduled;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledExecutorService占用指定数量线程池用来执行定时任务（任务间不冲突，第一个任务没执行完，第二个任务也能正常执行，多个线程）
 * initialDelay:第一次延迟时间
 * period：间隔时间
 *
 * Timer类只能启动一个线程，如果向他提交多个task，当某个task耗时过长时，其他task到了执行时间还是会等待之前的task执行完毕
 * 也可启动多个Timer
 */
@Component
public class TaskExecutor {
    ScheduledExecutorService service= Executors.newScheduledThreadPool(10);
    public void start(){
        service.scheduleAtFixedRate(new MyScheduledExecutor("job1"),0,10, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(new MyScheduledExecutor("job2"),0,5,TimeUnit.SECONDS);
    }
}
