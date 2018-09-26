package com.wumin.boot152;

import com.wumin.boot152.dubbo.zipkin.adapters.EnableDubboTrace;
import com.wumin.boot152.service.ClientServiceImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DubboClientApplication {
    public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        ConfigurableApplicationContext run = SpringApplication.run(DubboClientApplication.class, args);
        ClientServiceImp c =run.getBean(ClientServiceImp.class);
        c.printName("wumin");

    }
}
