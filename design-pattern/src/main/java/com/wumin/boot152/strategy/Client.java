package com.wumin.boot152.strategy;


/**
 * 策略模式（关注的是父类方法的不同具体实现）
 * 策略模式是一种行为型模式，它定义一系列的算法，将每一种算法封装起来并可以相互替换使用，
 * 策略模式让算法独立于使用它的客户端应用而独立变化。策略模式适合解决当程序出现多个不同分支，
 * 而且每个分支的逻辑还比较复杂的场景。
 *
 * 1.策略模式与工厂模式区别：
 *                          a.工厂模式传的是XXX.class，策略模式传的是XXX 对象
 *                          b.工厂模式针对的是对象的建立，策略模式针对的是对象的具体方法执行哪个逻辑
 * 2.策略模式 与 代理模式 的区别：
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
