package com.wumin.boot152.modle_method;

/**
 * 模板方法模式
 *
 * 汽车模板抽象类
 *
 * 抽象类不需要管 start ,stop ,engine 方法怎么实现
 * 当 run 方法为CarMethod 所有的子类都一样的业务逻辑时，将 run 方法提到  模板抽象类  中
 *
 */
public abstract class CarMethod {
    public abstract void start();
    public abstract void stop();
    public abstract void engine();
    public abstract void light();
    public void run(){
        this.start();
        this.engine();
        this.light();
        this.stop();
    }

}
