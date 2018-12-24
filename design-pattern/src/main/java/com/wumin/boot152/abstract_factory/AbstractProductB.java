package com.wumin.boot152.abstract_factory;

/**
 * 产品B 的接口
 *
 * 产品B 有几个 等级的线（如：一线，二线）就有几个实现
 */
public abstract class AbstractProductB {
    public void  method(){
        System.out.println("所有产品 B 的共有方法");
    }

    public abstract void methodDeff();
}
