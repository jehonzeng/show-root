package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.BrandInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "品牌管理:BrandController" })
@RestController
@RequestMapping(value = "/v1/brands")
public class BrandController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "获取品牌分页列表", notes = "获取品牌分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<BrandInfo>> page(@RequestBody PageParam<BrandInfo> base) {
        return showGoodsClient.getBrandPage(base);

    }

    @ApiOperation(value = "添加品牌信息", notes = "添加品牌信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<BrandInfo> addBrand(@RequestBody BrandInfo brandInfo) {
        return showGoodsClient.addBrand(brandInfo);

    }

    @ApiOperation(value = "修改品牌信息", notes = "修改品牌信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<BrandInfo> modifyBrand(@RequestBody BrandInfo brandInfo) {
        return showGoodsClient.modifyBrand(brandInfo);

    }

    @ApiOperation(value = "根据id获取品牌信息", notes = "根据id获取品牌信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<BrandInfo> getBrandInfo(@PathVariable("markId") String markId) {
        return showGoodsClient.getBrandInfo(markId);
    }
    
    @ApiOperation(value = "获取品牌下拉列表", notes = "获取品牌下拉列表")
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listCombobox() {
        return showGoodsClient.listBrandCombobox();
    }

}
