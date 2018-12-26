package com.wumin.boot152.proxy.static_proxy;

/**
 * 抽象主题角色
 */
public abstract class Subject {
    public abstract void login(String name,String password);
    public abstract void run();
    public abstract void doSomething();
    public abstract void logout();
}
