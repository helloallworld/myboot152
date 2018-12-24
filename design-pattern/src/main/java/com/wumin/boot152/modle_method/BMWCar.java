package com.wumin.boot152.modle_method;

public class BMWCar extends CarMethod {
    public BMWCar(){
        System.out.println("本车为BMW");
    }
    @Override
    public void start() {
        System.out.println("BMW启动");
    }

    @Override
    public void stop() {
        System.out.println("BMW停止");
    }

    @Override
    public void engine() {
        System.out.println("BMW 汽油发动机运作");
    }

    @Override
    public void light() {
        System.out.println("BMW 停止");
    }
}
