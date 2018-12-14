package com.wumin.boot152.service;

import com.alibaba.dubbo.config.annotation.Service;

@Service(version = "1.0.0")
public class DubboDemoService2Imp implements DubboDemoService2{

    @Override
    public String sayHello2(String name) {
        System.out.println(name+" in service2");
        return "hello2:"+name;
    }
}
