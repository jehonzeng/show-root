package com.szhengzhu.controller;

import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.order.HolidayInfo;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"节假日管理:HolidayController"})
@RestController
@RequestMapping("/v1/holiday")
public class HolidayController {

    @Resource
    private ShowOrderClient showOrderClient;
    
    @ApiOperation(value = "后台设置节假日", notes = "后台设置节假日")
    @GetMapping(value = "/operate/{holiday}")
    public Result operateHoliday(@PathVariable("holiday") @NotBlank String holiday) {
        return showOrderClient.operateHoliday(holiday);
    }
    
    @ApiOperation(value = "获取时间段的节假日列表", notes = "获取时间段的节假日列表")
    @GetMapping(value = "/list")
    public Result<List<HolidayInfo>> listHoliday(@RequestParam("start") String start, @RequestParam("end") String end) {
        return showOrderClient.listHoliday(start, end);
    }
}
