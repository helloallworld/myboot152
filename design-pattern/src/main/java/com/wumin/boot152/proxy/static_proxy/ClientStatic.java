package com.wumin.boot152.proxy.static_proxy;

/**
 * 静态代理模式
 *
 * 静态代理中 Proxy和RealSubject 都是 Subject 的子类
 *
 *      即：静态代理在实现阶段就知道要  被代理的是 RealSubject，
 *          而动态代理在实现阶段不关心代理谁，在运行阶段才会指定代理哪个对象
 *
 * 可以在代理类（Proxy）中 决定是否调用RealSubject 的realMethod
 *
 * 代理类不仅仅可以实现主题接口，也可以实现其他接口完成不同的任务，
 * 而且代理的目的是在目标对象方法的基础上作增强，这种增强的本质通常就是对目标对象的方法进行拦截和过滤。
 *
 * 代理模式：为所需要的    真正业务操作   进行前后处理
 *          即：before();
 *              realMethod();
 *              after();
 *              只有realMethod 是被代理的类的业务逻辑，
 *              before 和 after 是代理类在调用realMethod 前后的一些处理
 */
public class ClientStatic {
    public static void main(String[] args) {
        RealSubject realSubject=new RealSubject();
        Proxy proxy=new Proxy(realSubject);
        proxy.login("aaaa","11111");
        proxy.run();
        proxy.doSomething();
        proxy.logout();
    }
}
