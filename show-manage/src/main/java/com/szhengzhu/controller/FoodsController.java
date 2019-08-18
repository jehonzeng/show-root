package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.FoodsInfo;
import com.szhengzhu.bean.goods.FoodsItem;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsFoodVo;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = {"食材管理：FoodsController"})
@RestController
@RequestMapping(value = "/v1/foods")
public class FoodsController {
    
    @Resource
    private ShowGoodsClient showGoodsClient;
    
    @ApiOperation(value = "添加食材信息", notes = "添加食材信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<FoodsInfo> addFood(@RequestBody FoodsInfo base){
        return showGoodsClient.addFood(base);
    }
    
    @ApiOperation(value = "修改食材信息", notes = "修改食材信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<FoodsInfo> modifyFood(@RequestBody FoodsInfo base){
        return showGoodsClient.modifyFood(base);
    }
    
    @ApiOperation(value = "食材分页", notes = "根据不同条件获取食材分页")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<FoodsInfo>> foodPage(@RequestBody PageParam<FoodsInfo> base){
        return showGoodsClient.foodPage(base);
    }
    
    @ApiOperation(value = "根据id获取食材信息", notes = "根据id获取食材信息")
    @RequestMapping(value = "/{markId}",method = RequestMethod.GET)
    public Result<FoodsInfo> foodsInfo(@PathVariable("markId") String markId){
        return showGoodsClient.foodsInfo(markId);
    }
    
    @ApiOperation(value = "根据商品id获取未选择过的食材列表", notes = "根据商品id获取未选择过的食材列表")
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listFoodWithoutGoods(@RequestParam("goodsId") String goodsId) {
        return showGoodsClient.listFoodWithoutGoods(goodsId);
    }
    
    @ApiOperation(value = "食材列表", notes = "食材列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Combobox>> listFood() {
        return showGoodsClient.listFood();
    }

    @ApiOperation(value = "商品中批量添加需要食材", notes = "商品中批量添加需要食材")
    @RequestMapping(value = "/item/batch", method = RequestMethod.POST)
    public Result<?> addBatchFoodsItem(@RequestBody FoodsItem base){
        return showGoodsClient.addBatchFoodsItem(base);
    }

    @ApiOperation(value = "商品的食材列表", notes = "商品的食材列表")
    @RequestMapping(value = "/item/page",method = RequestMethod.POST)
    public Result<PageGrid<GoodsFoodVo>> foodsItemPage(@RequestBody PageParam<FoodsItem> base){
        return showGoodsClient.foodsItemPage(base);
    }
    
    @ApiOperation(value = "删除商品的食材信息", notes = "删除商品的食材信息")
    @RequestMapping(value = "/item/{markId}",method = RequestMethod.DELETE)
    public Result<?> deleteFoodsItem(@PathVariable("markId")String markId){
        return showGoodsClient.deleteFoodsItem(markId);
    }
    
    @ApiOperation(value = "修改商品的食材信息", notes = "修改商品的食材信息")
    @RequestMapping(value ="/item",method = RequestMethod.PATCH)
    public Result<FoodsItem> updateFoodsItem(@RequestBody FoodsItem base){
        return showGoodsClient.updateFoodsItem(base);
    }
    
}
