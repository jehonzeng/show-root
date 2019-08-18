package com.szhengzhu.controller;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.order.HolidayInfo;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"节假日管理:HolidayController"})
@RestController
@RequestMapping("/v1/holiday")
public class HolidayController {

    @Resource
    private ShowOrderClient showOrderClient;
    
    @ApiOperation(value = "后台设置节假日", notes = "后台设置节假日")
    @RequestMapping(value = "/operate/{holiday}", method = RequestMethod.PATCH)
    public Result<?> operateHoliday(@PathVariable("holiday") String holiday) throws ParseException {
        return showOrderClient.operateHoliday(holiday);
    }
    
    @ApiOperation(value = "获取时间段的节假日列表", notes = "获取时间段的节假日列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<HolidayInfo>> listHoliday(@RequestParam("start") String start, @RequestParam("end") String end) {
        return showOrderClient.listHoliday(start, end);
    }
}
