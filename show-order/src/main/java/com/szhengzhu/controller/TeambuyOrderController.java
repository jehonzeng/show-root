package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.bean.order.TeambuyGroup;
import com.szhengzhu.bean.wechat.vo.TeambuyModel;
import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.TeambuyOrderService;

@RestController
@RequestMapping("/teambuyorder")
public class TeambuyOrderController {

    @Resource
    private TeambuyOrderService teambuyOrderService;
    
    @Resource
    private ShowActivityClient showActivityClient;
    
    @RequestMapping(value = "/group/page", method = RequestMethod.POST)
    public Result<PageGrid<TeambuyGroup>> pageOrder(@RequestBody PageParam<TeambuyGroup> groupPage) {
        return teambuyOrderService.pageGroup(groupPage);
    }
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<TeambuyOrder>> pageItem(@RequestBody PageParam<TeambuyOrder> orderPage) {
        return teambuyOrderService.pageOrder(orderPage);
    }
    
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Result<?> modifyStatus(@RequestParam("markId") String markId, @RequestParam("orderStatus") String orderStatus) {
        return teambuyOrderService.updateStatus(markId, orderStatus);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addOrder(@RequestBody TeambuyModel model) {
        Result<TeambuyInfo> teambuyResult = showActivityClient.getTeambuyInfo(model.getMarkId(), model.getSpecIds());
        if (teambuyResult.getCode().equals("200") && teambuyResult.getData() != null) {
            TeambuyInfo teambuyInfo = teambuyResult.getData();
            return teambuyOrderService.addOrder(model, teambuyInfo);
        }
        return teambuyResult;
    }
    
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<TeambuyOrder> getOrderByNo(@RequestParam("orderNo") String orderNo) {
        return teambuyOrderService.getOrderByNo(orderNo);
    }
}
