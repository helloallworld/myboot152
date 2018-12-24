package com.wumin.boot152.abstract_factory;

/**
 * 生产一级品类的  产品A  和  产品B  的工厂实现类
 */
public class ConcreteCreator1 extends Creator {

    public ConcreteCreator1(){
        System.out.println("获取一级生产工厂");
    }
    //生产一级品类的  产品A
    @Override
    public AbstractProductA getProductA() {
        return new ConcreteProductA1();
    }

    //生产一级品类的  产品B
    @Override
    public AbstractProductB getProductB() {
        return new ConcreteProductB1();
    }
}
