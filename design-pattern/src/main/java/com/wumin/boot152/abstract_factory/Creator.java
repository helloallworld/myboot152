package com.wumin.boot152.abstract_factory;

/**
 * 工厂接口
 * 生产 ProductA 和 ProductB  （理解成两种产品）
 *
 * 但是有不同的具体工厂实现
 * 不同的工厂实现对 ProductA 和 ProductB 处理不一样（理解为对产品的要求不一样）
 *
 * 1 有几个产品就有几个产品的生产方法
 *
 */
public abstract class Creator {
    //产品A
    public abstract AbstractProductA getProductA();
    //产品B
    public abstract AbstractProductB getProductB();
}
