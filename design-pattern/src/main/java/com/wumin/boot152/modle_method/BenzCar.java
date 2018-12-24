package com.wumin.boot152.modle_method;

public class BenzCar extends CarMethod {
    public BenzCar(){
        System.out.println("本车为Benz");
    }
    @Override
    public void start() {
        System.out.println("Benz 启动");
    }

    @Override
    public void stop() {
        System.out.println("Benz 停止");
    }

    @Override
    public void engine() {
        System.out.println("Benz 电力发动机运作");
    }

    @Override
    public void light() {
        System.out.println("Benz 亮灯");
    }
}
