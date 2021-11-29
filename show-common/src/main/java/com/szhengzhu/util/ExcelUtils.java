package com.szhengzhu.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.szhengzhu.annotation.Excel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ExcelUtils {

    private HSSFWorkbook wb;

    private HSSFFont font;
    
    private HSSFFont titleFont;

    private HSSFCellStyle style;

    private HSSFCellStyle titleStyle;

    public ExcelUtils() {
        wb = new HSSFWorkbook();
        font = wb.createFont();
        font.setFontHeightInPoints((short) 12);// 字体大小
        font.setFontName("宋体");
        font.setColor(HSSFFont.COLOR_NORMAL);// 字体颜色
//        font.setBold(true);// 粗体
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        style.setFont(font);
        // 第一行标题样式
        titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 15);// 字体大小
        titleFont.setFontName("宋体");
        titleFont.setColor(HSSFFont.COLOR_RED);// 字体颜色
        titleFont.setBold(true);// 粗体
        titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        titleStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());//添加颜色
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
    }

    public void createSheet(List<?> list, String name, Class<?> clazz) {
        HSSFSheet sheet = wb.createSheet(name);
        List<ExcelSort> _ex = init(clazz);
        title(_ex, sheet);
        table(_ex, list, sheet);
    }

    /**
     * 创建标题
     * 
     * @param _ex
     */
    private void title(List<ExcelSort> _ex, HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        int width = 50;
        for (int i = 0, len = _ex.size(); i < len; i++) {
            sheet.setColumnWidth(i, 255 * width + 184);
            cellHeader(row.createCell(i), _ex.get(i).excel.name());
        }
    }

    private void cellHeader(HSSFCell cell, String text) {
        cell.setCellStyle(titleStyle);
        cell.setCellValue(text);
    }

    private void cell(HSSFCell cell, String text) {
        cell.setCellStyle(style);
        cell.setCellValue(text);
    }

    /**
     * 创建表格数据
     * 
     * @param _ex
     */
    private void table(List<ExcelSort> _ex, List<?> list, HSSFSheet sheet) {
        HSSFRow row;
        String[] text;
        for (int i = 0, len = list.size(); i < len; i++) {
            row = sheet.createRow(i + 1);
            text = value(_ex, list.get(i));
            for (int j = 0; j < text.length; j++) {
                if (text[j] != null && text[j].contains(":")) {
                    createColDownList(text[j], sheet, j, i);
                    continue;
                }
                cell(row.createCell(j), text[j]);
            }
        }
    }
    
    private String[] value(List<ExcelSort> excel, Object object) {
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        JSONObject json = JSONObject.parseObject(gson.toJson(object));
        String[] text = new String[excel.size()];
        String fomat;
        for (int i = 0, len = excel.size(); i < len; i++) {
            text[i] = json.getString(excel.get(i).field.getName());
            if (excel.get(i).excel.select()) {
                text[i] = ":" + text[i];// 标记下拉数据
                continue;
            }
            if (excel.get(i).excel.format().equals("")) {
                continue;
            }
            fomat = ";" + excel.get(i).excel.format() + ";";
            if (fomat.indexOf(";" + text[i] + ":") < 0) {
                text[i] = "";
                continue;
            }
            fomat = fomat.substring(fomat.indexOf(";" + text[i] + ":") + 1);
            fomat = fomat.substring(0, fomat.indexOf(";"));
            text[i] = fomat.replace(text[i] + ":", "");
        }
        return text;
    }

    /**
     * 排序
     * 
     * @param clazz
     * @return
     */
    private List<ExcelSort> init(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<ExcelSort> field_list = new LinkedList<>();
        for (int i = 0; i < fields.length; i++) {
            Excel excel = fields[i].getAnnotation(Excel.class);
            if (excel == null || excel.skip() == true) {
                continue;
            }
            field_list.add(new ExcelSort(excel, fields[i]));
        }
        Collections.sort(field_list);
        return field_list;
    }

    class ExcelSort implements Comparable<ExcelSort> {
        Excel excel;
        Field field;

        @Override
        public int compareTo(ExcelSort o) {
            return this.excel.sort() - o.excel.sort();
        }

        public ExcelSort(Excel excel, Field field) {
            this.excel = excel;
            this.field = field;
        }

    }

    public HSSFWorkbook getWb() {
        return wb;
    }

    /**
     * 创建某一列下拉框
     *
     * @date 2019年9月19日
     */
    private void createColDownList(String text, HSSFSheet sheet, int col, int row) {
//        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .create();
//        String[] data = gson.fromJson(text, String[].class);
        text = text.substring(text.indexOf("[") + 1,text.length() - 1 );
        String[] data = text.split(",");
        System.out.println(Arrays.toString(data));
        // //生成一个工作簿对象
        // HSSFWorkbook workbook = new HSSFWorkbook();
        // //生成一个名称为Info的表单
        // HSSFSheet sheet = workbook.createSheet("Info");
        // 设置下拉列表作用的单元格(firstrow, lastrow, firstcol, lastcol)
        CellRangeAddressList regions = new CellRangeAddressList(row + 1, row + 1, col, col);
        // 生成并设置数据有效性验证
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(data);
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        // 将有效性验证添加到表单
        sheet.addValidationData(data_validation_list);
    }
}