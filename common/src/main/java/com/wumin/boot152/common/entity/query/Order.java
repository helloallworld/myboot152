package com.wumin.boot152.common.entity.query;

/**
 * Created by liuyi on 2016/12/21.
 */
public class Order {
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    private String field;
    private String direction = Order.DESC;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        if (Order.DESC.equalsIgnoreCase(direction)) {
            this.direction = Order.DESC;
        } else {
            this.direction = Order.ASC;
        }
    }
}
