package com.wumin.boot152.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.wumin.boot152.dubbo.zipkin.adapters.EnableDubboTrace;

// 注册为 Dubbo 服务
@Service(version = "1.0.0")
public class DubboDemoServiceImp implements DubboDemoService {
    @Reference(version = "1.0.0")
    DubboDemoService2 dubboDemoService2;
    @Override
    public String sayHello(String name) {
        System.out.println(name+" in service");
        String str=dubboDemoService2.sayHello2(name);
        System.out.println("in dubboDemoService use dubboDemoService2 return:"+str);
        return "hello:"+name;
    }
}
