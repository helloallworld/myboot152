package com.wumin.boot152.factory;

/**
 * 工厂方法模式
 * 同一种产品 两条标准线
 *
 * 1 只有Product的子类能被本工厂生成
 * 2 只需要知道需要的是那种具体产品就能获取，不需要关心具体产品怎么来的
 */
public class Client {
    public static void main(String[] args) {
        Creator creator=new ConcreteCreator();
        //创建第一种具体产品
        Product product=creator.createProduct(ConcreteProduct1.class);
        product.method();
        product.methodDeff();
        //创建第二种具体产品
        Product product2=creator.createProduct(ConcreteProduct2.class);
        product2.method();
        product2.methodDeff();
    }
}
