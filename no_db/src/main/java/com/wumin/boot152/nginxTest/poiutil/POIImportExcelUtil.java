package com.wumin.boot152.nginxTest.poiutil;

import com.wumin.boot152.nginxTest.enums.EnumColumnIndex;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel导入工具类
 * 版权所有：中化信息技术有限公司 保留所有权利
 * 创建日期: 2019-02-21 10:03
 * 创建作者：BIN.CHEN
 * 文件名称：
 * 版本： dev
 **/
public class POIImportExcelUtil {

    /**
     * 读取excel数据
     *
     * @param path
     */
    public static Object readExcelToObj(String path) {
        Workbook wb = null;
        List<List<Map<String, Object>>> result = new ArrayList<>();
        try {
            wb = WorkbookFactory.create(new File(path));
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheetAt(i);
                //如果为空sheet，跳出此次循环
                if (sheet.getLastRowNum() == 0 && sheet.getPhysicalNumberOfRows() == 0) {
                    continue;
                }
                List<Map<String, Object>> arrayList = readExcel(sheet, 0, 0);
                result.add(arrayList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从流里面读出excel文件
     * @param file
     * @return
     */
    public static Object readExcelToObjByStream(MultipartFile file){
        Workbook wb = null;
        List<List<Map<String, Object>>> result = new ArrayList<>();
        try {
            wb = WorkbookFactory.create(file.getInputStream());
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheetAt(i);
                //如果为空sheet，跳出此次循环
                if (sheet.getLastRowNum() == 0 && sheet.getPhysicalNumberOfRows() == 0) {
                    continue;
                }
                List<Map<String, Object>> arrayList = readExcel(sheet, 2, 0);
                result.add(arrayList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取excel文件
     *
     * @param sheet
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine      去除最后读取的行
     */
    public static List<Map<String, Object>> readExcel(Sheet sheet, int startReadLine, int tailLine) {
        Row row = null;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {

            row = sheet.getRow(i);
            Map<String, Object> map = new HashMap<>();
            for (Cell c : row) {
                Object returnObj = "";

                boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
                //判断是否具有合并单元格
                if (isMerge) {
                    String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
                    returnObj = rs;
                } else {
                    if (StringUtils.equals(c.getCellTypeEnum().name(), "STRING")) {
                        returnObj = c.getRichStringCellValue().getString();
                    } else if (StringUtils.equals(c.getCellTypeEnum().name(), "NUMERIC")) {
                        returnObj = c.getNumericCellValue();
                    }
                }

                setFieldVal(map, c, returnObj);
            }
            result.add(map);
        }
        return result;

    }

    /**
     * 将excel中每列数据放入map中
     *
     * @param map
     * @param c
     * @param returnObj
     */
    public static void setFieldVal(Map<String, Object> map, Cell c, Object returnObj) {
        if(c != null){
            EnumColumnIndex enumColumnIndex = EnumColumnIndex.findByValue(c.getColumnIndex());
            map.put(enumColumnIndex.getColumnName(),returnObj);
        }
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {

                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }

        return null;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.STRING) {

            return cell.getStringCellValue();

        } else if (cell.getCellType() == CellType.BOOLEAN) {

            return String.valueOf(cell.getBooleanCellValue());

        } else if (cell.getCellType() == CellType.FORMULA) {

            return cell.getCellFormula();

        } else if (cell.getCellType() == CellType.NUMERIC) {

            return String.valueOf(cell.getNumericCellValue());

        }
        return "";
    }
}
