package com.wumin.boot152.proxy.dynamic_proxy;


/**
 * 具体主题角色：具体业务实现，被代理者
 */
public class RealSubject implements Subject {
    private String name;
    public RealSubject(){
        super();
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void login(String name, String password) {
        this.name=name;
        System.out.println(name+"登陆");
    }

}
