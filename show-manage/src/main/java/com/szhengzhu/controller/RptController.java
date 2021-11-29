package com.szhengzhu.controller;

import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.rpt.SaleParam;
import com.szhengzhu.bean.rpt.SaleStatistics;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = {"报表管理：RptController"})
@RestController
@RequestMapping("/v1/rpt")
public class RptController {

    @Resource
    private ShowOrderClient showOrderClient;

    @ApiOperation(value = "获取商品月度销量报表", notes = "获取商品月度销量报表")
    @GetMapping(value = "/sale/statistic")
    public Result<List<SaleStatistics>> rptSaleStatistics(@RequestParam("month") String month, @RequestParam("partner") String partner) {
        return showOrderClient.rptSaleStatistics(month, partner);
    }

    @ApiOperation(value = "获取订单半年销量报表", notes = "获取订单半年销量报表")
    @GetMapping(value = "/year/sale")
    public Result<List<Map<String, Object>>> rpYearSale() {
        return showOrderClient.rptYearSale();
    }

    @ApiOperation(value = "获取全国配送数量分布图", notes = "获取全国配送数量分图")
    @GetMapping(value = "/city/delivery/count")
    public Result<List<Map<String, Object>>> rptCityDeliveryCount() {
        return showOrderClient.rptCityDeliveryCount();
    }

    @ApiOperation(value = "获取销量", notes = "获取销量")
    @PostMapping(value = "/sale/volume")
    public Result<List<Map<String, Object>>> rptSaleVolume(@RequestBody SaleParam param) {
        return showOrderClient.rptSaleVolume(param);
    }
}
