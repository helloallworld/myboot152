package com.wumin.boot152.aopTest;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * 通知类型：包括before ,after ,around等（表示在切点执行的哪个时候调用通知，例如：before,表示在切点方法运行前调用定义的通知）
 */
public class TestAfterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("after :"+target.getClass().getName()+"."+method.getName());
    }
}
