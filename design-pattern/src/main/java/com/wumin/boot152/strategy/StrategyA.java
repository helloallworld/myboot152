package com.wumin.boot152.strategy;

/**
 * 策略实现类A：实现具体的A的运算方法
 */
public class StrategyA implements Strategy {
    @Override
    public void method() {
        System.out.println("StrategyA中的运算方法");
    }
}
