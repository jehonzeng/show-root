package com.szhengzhu.controller;

import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.goods.SpecialInfo;
import com.szhengzhu.bean.goods.SpecialItem;
import com.szhengzhu.bean.vo.*;
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
@Api(tags = {"促销管理:SpecialController"})
@RestController
@RequestMapping(value = "/v1/specials")
public class SpecialController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加特价", notes = "添加特价")
    @PostMapping(value = "")
    public Result<SpecialInfo> add(@RequestBody @Validated SpecialInfo base) {
        return showGoodsClient.addSpecial(base);
    }

    @ApiOperation(value = "修改特价", notes = "修改特价")
    @PatchMapping(value = "")
    public Result<SpecialInfo> edit(@RequestBody @Validated SpecialInfo base) {
        return showGoodsClient.editSpecial(base);
    }

    @ApiOperation(value = "获取特价列表分页", notes = "获取特价列表分页")
    @PostMapping(value = "/page")
    public Result<PageGrid<SpecialInfo>> page(@RequestBody PageParam<SpecialInfo> base) {
        return showGoodsClient.getSpecialPage(base);
    }

    @ApiOperation(value = "根据id获取特价信息", notes = "根据id获取特价信息")
    @GetMapping(value = "/{markId}")
    public Result<SpecialInfo> specialInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.specialInfoById(markId);
    }

    @ApiOperation(value = "根据栏目id批量添加特价", notes = "根据栏目id批量添加特价")
    @PostMapping(value = "/column/addBatch")
    public Result<List<SpecialItem>> addItemBatchByColumn(@RequestBody SpecialBatchVo base) {
        return showGoodsClient.addItemBatchByColumn(base);
    }

    @ApiOperation(value = "根据标签id批量添加特价", notes = "根据标签id批量添加特价")
    @PostMapping(value = "/label/addBatch")
    public Result<List<SpecialItem>> addItemBatchByLabel(@RequestBody SpecialBatchVo base) {
        return showGoodsClient.addItemBatchByLabel(base);
    }

    @ApiOperation(value = "取消特价商品", notes = "取消特价商品")
    @PostMapping(value = "/item/delete")
    public Result deleteItem(@RequestBody SpecialItem base) {
        return showGoodsClient.deleteSpecialItem(base);
    }

    @ApiOperation(value = "获取特价商品分页", notes = "获取特价商品分页")
    @PostMapping(value = "/item/page")
    public Result<PageGrid<SpecialGoodsVo>> itemPage(@RequestBody PageParam<SpecialItem> base) {
        return showGoodsClient.getSpecialItemPage(base);
    }

    @ApiOperation(value = "没有加入该商品的特价列表", notes = "没有加入该商品的特价列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listSpecialByGoods(@RequestParam("goodsId") @NotBlank String goodsId) {
        return showGoodsClient.listSpecialById(goodsId);
    }

    @ApiOperation(value = "商品添加特价", notes = "商品添加特价")
    @PostMapping(value = "/item")
    public Result<SpecialItem> addItem(@RequestBody SpecialItem base) {
        return showGoodsClient.addSpecialItem(base);
    }

    @ApiOperation(value = "批量添加特价商品", notes = "批量添加特价商品")
    @PostMapping(value = "/goods/batch")
    public Result batch(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchSpecialGoods(base);
    }

    @ApiOperation(value = "在特价中添加的单品分页", notes = "在特价中添加的商单品分页")
    @PostMapping(value = "/goods/selectPage")
    public Result<PageGrid<GoodsVo>> getGoodsPageBySpecial(@RequestBody PageParam<GoodsInfo> base) {
        return showGoodsClient.getPageBySpecial(base);
    }

    @ApiOperation(value = "在特价中添加的套餐分页", notes = "在特价中添加的套餐分页")
    @PostMapping(value = "/meal/selectPage")
    public Result<PageGrid<MealInfo>> getMealPageBySpecial(@RequestBody PageParam<MealInfo> base) {
        return showGoodsClient.getMealPageBySpecial(base);
    }
}
