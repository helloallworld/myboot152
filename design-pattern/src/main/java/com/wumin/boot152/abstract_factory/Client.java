package com.wumin.boot152.abstract_factory;

/**
 * 抽象工厂模式
 *
 * 两种产品， 两种标准
 *
 * 生产 ProductA 和 ProductB  （理解成两种产品）
 *
 * 但是有不同的具体工厂实现
 * 不同的工厂实现对 ProductA 和 ProductB 处理不一样（理解为对产品的要求不一样）
 *
 * 1 有几种产品要求(一级，二级)就有几个工厂实现（本例中：ConcreteCreator1,ConcreteCreator2）
 * 2 有几个产品,几种产品要求 就有几个产品的生产方法
 * (本例中: ConcreteProductA1，ConcreteProductA2，ConcreteProductB1，ConcreteProductB2)
 *
 *
 *      抽象工厂  --------  返回不同产品的工厂实现
 *      不同的产品要求（一级，二级）对抽象工厂不同的实现 ------- 一级工厂  和  二级的工厂
 *      一级工厂 ------- 生产一级的 产品A 和 产品 B
 *      二级工厂 ------- 生产二级的 产品A 和 产品 B
 */
public class Client {
    public static void main(String[] args) {
        //一级产品工厂
        Creator creator1=new ConcreteCreator1();
        //一级 产品A
        AbstractProductA productA1=creator1.getProductA();
        productA1.method();
        productA1.methodDeff();
        //一级 产品B
        AbstractProductB productB1=creator1.getProductB();
        productB1.method();
        productB1.methodDeff();

        //二级产品工厂
        Creator creator2=new ConcreteCreator2();
        //二级 产品A
        AbstractProductA productA2=creator2.getProductA();
        productA2.method();
        productA2.methodDeff();
        //二级 产品B
        AbstractProductB productB2=creator2.getProductB();
        productB2.method();
        productB2.methodDeff();
    }
}
