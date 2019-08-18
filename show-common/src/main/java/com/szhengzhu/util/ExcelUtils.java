package com.szhengzhu.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.szhengzhu.annotation.Excel;

public class ExcelUtils {

    private HSSFWorkbook wb;

    private HSSFFont font;

    private HSSFCellStyle style;

    public ExcelUtils() {
        wb = new HSSFWorkbook();
        font = wb.createFont();
        font.setFontHeightInPoints((short) 20);// 字体大小
        font.setFontName("宋体");
        font.setColor(HSSFFont.COLOR_NORMAL);// 字体颜色
        font.setBold(true);// 粗体
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        style.setFont(font);
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
        for (int i = 0, len = _ex.size(); i < len; i++) {
            cell(row.createCell(i), _ex.get(i).excel.name());
        }
    }

    private void cell(HSSFCell cell, String text) {
        cell.setCellStyle(style);
        cell.setCellValue(text);
    }

    /**
     * 创建标题
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
                cell(row.createCell(j), text[j]);
            }
        }
    }

    private String[] value(List<ExcelSort> excel, Object object) {
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        JSONObject json = JSONObject.parseObject(gson.toJson(object));
        String[] text = new String[excel.size()];
        String fomat;
        for (int i = 0, len = excel.size(); i < len; i++) {
            text[i] = json.getString(excel.get(i).field.getName());
            // 价格处理
//			if(excel.get(i).excel.price()) {
//				text[i] = StringUtils.format(text[i]);
//				continue;
//			}
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
        List<ExcelSort> field_list = new LinkedList<ExcelSort>();
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

}
