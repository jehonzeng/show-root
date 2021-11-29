package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.GoodsStock;
import com.szhengzhu.bean.vo.GoodsBaseVo;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.GoodsStockService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "/stocks")
public class StockController {

    @Resource
    private GoodsStockService goodsStockService;

    @PostMapping(value = "/add")
    public GoodsStock addGoodsStock(@RequestBody @Validated GoodsStock base) {
        return goodsStockService.addGoodsStock(base);
    }

    @PatchMapping(value = "/edit")
    public GoodsStock modifyGoodsStock(@RequestBody @Validated GoodsStock goodsStock) {
        return goodsStockService.modifyGoodsStock(goodsStock);
    }

    @PostMapping(value = "/page")
    public PageGrid<StockVo> page(@RequestBody PageParam<GoodsStock> base) {
        return goodsStockService.getPage(base);
    }

    @GetMapping(value = "/details/{markId}")
    public GoodsBaseVo details(@PathVariable("markId") @NotBlank String markId) {
        return goodsStockService.getDetailInfos(markId);
    }

    @GetMapping(value = "/{markId}")
    public GoodsStock stockInfo(@PathVariable("markId") @NotBlank String markId) {
        return goodsStockService.getGoodsStockInfo(markId);
    }

    @GetMapping(value = "/info")
    public StockBase getStockInfo(@RequestParam("goodsId") @NotBlank String goodsId,
                                  @RequestParam("specIds") @NotBlank String specIds,
                                  @RequestParam(value = "addressId", required = false) String addressId) {
        return goodsStockService.getStockInfo(goodsId, specIds, addressId);
    }

    @PostMapping(value = "/current/sub")
    public void subCurrentStock(@RequestBody ProductInfo productInfo) {
        goodsStockService.subCurrentStock(productInfo);
    }

    @PostMapping(value = "/total/sub")
    public void subTotalStock(@RequestBody ProductInfo productInfo) {
        goodsStockService.subTotalStock(productInfo);
    }

    @PostMapping(value = "/current/add")
    public void addCurrentStock(@RequestBody ProductInfo productInfo) {
        goodsStockService.addCurrentStock(productInfo);
    }

    @PostMapping(value = "/total/add")
    public void addTotalStock(@RequestBody ProductInfo productInfo) {
        goodsStockService.addTotalStock(productInfo);
    }
}
