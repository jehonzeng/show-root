package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.IconInfo;
import com.szhengzhu.bean.goods.IconItem;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.vo.IconGoodsVo;
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
@Api(tags = {"图标管理：IconController"})
@RestController
@RequestMapping(value = "/v1/icons")
public class IconController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加保存图标信息", notes = "添加保存图标信息")
    @PostMapping(value = "")
    public Result<IconInfo> save(@RequestBody @Validated IconInfo base) {
        return showGoodsClient.saveIcon(base);
    }

    @ApiOperation(value = "修改图标信息", notes = "修改图标信息")
    @PatchMapping(value = "")
    public Result<IconInfo> update(@RequestBody @Validated IconInfo base) {
        return showGoodsClient.modifyIcon(base);
    }

    @ApiOperation(value = "图标信息列表", notes = "图标信息列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<IconInfo>> iconPage(@RequestBody PageParam<IconInfo> base) {
        return showGoodsClient.iconPage(base);
    }

    @ApiOperation(value = "根据id图标信息", notes = "根据id图标信息")
    @GetMapping(value = "/{markId}")
    public Result<IconInfo> iconInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.getIconInfo(markId);
    }

    @ApiOperation(value = "没有加入该商品的有效图标列表", notes = "没有加入该商品的有效图标列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listIcon(@RequestParam("goodsId") @NotBlank String goodsId) {
        return showGoodsClient.listIconByGoods(goodsId);
    }

    @ApiOperation(value = "图标商品分页列表", notes = "图标商品分页列表")
    @PostMapping(value = "/item/page")
    public Result<PageGrid<IconGoodsVo>> iconItemPage(@RequestBody PageParam<IconItem> base) {
        return showGoodsClient.iconItemPage(base);
    }

    @ApiOperation(value = "根据id取消商品图标", notes = "根据id取消商品图标")
    @PostMapping(value = "/item/delete")
    public Result deleteItem(@RequestBody IconItem base) {
        return showGoodsClient.deleteIconItem(base);
    }

    @ApiOperation(value = "商品添加图标", notes = "商品添加图标")
    @PostMapping(value = "/item")
    public Result<IconItem> addIconItem(@RequestBody IconItem base) {
        return showGoodsClient.addIconItem(base);
    }

    @ApiOperation(value = "批量添加图标商品", notes = "批量添加图标商品")
    @PostMapping(value = "/goods/batch")
    public Result batch(@RequestBody BatchVo base) {
        return showGoodsClient.addBatchIconGoods(base);
    }

    @ApiOperation(value = "添加的商品分页", notes = "添加的商品分页")
    @PostMapping(value = "/goods/selectPage")
    public Result<PageGrid<GoodsVo>> getPageByIcon(@RequestBody PageParam<GoodsInfo> base) {
        return showGoodsClient.getPageByIcon(base);
    }

    @ApiOperation(value = "在图标中添加的套餐分页", notes = "在图标中添加的套餐分页")
    @PostMapping(value = "/meal/selectPage")
    public Result<PageGrid<MealInfo>> getMealPageByIcon(@RequestBody PageParam<MealInfo> base) {
        return showGoodsClient.getMealPageByIcon(base);
    }
}
