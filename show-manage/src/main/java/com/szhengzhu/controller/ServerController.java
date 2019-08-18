package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.ServerSupport;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "服务管理：ServerController" })
@RestController
@RequestMapping(value = "/v1/serves")
public class ServerController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加服务支持信息", notes = "添加服务支持信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<ServerSupport> addServer(@RequestBody ServerSupport base) {
        return showGoodsClient.addServer(base);
    }

    @ApiOperation(value = "修改服务支持信息", notes = "修改服务支持信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<ServerSupport> modifyServer(@RequestBody ServerSupport base) {
        return showGoodsClient.modifyServer(base);
    }

    @ApiOperation(value = "服务信息列表", notes = "服务信息列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<ServerSupport>> serverPage(@RequestBody PageParam<ServerSupport> base) {
        return showGoodsClient.serverPage(base);
    }

    @ApiOperation(value = "获取有效的（商品不包含的服务）服务列表", notes = "获取有效的（商品不包含的服务）服务列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<?> listServer() {
        return showGoodsClient.listServer();
    }
    
    @ApiOperation(value = "获取商品包含的服务列表", notes = "获取商品包含的服务列表")
    @RequestMapping(value = "/goods/inList", method = RequestMethod.GET)
    public Result<?> listInServer(@RequestParam("goodsId") String goodsId) {
        return showGoodsClient.serverListInGoods(goodsId);
    }

    @ApiOperation(value = "批量添加服务到商品", notes = "批量添加服务到商品")
    @RequestMapping(value = "/goods/addBatch", method = RequestMethod.POST)
    public Result<?> saveBatchServes(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchGoodsServe(base);
    }

    @ApiOperation(value = "批量移除商品服务", notes = "批量移除商品服务")
    @RequestMapping(value = "/goods/deleteBatch", method = RequestMethod.POST)
    public Result<?> deleteBatchServes(@RequestBody BatchVo base) {
        return showGoodsClient.deleteBatchGoodsServe(base);
    }

    @ApiOperation(value = "根据id获取服务信息", notes = "根据id获取服务信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> info(@PathVariable("markId") String markId) {
        return showGoodsClient.getServeById(markId);
    }
    
    @ApiOperation(value = "获取套餐包含的服务列表", notes = "获取套餐包含的服务列表")
    @RequestMapping(value = "/meal/inList", method = RequestMethod.GET)
    public Result<List<String>> serverListInMeal(@RequestParam("mealId") String mealId) {
        return showGoodsClient.serverListInMeal(mealId);
    }
    
    @ApiOperation(value = "批量添加服务到套餐", notes = "批量添加服务到套餐")
    @RequestMapping(value = "/meal/addBatch", method = RequestMethod.POST)
    public Result<?> addBatchMealServes(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchMealServe(base);
    }

    @ApiOperation(value = "批量移除套餐服务", notes = "批量移除套餐服务")
    @RequestMapping(value = "/meal/deleteBatch", method = RequestMethod.POST)
    public Result<?> deleteBatchMealServes(@RequestBody BatchVo base) {
        return showGoodsClient.deleteBatchMealServe(base);
    }
}
