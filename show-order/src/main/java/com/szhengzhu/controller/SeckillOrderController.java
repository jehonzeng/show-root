package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.wechat.vo.SeckillModel;
import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.SeckillOrderService;

@RestController
@RequestMapping("/seckillorder")
public class SeckillOrderController {

    @Resource
    private SeckillOrderService seckillOrderService;
    
    @Resource
    private ShowActivityClient showActivityClient;
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<SeckillOrder>> pageOrder(@RequestBody PageParam<SeckillOrder> orderPage) {
        return seckillOrderService.pageOrder(orderPage);
    }
    
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Result<?> modifyStatus(@RequestParam("markId") String markId, @RequestParam("orderStatus") String orderStatus) {
        return seckillOrderService.updateStatus(markId, orderStatus);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addOrder(@RequestBody SeckillModel model) {
        Result<SeckillInfo> seckillResult = showActivityClient.getSeckillInfo(model.getMarkId());
        if (seckillResult.getCode().equals("200") && seckillResult.getData() != null) {
            SeckillInfo seckillInfo = seckillResult.getData();
            seckillOrderService.addOrder(model, seckillInfo);
        }
        return seckillResult;
    }
    
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<SeckillOrder> getOrderByNo(@RequestParam("orderNo") String orderNo) {
        return seckillOrderService.getOrderByNo(orderNo);
    }
}
