package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.bean.ordering.Booking;
import com.szhengzhu.bean.ordering.param.BookingParam;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Api(tags = "预订：BookingController")
@Validated
@RestController
@RequestMapping("/v1/booking")
public class BookingController {
    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "查询预订信息")
    @PostMapping(value = "/selectInfo")
    public Result<List<Booking>> selectInfo(@RequestBody BookingParam booking) {
        return showOrderingClient.selectInfo(booking);
    }

    @ApiOperation(value = "添加预订信息")
    @PostMapping(value = "")
    public Result add(@RequestBody @Validated Booking booking, HttpSession session) {
        booking.setStoreId((String) session.getAttribute(Contacts.RESTAURANT_STORE));
        booking.setEmployeeId((String) session.getAttribute(Contacts.RESTAURANT_USER));
        return showOrderingClient.add(booking);
    }

    @ApiOperation(value = "修改预订信息")
    @PatchMapping(value = "")
    public Result modify(@RequestBody Booking booking, HttpSession session) {
        booking.setEmployeeId((String) session.getAttribute(Contacts.RESTAURANT_USER));
        return showOrderingClient.modify(booking);
    }

    @ApiOperation(value = "取消预订信息")
    @PatchMapping(value = "/{markId}")
    public Result updateStatus(@PathVariable("markId") @NotBlank String markId) {
        return showOrderingClient.updateStatus(markId);
    }

    @ApiOperation(value = "预订桌已使用")
    @PatchMapping(value = "/useStatus/{markId}")
    public Result useStatus(@PathVariable("markId") @NotBlank String markId) {
        return showOrderingClient.useStatus(markId);
    }
}
