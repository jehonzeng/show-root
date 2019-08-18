package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.goods.SpecialInfo;
import com.szhengzhu.bean.goods.SpecialItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.vo.SpecialBatchVo;
import com.szhengzhu.bean.vo.SpecialGoodsVo;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"特价管理:SpecialController"})
@RestController
@RequestMapping(value = "/v1/specials")
public class SpecialController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加特价", notes = "添加特价")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<SpecialInfo> add(@RequestBody SpecialInfo base) {
        return showGoodsClient.addSpecial(base);
    }

    @ApiOperation(value = "修改特价", notes = "修改特价")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<SpecialInfo> edit(@RequestBody SpecialInfo base) {
        return showGoodsClient.editSpecial(base);
    }

    @ApiOperation(value = "获取特价列表分页", notes = "获取特价列表分页")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<SpecialInfo>> page(@RequestBody PageParam<SpecialInfo> base) {
        return showGoodsClient.getSpecialPage(base);
    }

    @ApiOperation(value = "根据id获取特价信息", notes = "根据id获取特价信息")
    @RequestMapping(value ="/{markId}",method = RequestMethod.GET)
    public Result<SpecialInfo> specialInfo(@PathVariable("markId") String markId) {
        return showGoodsClient.specialInfoById(markId);
    }

    @ApiOperation(value = "根据栏目id批量添加特价", notes = "根据栏目id批量添加特价")
    @RequestMapping(value = "/column/addBatch", method = RequestMethod.POST)
    public Result<?> addItemBatchByColumn(@RequestBody SpecialBatchVo base) {
        return showGoodsClient.addItemBatchByColumn(base);
    }

    @ApiOperation(value = "根据标签id批量添加特价", notes = "根据标签id批量添加特价")
    @RequestMapping(value = "/label/addBatch", method = RequestMethod.POST)
    public Result<?> addItemBatchByLabel(@RequestBody SpecialBatchVo base) {
        return showGoodsClient.addItemBatchByLabel(base);
    }

    @ApiOperation(value = "取消特价商品", notes = "取消特价商品")
    @RequestMapping(value = "/item/delete", method = RequestMethod.POST)
    public Result<?> deleteItem(@RequestBody SpecialItem base) {
        return showGoodsClient.deleteSpecialItem(base);
    }

    @ApiOperation(value = "获取特价商品分页", notes = "获取特价商品分页")
    @RequestMapping(value = "/item/page", method = RequestMethod.POST)
    public Result<PageGrid<SpecialGoodsVo>> itemPage(@RequestBody PageParam<SpecialItem> base) {
        return showGoodsClient.getSpecialItemPage(base);
    }
    
    @ApiOperation(value = "没有加入该商品的特价列表", notes = "没有加入该商品的特价列表")
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listSpecialByGoods(@RequestParam("goodsId") String goodsId) {
        return showGoodsClient.listSpecialById(goodsId);
    }
    
    @ApiOperation(value = "商品添加特价",notes= "商品添加特价")
    @RequestMapping(value ="/item",method=RequestMethod.POST)
    public Result<SpecialItem> addItem(@RequestBody SpecialItem base) {
        return showGoodsClient.addSpecialItem(base);
    }
    
    @ApiOperation(value = "批量添加特价商品", notes = "批量添加特价商品")
    @RequestMapping(value = "/goods/batch", method = RequestMethod.POST)
    public Result<?> batch(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchSpecialGoods(base);
    }
    
    @ApiOperation(value="在特价中添加的单品分页",notes="在特价中添加的商单品分页")
    @RequestMapping(value = "/goods/selectPage", method = RequestMethod.POST)
    public Result<PageGrid<GoodsVo>> getGoodsPageBySpecial(@RequestBody PageParam<GoodsInfo> base){
        return showGoodsClient.getPageBySpecial(base);
    }
    
    @ApiOperation(value="在特价中添加的套餐分页",notes="在特价中添加的套餐分页")
    @RequestMapping(value = "/meal/selectPage", method = RequestMethod.POST)
    public Result<PageGrid<MealInfo>> getMealPageBySpecial(@RequestBody PageParam<MealInfo> base){
        return showGoodsClient.getMealPageBySpecial(base);
    }
}
