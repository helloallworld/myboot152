package com.wumin.boot152.nginxTest.enums;

/**
 * 版权所有：中化信息技术有限公司 保留所有权利
 * 创建日期: 2019-02-21 10:26
 * 创建作者：BIN.CHEN
 * 文件名称：
 * 版本： dev
 **/
public enum EnumColumnIndex {
    COLUMN_A(0,"column_a"),
    COLUMN_B(1,"column_b"),
    COLUMN_C(2,"column_c"),
    COLUMN_D(3,"column_d"),
    COLUMN_E(4,"column_e"),
    COLUMN_F(5,"column_f"),
    COLUMN_G(6,"column_g"),
    COLUMN_H(7,"column_h"),
    COLUMN_I(8,"column_i"),
    COLUMN_J(9,"column_j"),
    COLUMN_K(10,"column_k"),
    COLUMN_L(11,"column_l"),
    COLUMN_M(12,"column_m"),
    COLUMN_N(13,"column_n"),
    COLUMN_O(14,"column_o"),
    COLUMN_P(15,"column_p"),
    COLUMN_Q(16,"column_q");

    private Integer columnIndex;
    private String columnName;

    EnumColumnIndex(Integer columnIndex, String columnName) {
        this.columnIndex = columnIndex;
        this.columnName = columnName;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public static EnumColumnIndex findByValue(Integer columnIndex){
        for (EnumColumnIndex c : EnumColumnIndex.values()) {
            if (c.getColumnIndex().equals(columnIndex)) {
                return c;
            }
        }
        return null;
    }

}
