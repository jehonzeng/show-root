package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsStock;
import com.szhengzhu.bean.vo.GoodsBaseVo;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "库存管理：StockController" })
@RestController
@RequestMapping(value = "/v1/stocks")
public class StockController {
    
    @Resource
    private ShowGoodsClient showGoodsClient;
    
    @ApiOperation(value = "添加商品库存信息", notes = "添加商品库存信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<GoodsStock> addStocks(@RequestBody GoodsStock goodsStock) {
        return showGoodsClient.addGoodsStock(goodsStock);
    }

    @ApiOperation(value = "修改库存商品信息", notes = "修改库存商品信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<GoodsStock> editStocks(@RequestBody GoodsStock goodsStock) {
        return showGoodsClient.modifyGoodsStock(goodsStock);
    }

    @ApiOperation(value = "库存商品分页", notes = "获取库存商品分页")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<StockVo>> stockPage(@RequestBody PageParam<GoodsStock> base) {
        return showGoodsClient.stockPage(base);
    }
    
    @ApiOperation(value = "根据库存id获取详情信息", notes = "根据库存id获取详情信息")
    @RequestMapping(value = "/details/{markId}", method = RequestMethod.GET)
    public Result<GoodsBaseVo> editGoodsCoupon(@PathVariable(value = "markId") String markId) {
        return showGoodsClient.details(markId);
    } 
    
    @ApiOperation(value = "根据库存id获取库存信息", notes = "根据库存id获取库存信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    Result<GoodsStock> stockInfo(@PathVariable(value = "markId") String markId){
        return showGoodsClient.srtockInfo(markId);
        
    }
}
