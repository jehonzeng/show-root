package com.szhengzhu.controller;

import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.excel.MealGoodsModel;
import com.szhengzhu.bean.goods.PurchaseHistory;
import com.szhengzhu.bean.goods.PurchaseInfo;
import com.szhengzhu.bean.vo.PurchaseFood;
import com.szhengzhu.bean.vo.PurchaseHistoryVo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"采购管理：PurchaseController"})
@RestController
@RequestMapping(value = "/v1/purchases")
public class PurchaseController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "获取采购信息列表", notes = "获取采购信息列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<PurchaseFood>> page(@RequestBody PageParam<PurchaseInfo> base) {
        return showGoodsClient.purchasePage(base);
    }

    @ApiOperation(value = "获取采购历史记录列表", notes = "获取采购历史记录列表")
    @PostMapping(value = "/history/page")
    public Result<PageGrid<PurchaseHistoryVo>> historyPage(
            @RequestBody PageParam<PurchaseHistory> base) {
        return showGoodsClient.purchaseHistoryPage(base);
    }

    @ApiOperation(value = "手动更新采购单", notes = "手动更新采购单")
    @PostMapping(value = "reflash")
    public Result reflashPurchaseOrder() {
        return showGoodsClient.reflashPurchaseOrder();
    }

    @ApiOperation(value = "前端获取不同状态的采购分页（0待采购 1我的采购 2 已完成）", notes = "前端获取不同状态的采购分页（0待采购 1我的采购 2 已完成）")
    @GetMapping(value = "/fore/page")
    public Result<List<PurchaseFood>> getListByStatus(HttpSession session,
                                                      @RequestParam(value = "status", defaultValue = "0") @NotNull Integer status) {
        String userId = (String) session.getAttribute(Contacts.LJS_SESSION);
        return showGoodsClient.getPurchaseListByStatus(userId, status);
    }

    @ApiOperation(value = "采购商品列表", notes = "采购商品列表")
    @GetMapping(value = "/fore/product")
    public Result<List<MealGoodsModel>> productList() {
        return showGoodsClient.getProductList();
    }

    @ApiOperation(value = "前端我要购买", notes = "前端我要购买")
    @GetMapping(value = "/fore/appoin")
    public Result appointUser(HttpSession session, @RequestParam("markId") @NotBlank String markId) {
        String userId = (String) session.getAttribute(Contacts.LJS_SESSION);
        return showGoodsClient.appoinStaff(markId, userId);
    }

    @ApiOperation(value = "前端撤销", notes = "前端撤销")
    @GetMapping(value = "/fore/revoke")
    public Result revokeUser(@RequestParam("markId") @NotBlank String markId) {
        return showGoodsClient.revokeStaff(markId);
    }

    @ApiOperation(value = "前端确认购买", notes = "前端确认购买")
    @PostMapping(value = "/fore/buy")
    public Result buyFood(HttpSession session, @RequestBody @Validated PurchaseInfo base) {
        String userId = (String) session.getAttribute(Contacts.LJS_SESSION);
        base.setUserId(userId);
        return showGoodsClient.buyFood(base);
    }

    @ApiOperation(value = "根据采购日期清空采购单", notes = "根据采购日期清空采购单")
    @DeleteMapping(value = "/clear")
    public Result deletePurchaseOrder(@RequestParam("buyTime") @NotBlank String buyTime) {
        return showGoodsClient.clearPurchaseOrder(buyTime);
    }
}
