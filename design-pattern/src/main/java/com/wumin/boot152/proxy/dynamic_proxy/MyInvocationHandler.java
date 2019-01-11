package com.wumin.boot152.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类
 */
public class MyInvocationHandler implements InvocationHandler {
    //被代理者
    Class cls=null;
    //被代理的实例
    Object obj=null;
    public MyInvocationHandler(Object obj){
        this.obj=obj;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ProxyUtils.before();
        Object result=method.invoke(this.obj,args);
        ProxyUtils.after();
        return result;
    }
}
