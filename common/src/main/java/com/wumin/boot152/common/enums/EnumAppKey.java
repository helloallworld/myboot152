package com.wumin.boot152.common.enums;

/**
 *
 */
public enum EnumAppKey {

    ERP("aaa.key","sdafsdfasdgfadsf");

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
