package com.wumin.boot152.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * 动态代理是在实现阶段不用关心代理谁，而在运行阶段才指定代理哪一个对象。相对来说，自己写代理类的方式就是静态代理。
 *
 * 与静态代理相比，动态代理的  代理类MyInvocationHandler    在代码运行前并不知道自己具体代理的是哪个类
 * MyInvocationHandler  实现了 InvocationHandler（JAVA的动态代理接口）
 */
public class ClientDynamic {
    public static void main(String[] args) {
        Subject realSubject=new RealSubject();
        //获取具体的动态代理类
        InvocationHandler invocationHandler=new MyInvocationHandler(realSubject);
        //获取一个动态代理对象，所需参数：被代理类的classLoader,interfaces,具体的动态代理类
        Subject subjectProxy=(Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(),realSubject.getClass().getInterfaces(),invocationHandler);

        subjectProxy.login("wumin","111111");

    }
}
