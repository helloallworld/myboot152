package com.wumin.boot152.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wumin.boot152.service.ClientServiceImp;
import com.wumin.boot152.service.DubboDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client")
public class DubboClientController {
//    @Reference(version = "1.0.0")
//    DubboDemoService myDubboDemoService;
    @Autowired
ClientServiceImp clientServiceImp;
    @GetMapping("demo")
    public void demo(){
        System.out.println("in controller");
      //  myDubboDemoService.sayHello("wumin");
        clientServiceImp.printName("aaa");
    }

//    @GetMapping("service")
//    public void service(){
//        clientServiceImp.printName("service");
//    }
}
