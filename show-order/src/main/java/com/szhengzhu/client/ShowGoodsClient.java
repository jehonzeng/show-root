package com.szhengzhu.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.vo.CalcData;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;

@FeignClient("show-goods")
public interface ShowGoodsClient {

    @RequestMapping(value = "/stocks/list/ids", method = RequestMethod.GET)
    Result<List<StockVo>> listGoodsStocks(@RequestParam("markIds") List<String> markIds);
    
    @RequestMapping(value = "/carts/calc", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<CalcData> calcTotal(@RequestBody OrderModel orderModel);
}
