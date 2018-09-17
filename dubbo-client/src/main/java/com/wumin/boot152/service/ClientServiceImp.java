package com.wumin.boot152.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wumin.boot152.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class ClientServiceImp {
    @Reference(version = "1.0.0")
    DubboDemoService dubboDemoService;
    @Reference(version = "1.0.0")
    StudentDubboService studentDubboService;
    public void printName(String name){
        System.out.println(name+" in client");
      String str=  dubboDemoService.sayHello(name);
      System.out.println(str +" in client");
      Student student=studentDubboService.getStudent();
      System.out.println(student.getName()+","+student.getAge());
    }
}
