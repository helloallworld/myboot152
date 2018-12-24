package com.wumin.boot152.abstract_factory;

/**
 * 二线产品  工厂创建类
 */
public class ConcreteCreator2 extends Creator{
    public ConcreteCreator2(){
        System.out.println("获取二线产品工厂");
    }
    @Override
    public AbstractProductA getProductA() {
        return new ConcreteProductA2();
    }

    @Override
    public AbstractProductB getProductB() {
        return new ConcreteProductb2();
    }
}
