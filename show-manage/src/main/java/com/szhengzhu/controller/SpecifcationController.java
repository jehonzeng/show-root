package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.SpecBatchVo;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags= {"规格管理：SpecifcationController"})
@RestController
@RequestMapping("/v1/specification")
public class SpecifcationController {

    @Resource
    private ShowGoodsClient showGoodsClient;
    
    @ApiOperation(value = "批量添加规格属性", notes = "批量添加规格属性")
    @RequestMapping(value = "/batch",method = RequestMethod.POST)
    public Result<SpecBatchVo> addBatchSpecification(@RequestBody SpecBatchVo base){
        return showGoodsClient.addBatchSpecification(base);
    }
    
    @ApiOperation(value = "根据goodsId获取规格属性选项列表", notes = "根据goodsId获取规格属性选项列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result<List<SpecChooseBox>> listSpecification(@RequestParam("goodsId") String goodsId){
        return showGoodsClient.listSpecification(goodsId);
    }
    
    @ApiOperation(value = "添加商品规格", notes = "添加商品规格")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<SpecificationInfo> addSpecification(@RequestBody SpecificationInfo base) {
        return showGoodsClient.addSpecification(base);
    }
    
    @ApiOperation(value = "编辑商品规格", notes = "编辑商品规格")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<SpecificationInfo> modifySpecification(
            @RequestBody SpecificationInfo base) {
        return showGoodsClient.modifySpecification(base);
    }

    @ApiOperation(value = "商品规格分页", notes = "商品规格分页")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<SpecificationInfo>> specificationPage(
            @RequestBody PageParam<SpecificationInfo> base) {
        return showGoodsClient.getSpecificationPage(base);
    }
    
    @ApiOperation(value = "获取关联某一类型的规格列表", notes = "获取关联某一类型的规格列表")
    @RequestMapping(value = "/page/in", method = RequestMethod.POST)
    public Result<PageGrid<SpecificationInfo>> pageInByType(@RequestBody PageParam<SpecificationInfo> base) {
        return showGoodsClient.pageInByType(base);
    }
    
    @ApiOperation(value = "获取没有关联某一类型的规格列表", notes = "获取没有关联某一类型的规格列表")
    @RequestMapping(value = "/page/notin", method = RequestMethod.POST)
    public Result<PageGrid<SpecificationInfo>> pageNotInByType(@RequestBody PageParam<SpecificationInfo> base) {
        return showGoodsClient.pageNotInByType(base);
    }
}
