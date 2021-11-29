package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import com.szhengzhu.bean.ordering.Booking;
import com.szhengzhu.bean.ordering.param.BookingParam;
import com.szhengzhu.service.BookingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/booking")
public class BookingController {
    @Resource

    private BookingService bookingService;

    @PostMapping(value = "/selectInfo")
    public List<Booking> selectInfo(@RequestBody BookingParam booking) {
        return bookingService.selectInfo(booking);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated Booking booking) {
        bookingService.add(booking);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody Booking booking) {
        bookingService.modify(booking);
    }

    @PatchMapping(value = "/{markId}")
    public void updateStatus(@PathVariable("markId") @NotBlank String markId) {
        bookingService.modify(Booking.builder().markId(markId).status(-1).modifyTime(DateUtil.date()).build());
    }

    @PatchMapping(value = "/useStatus/{markId}")
    public void useStatus(@PathVariable("markId") @NotBlank String markId) {
        bookingService.modify(Booking.builder().markId(markId).status(2).modifyTime(DateUtil.date()).build());
    }
}
