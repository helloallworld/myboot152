package com.wumin.boot152.strategy;


/**
 * 策略模式（关注的是父类方法的不同具体实现）
 *
 * 1.策略模式与工厂模式区别：
 *                          a.工厂模式传的是XXX.class，策略模式传的是XXX 对象
 *                          b.工厂模式针对的是对象的建立，策略模式针对的是对象的具体方法执行哪个逻辑
 * 2.策略模式与代理模式的区别：
 *                            a.代理模式中代理类需要实现被代理类，
 *                              策略模式中封装类与策略类没有关系。
 *                            b.代理模式针对的是具体方法执行前后的操作，
 *                              策略模式针对的是具体执行哪个子类的方法
 *
 */
public class Client {
    public static void main(String[] args) {
        Context contextA=new Context(new StrategyA());
        contextA.method();
        Context contextB=new Context(new StrategyB());
        contextB.method();
    }
}
