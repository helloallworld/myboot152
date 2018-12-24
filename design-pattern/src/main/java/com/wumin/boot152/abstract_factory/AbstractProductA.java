package com.wumin.boot152.abstract_factory;

/**
 * 产品A 的接口
 *
 * 产品A有几个 等级的线（如：一线，二线）就有几个实现
 */
public abstract class AbstractProductA {
    public void  method(){
        System.out.println("所有产品 A 的共有方法");
    }

    public abstract void methodDeff();
}
