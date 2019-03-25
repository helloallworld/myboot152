package com.wumin.boot152.nginxTest;

import com.wumin.boot152.nginxTest.poiutil.POIImportExcelUtil;

public class ExcelImportTest {
    public static void main(String[] args) {
        String file="d:/importTest.xlsx";
       Object object= POIImportExcelUtil.readExcelToObj(file);
       System.out.println(object);
    }
}
