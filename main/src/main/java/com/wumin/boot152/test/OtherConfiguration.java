package com.wumin.boot152.test;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnBean({Student.class})
public class OtherConfiguration {
    private final Student student;
    public OtherConfiguration(Student student){
        this.student=student;
        System.out.println("in OtherConfiguration 有参构造"+student.getName());
    }

    @Bean
    public Teacher teacher(Student student){
        Teacher teacher=new Teacher(student);
        System.out.println("in create teacher bean"+student.getName());
        teacher.setStudent(student);
        return teacher;
    }
}
