package com.wumin.boot152;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * springboot启动类
 */
@SpringBootApplication
@MapperScan("com.wumin.boot152.common.mapper")
public class MainApplication {
    private Logger logge = LoggerFactory.getLogger(MainApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
