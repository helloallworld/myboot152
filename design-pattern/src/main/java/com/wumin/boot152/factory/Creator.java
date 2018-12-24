package com.wumin.boot152.factory;

/**
 * 工厂接口
 *
 * 1 只有Product的子类能被本工厂生成
 * 2 只需要知道需要的是那种具体产品就能获取，不需要关心具体产品怎么来的
 *
 */
public  abstract class Creator {
    public abstract  <T extends Product> T createProduct(Class<T> c);
}
