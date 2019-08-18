package com.szhengzhu.controller;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsStock;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.GoodsStockService;

@RestController
@RequestMapping(value = "/stocks")
public class StockController {
    
    @Resource
    private GoodsStockService goodsStockService;
    

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addGoodsStock(@RequestBody GoodsStock goodsStock) {
        return goodsStockService.addGoodsStock(goodsStock);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> modifyGoodsStock(@RequestBody GoodsStock goodsStock) {
        return goodsStockService.editGoodsStock(goodsStock);
    }
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<StockVo>> page(@RequestBody PageParam<GoodsStock> base) {
        return goodsStockService.getPage(base);
    }
    
    @RequestMapping(value = "/details/{markId}", method = RequestMethod.GET)
    public Result<?> details(@PathVariable(value = "markId") String markId) {
        return goodsStockService.getDetailedInfos(markId);
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> srtockInfo(@PathVariable(value = "markId") String markId) {
        return goodsStockService.getGoodsStockInfo(markId);
    }
    
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<StockBase> getStockInfo(@RequestParam("goodsId") String goodsId,
            @RequestParam("specIds") String specIds, @RequestParam(value = "addressId", required = false) String addressId) {
        return goodsStockService.getStockInfo(goodsId, specIds, addressId);
    }

//    
//    @RequestMapping(value = "/list/ids", method = RequestMethod.GET)
//    public Result<List<StockVo>> listGoodsStocks(@RequestParam("markIds") List<String> markIds) {
//        return goodsStockService.listGoodsStock(markIds);
//    }

}
