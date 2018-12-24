package com.wumin.boot152.factory;

/**
 * 产品接口
 */
public abstract class Product {
    public void method(){
        //所有产品共有的方法(即：产品共性)
        System.out.println("所有产品共有的方法");
    }
    public abstract void methodDeff();
}
