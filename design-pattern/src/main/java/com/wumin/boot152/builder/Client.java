package com.wumin.boot152.builder;



/**
 * 建造者模式（也叫生成器模式）
 *建造者适合用于构建复杂对象，它可以将创建和表示分离，使我们的代码可读性更好，更易于维护。
 *
 * 将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
 * 1.Product产品类 : 通常是实现了模板方法模式，也就是有模板方法和基本方法
 * （本例中：BenzModel,BMWModel）
 * 2.Builder抽象建造者  规范产品的组建，一般是由子类实现。
 * （本例中：CarBuilder）
 * 3.ConcreteBuilder具体建造者  实现抽象类定义的所有方法，并且返回一个组建好的对象。
 * （本例中：BenzBuilder，BMWBuilder）
 * 4.Director导演类  负责安排已有模块的顺序，然后告诉Builder开始建造。
 * 导演类起到封装的作用，避免高层模块深入到建造者内部的实现类。在建造者模式比较庞大时，导演类可以有多个。
 * （本例中：BenzDirector1，BenzDirector2）
 *
 * 理解：建造者模式可以理解为我需要一个产品，但是产品的组装顺序是需要可变的（由Director导演类决定产品顺序）。
 *      Director导演类中 声明对应的 Builder建造者，并给 Builder建造者 相应的产品组装顺序 并返回对应的 产品
 */
public class Client {
    public static void main(String[] args) {
        //获取第一个Director类
       BenzDirector1 benzDirector1=new BenzDirector1();
       benzDirector1.getBenzModel().run();
       //获取第二个Director类
       BenzDirector2 benzDirector2=new BenzDirector2();
       benzDirector2.getBenzModel().run();
    }
}
