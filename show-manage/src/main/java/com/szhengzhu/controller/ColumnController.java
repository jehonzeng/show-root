package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.goods.ColumnInfo;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.ColumnGoodsVo;
import com.szhengzhu.bean.vo.ColumnMealVo;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "栏目管理：ColumnController" })
@RestController
@RequestMapping(value = "/v1/columns")
public class ColumnController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加栏目信息", notes = "添加栏目信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<ColumnInfo> save(@RequestBody ColumnInfo base) {
        return showGoodsClient.saveColumn(base);
    }

    @ApiOperation(value = "修改栏目信息", notes = "修改栏目信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<ColumnInfo> update(@RequestBody ColumnInfo base) {
        return showGoodsClient.modifyColumn(base);
    }

    @ApiOperation(value = "获取栏目分页列表", notes = "获取栏目分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<ColumnInfo>> page(@RequestBody PageParam<ColumnInfo> base) {
        return showGoodsClient.columnPage(base);
    }
    
    @ApiOperation(value ="根据id获取栏目信息",notes="根据id获取栏目信息")
    @RequestMapping(value = "/{markId}",method = RequestMethod.GET)
    public Result<ColumnInfo> getColumnInfo(@PathVariable(value="markId") String markId){
        return showGoodsClient.getColumnInfo(markId);
    }

    @ApiOperation(value = "获取栏目商品分页列表", notes = "获取栏目商品分页列表")
    @RequestMapping(value = "/goods/page", method = RequestMethod.POST)
    public Result<PageGrid<ColumnGoodsVo>> columnGoodsPage(
            @RequestBody PageParam<ColumnGoods> base) {
        return showGoodsClient.columnGoodsPage(base);
    }

    @ApiOperation(value = "修改栏目商品状态和显示顺序", notes = "修改栏目商品状态和显示顺序")
    @RequestMapping(value = "/goods", method = RequestMethod.PATCH)
    public Result<ColumnGoods> modifyColumnGoods(@RequestBody ColumnGoods base) {
        return showGoodsClient.updateColumnGoods(base);
    }

    @ApiOperation(value = "栏目中批量添加商品", notes = "栏目中批量添加商品")
    @RequestMapping(value = "/goods/batch", method = RequestMethod.POST)
    public Result<?> batch(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchColumnGoods(base);
    }

    @ApiOperation(value = "删除栏目商品", notes = "删除栏目商品")
    @RequestMapping(value = "/goods/delete", method = RequestMethod.POST)
    public Result<?> deleteColumnGoods(@RequestBody ColumnGoods base) {
        return showGoodsClient.deleteColumnGoods(base);
    }
    
    @ApiOperation(value="栏目中选择添加的上品分页列表",notes="栏目中选择添加的商品分页列表")
    @RequestMapping(value = "/goods/selectPage", method = RequestMethod.POST)
    public Result<PageGrid<GoodsVo>> getPageByColumn(@RequestBody PageParam<GoodsInfo> base){
        return showGoodsClient.getPageByColumn(base);
    }
    
    @ApiOperation(value="栏目套餐分页列表",notes="栏目套餐分页列表")
    @RequestMapping(value = "/meal/page",method = RequestMethod.POST)
    public Result<PageGrid<ColumnMealVo>> columnMealPage(@RequestBody PageParam<ColumnGoods> base){
        return showGoodsClient.getColumnMealPage(base);
    }
    
    @ApiOperation(value="栏目中选择添加的套餐分页",notes="栏目中选择添加的套餐分页")
    @RequestMapping(value="/meal/selectPage" ,method=RequestMethod.POST)
    public Result<PageGrid<MealInfo>> getMealPageByColumn(@RequestBody PageParam<MealInfo> base){
        return showGoodsClient.getMealPageByColumn(base);
    }
}
