package com.szhengzhu.controller;

import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.goods.ColumnInfo;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.ColumnGoodsVo;
import com.szhengzhu.bean.vo.ColumnMealVo;
import com.szhengzhu.bean.vo.GoodsVo;
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
@Api(tags = {"栏目管理：ColumnController"})
@RestController
@RequestMapping(value = "/v1/columns")
public class ColumnController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加栏目信息", notes = "添加栏目信息")
    @PostMapping(value = "")
    public Result<ColumnInfo> save(@RequestBody @Validated ColumnInfo base) {
        return showGoodsClient.saveColumn(base);
    }

    @ApiOperation(value = "修改栏目信息", notes = "修改栏目信息")
    @PatchMapping(value = "")
    public Result<ColumnInfo> update(@RequestBody @Validated ColumnInfo base) {
        return showGoodsClient.modifyColumn(base);
    }

    @ApiOperation(value = "获取栏目分页列表", notes = "获取栏目分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<ColumnInfo>> page(@RequestBody PageParam<ColumnInfo> base) {
        return showGoodsClient.columnPage(base);
    }

    @ApiOperation(value = "根据id获取栏目信息", notes = "根据id获取栏目信息")
    @GetMapping(value = "/{markId}")
    public Result<ColumnInfo> getColumnInfo(@PathVariable(value = "markId") @NotBlank String markId) {
        return showGoodsClient.getColumnInfo(markId);
    }

    @ApiOperation(value = "获取栏目商品分页列表", notes = "获取栏目商品分页列表")
    @PostMapping(value = "/goods/page")
    public Result<PageGrid<ColumnGoodsVo>> columnGoodsPage(
            @RequestBody PageParam<ColumnGoods> base) {
        return showGoodsClient.columnGoodsPage(base);
    }

    @ApiOperation(value = "修改栏目商品状态和显示顺序", notes = "修改栏目商品状态和显示顺序")
    @PatchMapping(value = "/goods")
    public Result<ColumnGoods> modifyColumnGoods(@RequestBody ColumnGoods base) {
        return showGoodsClient.updateColumnGoods(base);
    }

    @ApiOperation(value = "栏目中批量添加商品", notes = "栏目中批量添加商品")
    @PostMapping(value = "/goods/batch")
    public Result batch(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchColumnGoods(base);
    }

    @ApiOperation(value = "删除栏目商品", notes = "删除栏目商品")
    @PostMapping(value = "/goods/delete")
    public Result deleteColumnGoods(@RequestBody ColumnGoods base) {
        return showGoodsClient.deleteColumnGoods(base);
    }

    @ApiOperation(value = "栏目中选择添加的上品分页列表", notes = "栏目中选择添加的商品分页列表")
    @PostMapping(value = "/goods/selectPage")
    public Result<PageGrid<GoodsVo>> getPageByColumn(@RequestBody PageParam<GoodsInfo> base) {
        return showGoodsClient.getPageByColumn(base);
    }

    @ApiOperation(value = "栏目套餐分页列表", notes = "栏目套餐分页列表")
    @PostMapping(value = "/meal/page")
    public Result<PageGrid<ColumnMealVo>> columnMealPage(@RequestBody PageParam<ColumnGoods> base) {
        return showGoodsClient.getColumnMealPage(base);
    }

    @ApiOperation(value = "栏目中选择添加的套餐分页", notes = "栏目中选择添加的套餐分页")
    @PostMapping(value = "/meal/selectPage")
    public Result<PageGrid<MealInfo>> getMealPageByColumn(@RequestBody PageParam<MealInfo> base) {
        return showGoodsClient.getMealPageByColumn(base);
    }
}
