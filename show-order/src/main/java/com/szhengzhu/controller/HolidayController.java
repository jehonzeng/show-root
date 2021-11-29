package com.szhengzhu.controller;

import com.szhengzhu.bean.order.HolidayInfo;
import com.szhengzhu.bean.vo.DeliveryDate;
import com.szhengzhu.service.HolidayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 假期操作类
 *
 * @author Jehon Zeng
 */
@RestController
@RequestMapping("/holiday")
public class HolidayController {

    @Resource
    private HolidayService holidayService;

    @GetMapping(value = "/delivery/list")
    public List<DeliveryDate> listDeliveryDate() {
        return holidayService.listDeliveryDate();
    }

    @GetMapping(value = "/operate/{holiday}")
    public void operateHoliday(@PathVariable("holiday") String holiday) {
        holidayService.operateHoliday(holiday);
    }

    @GetMapping(value = "/list")
    public List<HolidayInfo> listHoliday(@RequestParam("start") String start, @RequestParam("end") String end) {
        return holidayService.listHoliday(start, end);
    }

    @GetMapping(value = "/{date}")
    public HolidayInfo getHoliday(@PathVariable("date") Date date) {
        return holidayService.getHoliday(date);
    }
}
