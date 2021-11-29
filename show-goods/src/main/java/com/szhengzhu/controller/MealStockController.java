package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.MealStock;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.MealStockService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/mealstock")
public class MealStockController {

    @Resource
    private MealStockService mealStockService;

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated MealStock base) {
        mealStockService.add(base);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated MealStock base) {
        mealStockService.modify(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<MealStock> page(@RequestBody PageParam<MealStock> base) {
        return mealStockService.pageStock(base);
    }

    @GetMapping(value = "/info")
    public StockBase getStockInfo(@RequestParam("mealId") @NotBlank String mealId, @RequestParam(value = "addressId", required = false) String addressId) {
        return mealStockService.getStockInfo(mealId, addressId);
    }

    @PostMapping(value = "/current/sub")
    public void subCurrentStock(@RequestBody ProductInfo productInfo) {
        mealStockService.subCurrentStock(productInfo);
    }

    @PostMapping(value = "/total/sub")
    public void subTotalStock(@RequestBody ProductInfo productInfo) {
        mealStockService.subTotalStock(productInfo);
    }

    @PostMapping(value = "/current/add")
    public void addCurrentStock(@RequestBody ProductInfo productInfo) {
        mealStockService.addCurrentStock(productInfo);
    }

    @PostMapping(value = "/total/add")
    public void addTotalStock(@RequestBody ProductInfo productInfo) {
        mealStockService.addTotalStock(productInfo);
    }
}
