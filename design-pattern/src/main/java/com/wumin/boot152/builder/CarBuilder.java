package com.wumin.boot152.builder;

import java.util.ArrayList;

public abstract class CarBuilder {
    //组装顺序写入
    public abstract void setSequence(ArrayList<String> sequence);
    //组装顺序完毕，获取CarModel
    public abstract CarModel getCarModel();
}
