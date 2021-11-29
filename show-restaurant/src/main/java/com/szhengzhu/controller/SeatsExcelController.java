package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.bean.excel.SeatsInfoVo;
import com.szhengzhu.bean.excel.SeatsVo;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(tags = "导出座位信息表格：SeatsExcelController")
@RestController
@RequestMapping("/v1/excel")
public class SeatsExcelController {
    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "导出日期之间的座位类型的座位数表格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginDate", value = "开始日期", dataType = "Date"),
            @ApiImplicitParam(name = "lastDate", value = "结束日期", dataType = "Date")})
    @GetMapping(value = "/seats/info")
    public Result<List<Map<String, Object>>> seatsInfo(@RequestParam("beginDate") Date beginDate, @RequestParam("lastDate") Date lastDate,
                                                       HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent");
        StringBuilder source = new StringBuilder();
        source.append("packing-Seats_");
        source.append(DateUtil.today());
        source.append(".xls");
        String name = source.toString();
        String fileName;
        if (StrUtil.contains(userAgent, "MSIE")) {
            fileName = URLEncoder.encode(name, "UTF-8");
        } else if (StrUtil.contains(userAgent, "Mozilla")) {
            fileName = new String(name.getBytes(), "ISO-8859-1");
        } else {
            fileName = URLEncoder.encode(name, "UTF-8");
        }
        Result<List<Map<String, Object>>> result = showOrderingClient.seatsInfo(beginDate, lastDate);
        // 设置文件MIME类型
        response.setContentType(request.getServletContext().getMimeType(fileName));
        // 设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setCharacterEncoding("UTF-8");
        ExcelUtils eUtils = new ExcelUtils();
        HSSFWorkbook book = eUtils.getWb();
        eUtils.createSheet(result.getData(), "座位数统计", SeatsVo.class);
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

    @ApiOperation(value = "导出日期之间的座位类型情况的表格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginDate", value = "开始日期", dataType = "Date"),
            @ApiImplicitParam(name = "lastDate", value = "结束日期", dataType = "Date")})
    @GetMapping(value = "/seats/typeInfo")
    public Result<List<Map<String, Object>>> seatsTypeInfo(@RequestParam("beginDate") Date beginDate, @RequestParam("lastDate") Date lastDate,
                                                           HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent");
        StringBuilder source = new StringBuilder();
        source.append("packing-SeatsInfo_");
        source.append(DateUtil.today());
        source.append(".xls");
        String name = source.toString();
        String fileName;
        if (StrUtil.contains(userAgent, "MSIE")) {
            fileName = URLEncoder.encode(name, "UTF-8");
        } else if (StrUtil.contains(userAgent, "Mozilla")) {
            fileName = new String(name.getBytes(), "ISO-8859-1");
        } else {
            fileName = URLEncoder.encode(name, "UTF-8");
        }
        Result<List<Map<String, Object>>> result = showOrderingClient.seatsTypeInfo(beginDate, lastDate);
        // 设置文件MIME类型
        response.setContentType(request.getServletContext().getMimeType(fileName));
        // 设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setCharacterEncoding("UTF-8");
        ExcelUtils eUtils = new ExcelUtils();
        HSSFWorkbook book = eUtils.getWb();
        eUtils.createSheet(result.getData(), "座位类型情况统计", SeatsInfoVo.class);
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

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //转换日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
