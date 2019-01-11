package com.wumin.boot152.aopTest;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

/**
 * 切面：（也叫aspect）将切点和通知绑定起来
 */
public class TestAdvisor implements PointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return new TestPointcut();
    }

    @Override
    public Advice getAdvice() {
        return new TestAfterAdvice();
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
