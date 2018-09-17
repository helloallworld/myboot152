package com.wumin.boot152.common.enums;

/**
 * Created by zhujianbo on 2018/8/23.
 */
public enum EnumAppKey {

    ERP("sinochem.erp.key","5810B50A084419630624EF74643D1A0D");

    EnumAppKey(String key, String value){
        this.key = key;
        this.value = value;
    }


    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static EnumAppKey findByKey(String key) {
        for (EnumAppKey c : EnumAppKey.values()) {
            if (c.getKey().equals(key)) {
                return c;
            }
        }
        return null;
    }

}
