package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.goods.GoodsStock;
import com.szhengzhu.bean.goods.MealStock;
import com.szhengzhu.bean.vo.GoodsBaseVo;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"库存管理：StockController"})
@RestController
@RequestMapping(value = "/v1/stocks")
public class StockController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加商品库存信息", notes = "添加商品库存信息")
    @PostMapping(value = "")
    public Result<GoodsStock> addStocks(@RequestBody @Validated GoodsStock goodsStock) {
        return showGoodsClient.addGoodsStock(goodsStock);
    }

    @ApiOperation(value = "修改库存商品信息", notes = "修改库存商品信息")
    @PatchMapping(value = "")
    public Result<GoodsStock> editStocks(@RequestBody @Validated GoodsStock goodsStock) {
        return showGoodsClient.modifyGoodsStock(goodsStock);
    }

    @ApiOperation(value = "库存商品分页", notes = "获取库存商品分页")
    @PostMapping(value = "/page")
    public Result<PageGrid<StockVo>> stockPage(@RequestBody PageParam<GoodsStock> base) {
        return showGoodsClient.stockPage(base);
    }

    @ApiOperation(value = "根据库存id获取详情信息", notes = "根据库存id获取详情信息")
    @GetMapping(value = "/details/{markId}")
    public Result<GoodsBaseVo> editGoodsCoupon(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.details(markId);
    }

    @ApiOperation(value = "根据库存id获取库存信息", notes = "根据库存id获取库存信息")
    @GetMapping(value = "/{markId}")
    Result<GoodsStock> stockInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.srtockInfo(markId);
    }

    @ApiOperation(value = "添加套餐库存", notes = "添加套餐库存")
    @PostMapping(value = "/meal")
    public Result addMealStock(@RequestBody @Validated MealStock stock) {
        return showGoodsClient.addMealStock(stock);
    }

    @ApiOperation(value = "修改套餐库存", notes = "修改套餐库存")
    @PatchMapping(value = "/meal")
    public Result modifyMealStock(@RequestBody @Validated MealStock stock) {
        return showGoodsClient.modifyMealStock(stock);
    }

    @ApiOperation(value = "获取套餐库存分页列表", notes = "获取套餐库存分页列表")
    @PostMapping(value = "/meal/page")
    public Result<PageGrid<MealStock>> pageMealStock(@RequestBody PageParam<MealStock> pageStock) {
        return showGoodsClient.pageMealStock(pageStock);
    }
}
