package com.wumin.boot152.nginxTest.scheduled;

import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
@Component
public class TimeTaskExecutor {
    public void start(){
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MyScheduledExecutor job1=new MyScheduledExecutor("job1");
                job1.run();
            }
        },0,5000);
        Timer timer2=new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                MyScheduledExecutor job2=new MyScheduledExecutor("job2");
                job2.run();
            }
        },0,2000);
    }
}
