package com.wumin.boot152.dubbo.zipkin.adapters;

public enum StatusEnum {
    /**
     * 正常
     */
    OK(200, "OK"),
    /**
     * 错误
     */
    ERROR(500, "ERROR");
    private int code;
    private String desc;

    private StatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public int getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
}
