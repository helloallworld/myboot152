package com.wumin.boot152.builder;


import java.util.ArrayList;

public class BenzBuilder extends CarBuilder {
    private BenzModel benzModel=new BenzModel();
    @Override
    public void setSequence(ArrayList<String> sequence) {
        benzModel.setSequence(sequence);
    }

    @Override
    public CarModel getCarModel() {
        return this.benzModel;
    }
}
