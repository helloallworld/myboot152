package com.wumin.boot152.aopTest;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用注解配置springboot会自动加载，建议使用xml来配置这些bean,这样打断点可以更好的看到具体过程
 */
@Configuration
public class Config {

    @Bean(name="testAdvisor")
    public TestAdvisor getTestAdvisor(){
        TestAdvisor testAdvisor=new TestAdvisor();
        return testAdvisor;
    }
    @Bean(name="testTarget")
    public TestTarget getTestTarget(){
        TestTarget testTarge=new TestTarget();
        return testTarge;
    }
    @Bean(name="testAop")
    public ProxyFactoryBean testAop(){
        ProxyFactoryBean proxyFactoryBean=new ProxyFactoryBean();
        proxyFactoryBean.setTargetName("testTarget");
        proxyFactoryBean.setInterceptorNames("testAdvisor");
        return proxyFactoryBean;
    }
}
