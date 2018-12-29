package com.wumin.boot152.strategy;

/**
 * 策略模式的封装类：将具体策略传入，
 */
public class Context {
    private Strategy strategy;
    public Context(Strategy strategy){
        this.strategy=strategy;
    }
    public void method(){
        this.strategy.method();
    }
}
