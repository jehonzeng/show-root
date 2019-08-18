package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.PurchaseHistory;
import com.szhengzhu.bean.goods.PurchaseInfo;
import com.szhengzhu.bean.vo.PurchaseFood;
import com.szhengzhu.bean.vo.PurchaseHistoryVo;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.common.Commons;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"采购管理：PurchaseController"})
@RestController
@RequestMapping(value = "/v1/purchases")
public class PurchaseController {

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "获取采购信息列表", notes = "获取采购信息列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<PurchaseFood>> page(@RequestBody PageParam<PurchaseInfo> base) {
        return showGoodsClient.purchasePage(base);
    }

    @ApiOperation(value = "获取采购历史记录列表", notes = "获取采购历史记录列表")
    @RequestMapping(value = "/history/page", method = RequestMethod.POST)
    public Result<PageGrid<PurchaseHistoryVo>> historyPage(
            @RequestBody PageParam<PurchaseHistory> base) {
        return showGoodsClient.purchaseHistoryPage(base);
    }

    @ApiOperation(value = "手动更新采购单", notes = "手动更新采购单")
    @RequestMapping(value = "reflash", method = RequestMethod.POST)
    public Result<?> reflashPurchaseOrder() {
        return showGoodsClient.reflashPurchaseOrder();
    }

    @ApiOperation(value = "获取不同状态的采购分页", notes = "获取不同状态的采购分页")
    @RequestMapping(value = "/front/purchasePage", method = RequestMethod.GET)
    public Result<List<PurchaseFood>> getListByStatus(HttpSession session,
            @RequestParam("status") Integer status) {
//        String userId = (String) session.getAttribute(Commons.SESSION);
        String userId = "23414695960510464";
        return showGoodsClient.getPurchaseListByStatus(userId,status);
    }
//
//    @ApiOperation(value = "采购系统详情列表", notes = "采购系统详情列表")
//    @RequestMapping(value = "/front/details", method = RequestMethod.POST)
//    public Result<Map<String, Object>> details() {
//        return null;
//    }

    @ApiOperation(value = "选择我要买指定指派人", notes = "选择我要买指定指派人")
    @RequestMapping(value = "/front/appoin", method = RequestMethod.PATCH)
    public Result<?> appointUser(HttpSession session, @RequestParam("markId") String markId) {
        String userId = (String) session.getAttribute(Commons.SESSION);
        return showGoodsClient.appoinStaff(markId, userId);
    }

    @ApiOperation(value = "选择我不买撤销指定人", notes = "选择我不买撤销指定人")
    @RequestMapping(value = "/front/revoke", method = RequestMethod.PATCH)
    public Result<?> revokeUser(@RequestParam("markId") String markId) {
        return showGoodsClient.revokeStaff(markId);
    }

    @ApiOperation(value = "确认购买让输入购买量保存购买记录", notes = "确认购买输入购买量保存购买记录")
    @RequestMapping(value = "/front/buy", method = RequestMethod.POST)
    public Result<?> buyFood(HttpSession session, @RequestBody PurchaseInfo base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4004);
        String userId = (String) session.getAttribute(Commons.SESSION);
        base.setUserId(userId);
        return showGoodsClient.buyFood(base);
    }
    
    @ApiOperation(value="根据采购日期清空采购单",notes="根据采购日期清空采购单")
    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    public Result<?> deletePurchaseOrder(@RequestParam("buyTime") String buyTime) {
        return showGoodsClient.clearPurchaseOrder(buyTime);
    }
}
