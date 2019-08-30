package com.wumin.boot152.nginxTest;

import com.wumin.boot152.nginxTest.scheduled.TaskExecutor;
import com.wumin.boot152.nginxTest.scheduled.TimeTaskExecutor;
import com.wumin.boot152.nginxTest.services.UuidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@ServletComponentScan
public class NginxTestApplication {
    private Logger logge = LoggerFactory.getLogger(NginxTestApplication.class);
    public static void main(String[] args) {
        ApplicationContext context=SpringApplication.run(NginxTestApplication.class, args);
//        TaskExecutor taskExecutor=context.getBean(TaskExecutor.class);
//        taskExecutor.start();
//        TimeTaskExecutor timeTaskExecutor=context.getBean(TimeTaskExecutor.class);
//        timeTaskExecutor.start();

        context.getBean(UuidService.class).getUuids();
    }
}
