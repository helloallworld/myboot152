package com.wumin.boot152;

import com.wumin.boot152.aopTest.TestTarget;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
       ApplicationContext context= SpringApplication.run(Application.class, args);
       //System.out.println(context.getBean("testAop").getClass());
        TestTarget targe=(TestTarget) context.getBean("testTarget");
        targe.test();
        System.out.println("-----------------");
        targe.test("asdfasdgds");
        System.out.println("-----------------");
        targe.test2();
    }
}
