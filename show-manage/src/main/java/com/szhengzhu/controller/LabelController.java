package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.goods.LabelInfo;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.vo.LabelGoodsVo;
import com.szhengzhu.bean.vo.LabelMealVo;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "分类标签管理：LabelController" })
@RestController
@RequestMapping(value = "/v1/labels")
public class LabelController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加分类标签信息", notes = "添加分类标签信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<LabelInfo> save(@RequestBody LabelInfo base) {
        return showGoodsClient.saveLabel(base);
    }

    @ApiOperation(value = "修改分类标签信息", notes = "修改分类标签信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<LabelInfo> update(@RequestBody LabelInfo base) {
        return showGoodsClient.modifyLabel(base);
    }

    @ApiOperation(value = "分类标签分页列表", notes = "分类标签分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<LabelInfo>> page(@RequestBody PageParam<LabelInfo> base) {
        return showGoodsClient.labelPage(base);
    }

    @ApiOperation(value = "分类标签中批量添加商品", notes = "分类标签中批量添加商品")
    @RequestMapping(value = "/goods/batch", method = RequestMethod.POST)
    public Result<?> batch(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchLabelGoods(base);
    }

    @ApiOperation(value = "分类商品分页列表", notes = "分类商品分页列表")
    @RequestMapping(value = "/goods/page", method = RequestMethod.POST)
    public Result<PageGrid<LabelGoodsVo>> labelGoodsPage(@RequestBody PageParam<LabelGoods> base) {
        return showGoodsClient.labelGoodsPage(base);
    }
    
    @ApiOperation(value = "修改分类商品的状态和显示顺序", notes = "修改分类商品的状态和显示顺序")
    @RequestMapping(value = "/goods", method = RequestMethod.PATCH)
    public Result<LabelGoods> modifyLabelGoods(@RequestBody LabelGoods base) {
        return showGoodsClient.updateLabelGoods(base);
    }

    @ApiOperation(value = "删除分类商品", notes = "删除分类商品")
    @RequestMapping(value = "/goods/delete", method = RequestMethod.POST)
    public Result<?> deleteLabelGoods(@RequestBody LabelGoods base) {
        return showGoodsClient.deleteLabelGoods(base);
    }
    
    @ApiOperation(value ="根据id获取分类标签信息",notes="根据id获取分类标签信息")
    @RequestMapping(value = "/{markId}",method = RequestMethod.GET)
    public Result<LabelInfo> getLabelInfo(@PathVariable("markId") String markId){
        return showGoodsClient.getLabelInfo(markId);
    }
    
    @ApiOperation(value="分类标签中选择添加的商品分页列表",notes="分类标签中选择添加的商品分页列表")
    @RequestMapping(value = "/goods/selectPage", method = RequestMethod.POST)
    public Result<PageGrid<GoodsVo>> getPageByLabel(@RequestBody PageParam<GoodsInfo> base){
        return showGoodsClient.getPageByLabel(base);
    }

    @ApiOperation(value="分类标签中添加的套餐分页列表",notes="分类标签中添加的套餐分页列表")
    @RequestMapping(value="/meal/selectPage" ,method=RequestMethod.POST)
    public Result<PageGrid<MealInfo>> getMealPageByLabel(@RequestBody PageParam<MealInfo> base){
        return showGoodsClient.getMealPageByLabel(base);
    }
    
    @ApiOperation(value="分类标签套餐分页列表",notes="分类标签套餐分页列表")
    @RequestMapping(value = "/meal/page",method = RequestMethod.POST)
    public Result<PageGrid<LabelMealVo>> labelMealPage(@RequestBody PageParam<LabelGoods> base){
        return showGoodsClient.getLabelMealPage(base);
    }
}
