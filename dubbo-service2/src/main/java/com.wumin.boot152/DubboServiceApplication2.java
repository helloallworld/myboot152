package com.wumin.boot152;

import com.wumin.boot152.dubbo.zipkin.adapters.EnableDubboTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboTrace
public class DubboServiceApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(DubboServiceApplication2.class,args);
    }
}
