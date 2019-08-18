package com.szhengzhu.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.order.HolidayInfo;
import com.szhengzhu.bean.vo.DeliveryDate;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.HolidayService;

@RestController
@RequestMapping("/holiday")
public class HolidayController {

    @Resource
    private HolidayService holidayService;
    
    @RequestMapping(value = "/delivery/list", method = RequestMethod.GET)
    public Result<List<DeliveryDate>> listDeliveryDate() {
        return holidayService.listDeliveryDate();
    }
    
    @RequestMapping(value = "/operate/{holiday}", method = RequestMethod.PATCH)
    public Result<?> operateHoliday(@PathVariable("holiday") String holiday) throws ParseException {
        return holidayService.operateHoliday(holiday);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<HolidayInfo>> listHoliday(@RequestParam("start") String start, @RequestParam("end") String end) throws ParseException {
        return holidayService.listHoliday(start, end);
    }
    
    @RequestMapping(value = "/{date}", method = RequestMethod.GET)
    public Result<HolidayInfo> getHoliday(@PathVariable("date") Date date) {
        return holidayService.getHoliday(date);
    }
}
