package com.wumin.boot152.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wumin.boot152.entity.Student;

// 注册为 Dubbo 服务
@Service(version = "1.0.0")
public class StudentDubboServiceImp implements StudentDubboService {
    @Override
    public Student getStudent() {
        System.out.println("getStudent in service");
        Student student=new Student();
        student.setAge(22);
        student.setName("biubiubiu");
        return student;
    }
}
