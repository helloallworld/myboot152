package com.wumin.boot152.proxy.static_proxy;

import org.apache.commons.lang3.StringUtils;

/**
 * 具体主题角色：具体业务实现，被代理者
 */
public class RealSubject extends Subject {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public void login(String name, String password) {
        this.name=name;
        System.out.println(name+"登陆");
    }

    @Override
    public void run() {
        System.out.println(this.name + "启动");
    }

    @Override
    public void doSomething() {
        System.out.println(this.name+"一顿操作猛如虎");
    }

    @Override
    public void logout() {
        System.out.println(this.name+"退出");
    }
}
