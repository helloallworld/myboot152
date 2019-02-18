package com.wumin.boot152.nginxTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NginxTestApplication {
    private Logger logge = LoggerFactory.getLogger(NginxTestApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(NginxTestApplication.class, args);
    }
}
