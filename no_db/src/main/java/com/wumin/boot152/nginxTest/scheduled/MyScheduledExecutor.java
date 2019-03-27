package com.wumin.boot152.nginxTest.scheduled;

import org.apache.commons.lang3.StringUtils;

public class MyScheduledExecutor implements  Runnable {
    private String name;
    public MyScheduledExecutor(String name){
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        if(StringUtils.equals(name,"job1")){
            System.out.println("job1 sleep");
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("job1 sleep over");
        }
        System.out.println(name+"执行");
    }
}
