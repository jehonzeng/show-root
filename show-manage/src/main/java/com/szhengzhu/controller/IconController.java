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
import com.szhengzhu.bean.goods.IconInfo;
import com.szhengzhu.bean.goods.IconItem;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.vo.IconGoodsVo;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "图标管理：IconController" })
@RestController
@RequestMapping(value = "/v1/icons")
public class IconController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加保存图标信息", notes = "添加保存图标信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<IconInfo> save(@RequestBody IconInfo base) {
        return showGoodsClient.saveIcon(base);
    }

    @ApiOperation(value = "修改图标信息", notes = "修改图标信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<IconInfo> update(@RequestBody IconInfo base) {
        return showGoodsClient.modifyIcon(base);
    }

    @ApiOperation(value = "图标信息列表", notes = "图标信息列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<IconInfo>> iconPage(@RequestBody PageParam<IconInfo> base) {
        return showGoodsClient.iconPage(base);
    }
    
    @ApiOperation(value = "根据id图标信息", notes = "根据id图标信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<IconInfo> iconInfo(@PathVariable("markId") String markId) {
        return showGoodsClient.getIconInfo(markId);
    }
    
    @ApiOperation(value = "没有加入该商品的有效图标列表",notes="没有加入该商品的有效图标列表")
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listIcon(@RequestParam("goodsId") String goodsId) {
        return showGoodsClient.listIconByGoods(goodsId);
    }
    
    @ApiOperation(value = "图标商品分页列表", notes = "图标商品分页列表")
    @RequestMapping(value = "/item/page", method = RequestMethod.POST)
    public Result<PageGrid<IconGoodsVo>> iconItemPage(@RequestBody PageParam<IconItem> base) {
        return showGoodsClient.iconItemPage(base);
    }
    
    @ApiOperation(value = "根据id取消商品图标", notes = "根据id取消商品图标")
    @RequestMapping(value = "/item/delete", method = RequestMethod.POST)
    public Result<?> deleteItem(@RequestBody IconItem base) {
        return showGoodsClient.deleteIconItem(base);
    }
    
    @ApiOperation(value = "商品添加图标",notes="商品添加图标")
    @RequestMapping(value = "/item",method =RequestMethod.POST)
    public  Result<IconItem> addIconItem(@RequestBody IconItem base){
        return showGoodsClient.addIconItem(base);
    }
    
    @ApiOperation(value = "批量添加图标商品", notes = "批量添加图标商品")
    @RequestMapping(value = "/goods/batch", method = RequestMethod.POST)
    public Result<?> batch(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchIconGoods(base);
    }
    
    @ApiOperation(value="添加的商品分页",notes="添加的商品分页")
    @RequestMapping(value = "/goods/selectPage", method = RequestMethod.POST)
    public Result<PageGrid<GoodsVo>> getPageByIcon(@RequestBody PageParam<GoodsInfo> base){
        return showGoodsClient.getPageByIcon(base);
    }
    
    @ApiOperation(value="在图标中添加的套餐分页",notes="在图标中添加的套餐分页")
    @RequestMapping(value = "/meal/selectPage", method = RequestMethod.POST)
    public Result<PageGrid<MealInfo>> getMealPageByIcon(@RequestBody PageParam<MealInfo> base){
        return showGoodsClient.getMealPageByIcon(base);
    }
}
