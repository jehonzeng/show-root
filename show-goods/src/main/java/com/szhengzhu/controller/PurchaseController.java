package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.PurchaseHistory;
import com.szhengzhu.bean.goods.PurchaseInfo;
import com.szhengzhu.bean.vo.PurchaseFood;
import com.szhengzhu.bean.vo.PurchaseHistoryVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.PurchaseService;

@RestController
@RequestMapping(value = "purchases")
public class PurchaseController {

    @Resource
    private PurchaseService purchaseService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<PurchaseFood>> page(@RequestBody PageParam<PurchaseInfo> base) {
        return purchaseService.getPurchasePage(base);
    }

    @RequestMapping(value = "/history/page", method = RequestMethod.POST)
    public Result<PageGrid<PurchaseHistoryVo>> historyPage(
            @RequestBody PageParam<PurchaseHistory> base) {
        return purchaseService.getHistoryPage(base);
    }

    @RequestMapping(value = "/appoin", method = RequestMethod.PATCH)
    public Result<?> appoinStaff(@RequestParam("markId") String markId,
            @RequestParam("userId") String userId) {
        return purchaseService.appoinStaff(markId, userId);
    }

    @RequestMapping(value = "/revoke", method = RequestMethod.PATCH)
    public Result<?> revokeStaff(@RequestParam("markId") String markId) {
        return purchaseService.revokeStaff(markId);
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public Result<?> buyFood(@RequestBody PurchaseInfo base) {
        return purchaseService.buyFood(base);
    }  
    
    @RequestMapping(value="/create",method = RequestMethod.POST)
    public Result<?> createPurchaseOrder() {
        return purchaseService.createPurchaseOrder();
    }
    
    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    public Result<?> deletePurchaseOrder(@RequestParam("buyTime") String buyTime) {
        return purchaseService.deletePurchaseOrder(buyTime);
    }
    
    @RequestMapping(value = "/reflash", method = RequestMethod.POST)
    public Result<?> reflashPurchaseOrder(){
        return purchaseService.reflashPurchaseOrder();
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<PurchaseFood>> getPurchaseListByStatus(@RequestParam("userId") String userId,
            @RequestParam("status") Integer status){
           return purchaseService.getListByStatus(userId,status);
    }
}
