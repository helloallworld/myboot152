package com.wumin.boot152.proxy.static_proxy;

import org.apache.commons.lang3.StringUtils;

/**
 * 代理类
 *
 * 它负责对真实角色的应用，把所有抽象主题类定义的方法限制委托给真实主题角色实现，
 * 并且在真实主题角色处理完毕前后做预处理和善后处理工作。
 *
 * 代理类也可决定是否执行
 */
public class Proxy extends Subject{
    private RealSubject realSubject;
    public Proxy(RealSubject realSubject){
        this.realSubject=realSubject;
    }
    @Override
    public void login(String name, String password) {
        //可在代理前后做些数据处理
        System.out.println("============开始代理===========");
        if(!StringUtils.equals(password,"111111")){
            System.out.println("********密码错误********");
        }else {
            realSubject.login(name, password);
        }
    }

    @Override
    public void run() {
        if(StringUtils.isNotBlank(realSubject.getName())){
            realSubject.run();
        }else {
            System.out.println("冒的名字，玩球");
        }

    }

    @Override
    public void doSomething() {
        realSubject.doSomething();
    }

    @Override
    public void logout() {
        realSubject.logout();
        System.out.println("代理结束");
    }
}
