package com.wumin.boot152.factory;

/**
 * 工厂接口实现类
 *
 * 1 只有Product的子类能被本工厂生成
 * 2 只需要知道需要的是那种具体产品就能获取，不需要关心具体产品怎么来的
 */
public class ConcreteCreator extends Creator{
    @Override
    public <T extends Product> T createProduct(Class<T> c) {
        Product product=null;
        try {
            product=(Product)Class.forName(c.getName()).newInstance();
            System.out.println("创建产品:"+c.getName());
        } catch (Exception e) {
            System.out.println("创建异常");
        }
        return (T) product;
    }
}
