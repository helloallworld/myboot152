package com.wumin.boot152.common.enums;

public enum  EnumOnsProducerTag {
    TestTag("testTag","Ons测试tag"),
    TestMapTag("testMapTag","Ons测试tag,messageBody为map");
    private String tag;
    private String description;
    EnumOnsProducerTag(String tag,String description){
        this.tag=tag;
        this.description=description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
