package com.wumin.boot152.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfiguration {

    @Bean
    public Student student(){
        return new Student();
    }
}
