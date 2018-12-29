package com.wumin.boot152.strategy;

/**
 * 策略实现类B：实现具体的B的运算方法
 */
public class StrategyB implements Strategy {
    @Override
    public void method() {
        System.out.println("StrategyB中的具体运算方法");
    }
}
