package com.wumin.boot152.builder;

/**
 * Product产品 Benz产品类
 */
public class BenzModel extends CarModel {
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
        System.out.println("Benz 能量");
    }

    @Override
    public void light() {
        System.out.println("Benz 开灯");
    }
}
