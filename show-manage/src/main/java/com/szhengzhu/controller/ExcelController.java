package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.excel.*;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.ExcelUtils;
import com.szhengzhu.util.JHExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Api(tags = "EXCEL导入导出管理：OrderController")
@RestController
@RequestMapping(value = "/v1/excels")
public class ExcelController {

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "导出今日下单用户配送信息", notes = "导出今日下单用户配送配送信息")
    @GetMapping(value = "/delivey")
    public Result<?> downDeliveyExcel(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent");
        StringBuilder source = new StringBuilder();
        source.append("delivery-info_");
        source.append(DateUtil.date());
        source.append(".xls");
        String name = source.toString();
        String fileName = null;
        if (StringUtils.contains(userAgent, "MSIE")) {
            fileName = URLEncoder.encode(name, "UTF-8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {
            fileName = new String(name.getBytes(), "ISO-8859-1");
        } else {
            fileName = URLEncoder.encode(name, "UTF-8");
        }
        Result<List<DeliveryModel>> result = showOrderClient.getDownDeliveryList();
        // 设置文件MIME类型
        response.setContentType(request.getServletContext().getMimeType(fileName));
        // 设置Content-Disposition
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        ExcelUtils eUtils = new ExcelUtils();
        HSSFWorkbook book = eUtils.getWb();
        eUtils.createSheet(result.getData(), "配送信息", DeliveryModel.class);
        try {
            OutputStream out = response.getOutputStream();
            book.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>();
    }

    @ApiOperation(value = "导出今日备货商品信息", notes = "导出今日备货商品信息")
    @GetMapping(value = "/operator/product")
    public Result<?> downOperatorGoodsExcel(HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent");
        StringBuilder source = new StringBuilder();
        source.append("stock-commodity_");
        source.append(DateUtil.date());
        source.append(".xls");
        String name = source.toString();
        String fileName = null;
        if (StringUtils.contains(userAgent, "MSIE")) {
            fileName = URLEncoder.encode(name, "UTF-8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {
            fileName = new String(name.getBytes(), "ISO-8859-1");
        } else {
            fileName = URLEncoder.encode(name, "UTF-8");
        }
        Result<List<ProductModel>> result = showOrderClient.getProductList();
        // 设置文件MIME类型
        response.setContentType(request.getServletContext().getMimeType(fileName));
        // 设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setCharacterEncoding("UTF-8");
        ExcelUtils eUtils = new ExcelUtils();
        HSSFWorkbook book = eUtils.getWb();
        eUtils.createSheet(result.getData(), "运营商品统计", ProductModel.class);
        try {
            OutputStream out = response.getOutputStream();
            book.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>();
    }

    @ApiOperation(value = "导出今日酱料打包统计", notes = "导出今日酱料打包统计")
    @GetMapping(value = "/sauce")
    public Result<?> downSauceExcel(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent");
        StringBuilder source = new StringBuilder();
        source.append("packing-sauce_");
        source.append(DateUtil.date());
        source.append(".xls");
        String name = source.toString();
        String fileName = null;
        if (StringUtils.contains(userAgent, "MSIE")) {
            fileName = URLEncoder.encode(name, "UTF-8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {
            fileName = new String(name.getBytes(), "ISO-8859-1");
        } else {
            fileName = URLEncoder.encode(name, "UTF-8");
        }
        Result<List<SauceVo>> result = showOrderClient.getDownSauceList();
        // 设置文件MIME类型
        response.setContentType(request.getServletContext().getMimeType(fileName));
        // 设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setCharacterEncoding("UTF-8");
        ExcelUtils eUtils = new ExcelUtils();
        HSSFWorkbook book = eUtils.getWb();
        eUtils.createSheet(result.getData(), "酱料打包统计", SauceVo.class);
        try {
            OutputStream out = response.getOutputStream();
            book.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>();
    }

    @ApiOperation(value = "导入excel物流信息批量修改配送订单", notes = "导入excel物流信息批量修改配送订单")
    @PostMapping(value = "/delivery/batchUpdate")
    public Result<?> batchModifyDeliveryOrder(
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (!file.isEmpty()) {
            Workbook workbook;
            List<Object> deliveryList = new ArrayList<>();
            InputStream is = null;
            try {
                LogisticsModel param = new LogisticsModel();
                is = file.getInputStream();
                String fileName = file.getOriginalFilename();
                workbook = JHExcelUtils.createWorkbook(is, fileName);
                deliveryList = JHExcelUtils.importDataFromExcelWorkbook(param, workbook);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 解析成List<实体>
            List<LogisticsModel> list = parsEntityList(deliveryList);
            return showOrderClient.batchModifyDeliveryOrder(list);
        }
        return new Result<>(StatusCode._4004);
    }

    private List<LogisticsModel> parsEntityList(List<Object> deliveryList) {
        List<LogisticsModel> list = new LinkedList<>();
        LogisticsModel logisticsModel;
        for (int i = 0, len = deliveryList.size(); i < len; i++) {
            logisticsModel = (LogisticsModel) deliveryList.get(i);
            logisticsModel.setCompanyNo(showBaseClient.getCodeByName(logisticsModel.getCompany()).getData());
            list.add(logisticsModel);
        }
        return list;
    }
    
    @ApiOperation(value = "导出今日发货订单", notes = "导出今日发货订单")
    @GetMapping(value = "/send")
    public Result<?> downSendInfo(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent");
        StringBuilder source = new StringBuilder();
        source.append("send-info_");
        source.append(DateUtil.date());
        source.append(".xls");
        String name = source.toString();
        String fileName;
        if (StringUtils.contains(userAgent, "MSIE")) {
            fileName = URLEncoder.encode(name, "UTF-8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {
            fileName = new String(name.getBytes(), "ISO-8859-1");
        } else {
            fileName = URLEncoder.encode(name, "UTF-8");
        }
        Result<List<OrderSendModel>> result = showOrderClient.listSendInfo();
        // 设置文件MIME类型
        response.setContentType(request.getServletContext().getMimeType(fileName));
        // 设置Content-Disposition
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        ExcelUtils eUtils = new ExcelUtils();
        HSSFWorkbook book = eUtils.getWb();
        eUtils.createSheet(result.getData(), "发货订单", OrderSendModel.class);
        try {
            OutputStream out = response.getOutputStream();
            book.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>();
    }
    
    @ApiOperation(value = "导出当前备货商品数量", notes = "导出当前备货商品数量")
    @GetMapping(value = "/product/quantity")
    public Result<?> downTodayProductQuantity(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent");
        StringBuilder source = new StringBuilder();
        source.append("product_");
        source.append(DateUtil.date());
        source.append(".xls");
        String name = source.toString();
        String fileName;
        if (StringUtils.contains(userAgent, "MSIE")) {
            fileName = URLEncoder.encode(name, "UTF-8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {
            fileName = new String(name.getBytes(), "ISO-8859-1");
        } else {
            fileName = URLEncoder.encode(name, "UTF-8");
        }
        Result<List<ProductModel>> result = showOrderClient.listTodayProductQuantity();
        // 设置文件MIME类型
        response.setContentType(request.getServletContext().getMimeType(fileName));
        // 设置Content-Disposition
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        ExcelUtils eUtils = new ExcelUtils();
        HSSFWorkbook book = eUtils.getWb();
        eUtils.createSheet(result.getData(), "当天备货商品数量", ProductModel.class);
        try {
            OutputStream out = response.getOutputStream();
            book.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>();
    }
}
