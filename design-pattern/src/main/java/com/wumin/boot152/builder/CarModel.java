package com.wumin.boot152.builder;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Product产品 抽象类
 */
public abstract class CarModel {
    private ArrayList<String> sequence=null;
    public abstract void start();
    public abstract void stop();
    public abstract void engine();
    public abstract void light();
    public void setSequence(ArrayList<String> sequence){
        this.sequence=sequence;
    }
    public void run(){
        for(String str:sequence){
            if(StringUtils.equals(str,"start")){
                this.start();
            }else if(StringUtils.equals(str,"stop")){
                this.stop();
            }else if(StringUtils.equals(str,"engine")){
                this.engine();
            }else if(StringUtils.equals(str,"light")){
                this.light();
            }
        }
    }
}
