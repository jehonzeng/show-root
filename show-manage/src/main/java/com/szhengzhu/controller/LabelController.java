package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.goods.LabelInfo;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.vo.LabelGoodsVo;
import com.szhengzhu.bean.vo.LabelMealVo;
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
@Api(tags = {"分类标签管理：LabelController"})
@RestController
@RequestMapping(value = "/v1/labels")
public class LabelController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加分类标签信息", notes = "添加分类标签信息")
    @PostMapping(value = "")
    public Result<LabelInfo> save(@RequestBody @Validated LabelInfo base) {
        return showGoodsClient.saveLabel(base);
    }

    @ApiOperation(value = "修改分类标签信息", notes = "修改分类标签信息")
    @PatchMapping(value = "")
    public Result<LabelInfo> update(@RequestBody @Validated LabelInfo base) {
        return showGoodsClient.modifyLabel(base);
    }

    @ApiOperation(value = "分类标签分页列表", notes = "分类标签分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<LabelInfo>> page(@RequestBody PageParam<LabelInfo> base) {
        return showGoodsClient.labelPage(base);
    }

    @ApiOperation(value = "分类标签中批量添加商品", notes = "分类标签中批量添加商品")
    @PostMapping(value = "/goods/batch")
    public Result batch(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchLabelGoods(base);
    }

    @ApiOperation(value = "分类商品分页列表", notes = "分类商品分页列表")
    @PostMapping(value = "/goods/page")
    public Result<PageGrid<LabelGoodsVo>> labelGoodsPage(@RequestBody PageParam<LabelGoods> base) {
        return showGoodsClient.labelGoodsPage(base);
    }

    @ApiOperation(value = "修改分类商品的状态和显示顺序", notes = "修改分类商品的状态和显示顺序")
    @PatchMapping(value = "/goods")
    public Result<LabelGoods> modifyLabelGoods(@RequestBody LabelGoods base) {
        return showGoodsClient.updateLabelGoods(base);
    }

    @ApiOperation(value = "删除分类商品", notes = "删除分类商品")
    @PostMapping(value = "/goods/delete")
    public Result deleteLabelGoods(@RequestBody LabelGoods base) {
        return showGoodsClient.deleteLabelGoods(base);
    }

    @ApiOperation(value = "根据id获取分类标签信息", notes = "根据id获取分类标签信息")
    @GetMapping(value = "/{markId}")
    public Result<LabelInfo> getLabelInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.getLabelInfo(markId);
    }

    @ApiOperation(value = "分类标签中选择添加的商品分页列表", notes = "分类标签中选择添加的商品分页列表")
    @PostMapping(value = "/goods/selectPage")
    public Result<PageGrid<GoodsVo>> getPageByLabel(@RequestBody PageParam<GoodsInfo> base) {
        return showGoodsClient.getPageByLabel(base);
    }

    @ApiOperation(value = "分类标签中添加的套餐分页列表", notes = "分类标签中添加的套餐分页列表")
    @PostMapping(value = "/meal/selectPage")
    public Result<PageGrid<MealInfo>> getMealPageByLabel(@RequestBody PageParam<MealInfo> base) {
        return showGoodsClient.getMealPageByLabel(base);
    }

    @ApiOperation(value = "分类标签套餐分页列表", notes = "分类标签套餐分页列表")
    @PostMapping(value = "/meal/page")
    public Result<PageGrid<LabelMealVo>> labelMealPage(@RequestBody PageParam<LabelGoods> base) {
        return showGoodsClient.getLabelMealPage(base);
    }
}
