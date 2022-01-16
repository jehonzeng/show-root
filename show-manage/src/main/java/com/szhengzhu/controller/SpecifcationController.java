package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.SpecBatchVo;
import com.szhengzhu.bean.vo.SpecChooseBox;
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
@Api(tags = {"规格管理：SpecifcationController"})
@RestController
@RequestMapping("/v1/specification")
public class SpecifcationController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "批量添加规格属性", notes = "批量添加规格属性")
    @PostMapping(value = "/batch")
    public Result<SpecBatchVo> addBatchSpecification(@RequestBody SpecBatchVo base) {
        return showGoodsClient.addBatchSpecification(base);
    }

    @ApiOperation(value = "根据goodsId获取规格属性选项列表", notes = "根据goodsId获取规格属性选项列表")
    @GetMapping(value = "/list")
    public Result<List<SpecChooseBox>> listSpecification(@RequestParam("goodsId") @NotBlank String goodsId) {
        return showGoodsClient.listSpecification(goodsId);
    }

    @ApiOperation(value = "添加商品规格", notes = "添加商品规格")
    @PostMapping(value = "")
    public Result<SpecificationInfo> addSpecification(@RequestBody @Validated SpecificationInfo base) {
        return showGoodsClient.addSpecification(base);
    }

    @ApiOperation(value = "编辑商品规格", notes = "编辑商品规格")
    @PatchMapping(value = "")
    public Result<SpecificationInfo> modifySpecification(
            @RequestBody @Validated SpecificationInfo base) {
        return showGoodsClient.modifySpecification(base);
    }

    @ApiOperation(value = "商品规格分页", notes = "商品规格分页")
    @PostMapping(value = "/page")
    public Result<PageGrid<SpecificationInfo>> specificationPage(
            @RequestBody PageParam<SpecificationInfo> base) {
        return showGoodsClient.getSpecificationPage(base);
    }

    @ApiOperation(value = "获取关联某一类型的规格列表", notes = "获取关联某一类型的规格列表")
    @PostMapping(value = "/page/in")
    public Result<PageGrid<SpecificationInfo>> pageInByType(@RequestBody PageParam<SpecificationInfo> base) {
        return showGoodsClient.pageInByType(base);
    }

    @ApiOperation(value = "获取没有关联某一类型的规格列表", notes = "获取没有关联某一类型的规格列表")
    @PostMapping(value = "/page/notin")
    public Result<PageGrid<SpecificationInfo>> pageNotInByType(@RequestBody PageParam<SpecificationInfo> base) {
        return showGoodsClient.pageNotInByType(base);
    }
}
