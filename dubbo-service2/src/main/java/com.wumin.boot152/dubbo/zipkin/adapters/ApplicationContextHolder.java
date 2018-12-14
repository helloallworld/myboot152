package com.wumin.boot152.dubbo.zipkin.adapters;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        setCtx(ctx);
    }

    private static void setCtx(ApplicationContext ctx) {
        applicationContext = ctx;
    }

    public static <T> Object getBean(Class requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static Object getBean(String classStr) {
        return applicationContext.getBean(classStr);
    }
}
