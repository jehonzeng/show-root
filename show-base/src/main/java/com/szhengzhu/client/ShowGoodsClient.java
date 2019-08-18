package com.szhengzhu.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.core.Result;

@FeignClient(name = "show-goods")
public interface ShowGoodsClient {

    @RequestMapping(value = "/goodsVouchers/{markId}", method = RequestMethod.GET)
    Result<GoodsVoucher> getGoodsVoucherInfo(@PathVariable("markId") String markId);
}
