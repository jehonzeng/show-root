package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.goods.FoodsInfo;
import com.szhengzhu.bean.goods.FoodsItem;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsFoodVo;
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
@Api(tags = {"食材管理：FoodsController"})
@RestController
@RequestMapping(value = "/v1/foods")
public class FoodsController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加食材信息", notes = "添加食材信息")
    @PostMapping(value = "")
    public Result<FoodsInfo> addFood(@RequestBody @Validated FoodsInfo base) {
        return showGoodsClient.addFood(base);
    }

    @ApiOperation(value = "修改食材信息", notes = "修改食材信息")
    @PatchMapping(value = "")
    public Result<FoodsInfo> modifyFood(@RequestBody @Validated FoodsInfo base) {
        return showGoodsClient.modifyFood(base);
    }

    @ApiOperation(value = "食材分页", notes = "根据不同条件获取食材分页")
    @PostMapping(value = "/page")
    public Result<PageGrid<FoodsInfo>> foodPage(@RequestBody PageParam<FoodsInfo> base) {
        return showGoodsClient.foodPage(base);
    }

    @ApiOperation(value = "根据id获取食材信息", notes = "根据id获取食材信息")
    @GetMapping(value = "/{markId}")
    public Result<FoodsInfo> foodsInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.foodsInfo(markId);
    }

    @ApiOperation(value = "根据商品id获取未选择过的食材列表", notes = "根据商品id获取未选择过的食材列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listFoodWithoutGoods(@RequestParam("goodsId") @NotBlank String goodsId) {
        return showGoodsClient.listFoodWithoutGoods(goodsId);
    }

    @ApiOperation(value = "食材列表", notes = "食材列表")
    @GetMapping(value = "/list")
    public Result<List<Combobox>> listFood() {
        return showGoodsClient.listFood();
    }

    @ApiOperation(value = "商品中批量添加需要食材", notes = "商品中批量添加需要食材")
    @PostMapping(value = "/item/batch")
    public Result addBatchFoodsItem(@RequestBody @Validated FoodsItem base) {
        return showGoodsClient.addBatchFoodsItem(base);
    }

    @ApiOperation(value = "商品的食材列表", notes = "商品的食材列表")
    @PostMapping(value = "/item/page")
    public Result<PageGrid<GoodsFoodVo>> foodsItemPage(@RequestBody PageParam<FoodsItem> base) {
        return showGoodsClient.foodsItemPage(base);
    }

    @ApiOperation(value = "删除商品的食材信息", notes = "删除商品的食材信息")
    @DeleteMapping(value = "/item/{markId}")
    public Result deleteFoodsItem(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.deleteFoodsItem(markId);
    }

    @ApiOperation(value = "修改商品的食材信息", notes = "修改商品的食材信息")
    @PatchMapping(value = "/item")
    public Result<FoodsItem> updateFoodsItem(@RequestBody @Validated FoodsItem base) {
        return showGoodsClient.updateFoodsItem(base);
    }

}
