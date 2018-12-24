package com.wumin.boot152.modle_method;


/**
 * 模板方法模式
 *
 * 抽象类不需要管 start ,stop ,engine 方法怎么实现（具体实现方法由子类去完成）
 * 当 run 方法为CarMethod 所有的子类都一样的业务逻辑时，将 run 方法提到  模板抽象类  中
 *
 */
public class Client {
    public static void main(String[] args) {
        CarMethod carBenz=new BenzCar();
        carBenz.run();

        CarMethod carBMW=new BMWCar();
        carBMW.run();
    }
}
