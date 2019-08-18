package com.szhengzhu.client;

import java.util.Date;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.order.HolidayInfo;
import com.szhengzhu.bean.vo.CalcBase;
import com.szhengzhu.core.Result;

@FeignClient("show-order")
public interface ShowOrderClient {

    @RequestMapping(value = "/usercoupon/calc/param", method = RequestMethod.GET)
    Result<CalcBase> getCalcParam(@RequestParam(value = "couponId", required = false) String couponId,
            @RequestParam(value = "vouchers", required = false) List<String> vouchers,
            @RequestParam(value = "addressId", required = false) String addressId);
    
    @RequestMapping(value = "/{date}", method = RequestMethod.GET)
    Result<HolidayInfo> getHoliday(@PathVariable("date") Date date);
}
