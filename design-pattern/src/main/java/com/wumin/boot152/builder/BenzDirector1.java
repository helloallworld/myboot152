package com.wumin.boot152.builder;


import java.util.ArrayList;

public class BenzDirector1 {
    private CarBuilder benzBuilder=new BenzBuilder();
    public CarModel getBenzModel(){
        ArrayList<String> sequence=new ArrayList<String>();
        sequence.add("start");
        sequence.add("engine");
        sequence.add("light");
        sequence.add("stop");
        benzBuilder.setSequence(sequence);
        return benzBuilder.getCarModel();
    }
}
