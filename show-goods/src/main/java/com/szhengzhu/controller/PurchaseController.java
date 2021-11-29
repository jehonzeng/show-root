package com.szhengzhu.controller;

import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.excel.MealGoodsModel;
import com.szhengzhu.bean.goods.PurchaseHistory;
import com.szhengzhu.bean.goods.PurchaseInfo;
import com.szhengzhu.bean.vo.PurchaseFood;
import com.szhengzhu.bean.vo.PurchaseHistoryVo;
import com.szhengzhu.bean.vo.TodayProductVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.PurchaseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "purchases")
public class PurchaseController {

    @Resource
    private PurchaseService purchaseService;
    
    @Resource
    private ShowOrderClient showOrderClient;

    @PostMapping(value = "/page")
    public PageGrid<PurchaseFood> page(@RequestBody PageParam<PurchaseInfo> base) {
        return purchaseService.getPurchasePage(base);
    }

    @PostMapping(value = "/history/page")
    public PageGrid<PurchaseHistoryVo> historyPage(
            @RequestBody PageParam<PurchaseHistory> base) {
        return purchaseService.getHistoryPage(base);
    }

    @GetMapping(value = "/appoin")
    public void appoinStaff(@RequestParam("markId") @NotBlank String markId,
            @RequestParam("userId") @NotBlank String userId) {
        purchaseService.appoinStaff(markId, userId);
    }

    @GetMapping(value = "/revoke")
    public void revokeStaff(@RequestParam("markId") @NotBlank String markId) {
        purchaseService.revokeStaff(markId);
    }

    @PostMapping(value = "/buy")
    public void buyFood(@RequestBody PurchaseInfo base) {
        purchaseService.buyFood(base);
    }  
    
    @PostMapping(value="/create")
    public void createPurchaseOrder() {
         purchaseService.createPurchaseOrder();
    }
    
    @DeleteMapping(value = "/clear")
    public void deletePurchaseOrder(@RequestParam("buyTime") @NotBlank String buyTime) {
        purchaseService.deletePurchaseOrder(buyTime);
    }
    
    @PostMapping(value = "/reflash")
    public void reflashPurchaseOrder(){
        purchaseService.refreshPurchaseOrder();
    }
    
    @GetMapping(value = "/list")
    public List<PurchaseFood> getPurchaseListByStatus(@RequestParam("userId") @NotBlank String userId,
            @RequestParam("status") @NotNull Integer status){
           return purchaseService.getListByStatus(userId,status);
    }
    
    @GetMapping(value="/cheif/product")
    public List<MealGoodsModel> getProductList(){
        Result<List<TodayProductVo>> result = showOrderClient.listTodayItem();
        return purchaseService.getProductList(result.getData());
    }
}
