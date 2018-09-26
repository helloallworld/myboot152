package com.wumin.boot152.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wumin.boot152.dubbo.zipkin.adapters.EnableDubboTrace;

// 注册为 Dubbo 服务
@Service(version = "1.0.0")
public class DubboDemoServiceImp implements DubboDemoService {
    @Override
    public String sayHello(String name) {
        System.out.println(name+" in service");
        return "hello:"+name;
    }
}
