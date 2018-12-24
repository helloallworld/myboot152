package com.wumin.boot152.single;

/**
 * 单例模式
 * 1.构造方法私有化
 * 2.提供一个public的获取Emperor的方法
 * 3.保证该public的方法返回的Emperor为同一个对象
 */
public class Emperor {
    private static final Emperor EMPEROR=new Emperor();
    private Emperor(){
        //构造方法私有化
    }
    public static Emperor getInstance(){
        return EMPEROR;
    }

}
