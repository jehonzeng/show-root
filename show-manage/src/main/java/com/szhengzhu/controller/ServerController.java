package com.szhengzhu.controller;

import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.goods.ServerSupport;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"服务管理：ServerController"})
@RestController
@RequestMapping(value = "/v1/serves")
public class ServerController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加服务支持信息", notes = "添加服务支持信息")
    @PostMapping(value = "")
    public Result<ServerSupport> addServer(@RequestBody @Validated ServerSupport base) {
        return showGoodsClient.addServer(base);
    }

    @ApiOperation(value = "修改服务支持信息", notes = "修改服务支持信息")
    @PatchMapping(value = "")
    public Result<ServerSupport> modifyServer(@RequestBody @Validated ServerSupport base) {
        return showGoodsClient.modifyServer(base);
    }

    @ApiOperation(value = "服务信息列表", notes = "服务信息列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<ServerSupport>> serverPage(@RequestBody PageParam<ServerSupport> base) {
        return showGoodsClient.serverPage(base);
    }

    @ApiOperation(value = "获取有效的（商品不包含的服务）服务列表", notes = "获取有效的（商品不包含的服务）服务列表")
    @GetMapping(value = "/list")
    public Result<?> listServer() {
        return showGoodsClient.listServer();
    }

    @ApiOperation(value = "获取商品包含的服务列表", notes = "获取商品包含的服务列表")
    @GetMapping(value = "/goods/inList")
    public Result<?> listInServer(@RequestParam("goodsId") @NotBlank String goodsId) {
        return showGoodsClient.serverListInGoods(goodsId);
    }

    @ApiOperation(value = "批量添加服务到商品", notes = "批量添加服务到商品")
    @PostMapping(value = "/goods/addBatch")
    public Result saveBatchServes(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchGoodsServe(base);
    }

    @ApiOperation(value = "批量移除商品服务", notes = "批量移除商品服务")
    @PostMapping(value = "/goods/deleteBatch")
    public Result deleteBatchServes(@RequestBody BatchVo base) {
        return showGoodsClient.deleteBatchGoodsServe(base);
    }

    @ApiOperation(value = "根据id获取服务信息", notes = "根据id获取服务信息")
    @GetMapping(value = "/{markId}")
    public Result<ServerSupport> info(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.getServeById(markId);
    }

    @ApiOperation(value = "获取套餐包含的服务列表", notes = "获取套餐包含的服务列表")
    @GetMapping(value = "/meal/inList")
    public Result<List<String>> serverListInMeal(@RequestParam("mealId") @NotBlank String mealId) {
        return showGoodsClient.serverListInMeal(mealId);
    }

    @ApiOperation(value = "批量添加服务到套餐", notes = "批量添加服务到套餐")
    @PostMapping(value = "/meal/addBatch")
    public Result addBatchMealServes(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchMealServe(base);
    }

    @ApiOperation(value = "批量移除套餐服务", notes = "批量移除套餐服务")
    @PostMapping(value = "/meal/deleteBatch")
    public Result deleteBatchMealServes(@RequestBody BatchVo base) {
        return showGoodsClient.deleteBatchMealServe(base);
    }
}
