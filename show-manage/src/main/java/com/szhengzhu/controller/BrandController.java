package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.goods.BrandInfo;
import com.szhengzhu.bean.vo.Combobox;
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
@Api(tags = { "品牌管理:BrandController" })
@RestController
@RequestMapping(value = "/v1/brands")
public class BrandController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "获取品牌分页列表", notes = "获取品牌分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<BrandInfo>> page(@RequestBody PageParam<BrandInfo> base) {
        return showGoodsClient.getBrandPage(base);

    }

    @ApiOperation(value = "添加品牌信息", notes = "添加品牌信息")
    @PostMapping(value = "")
    public Result<BrandInfo> addBrand(@RequestBody @Validated BrandInfo brandInfo) {
        return showGoodsClient.addBrand(brandInfo);

    }

    @ApiOperation(value = "修改品牌信息", notes = "修改品牌信息")
    @PatchMapping(value = "")
    public Result<BrandInfo> modifyBrand(@RequestBody @Validated BrandInfo brandInfo) {
        return showGoodsClient.modifyBrand(brandInfo);

    }

    @ApiOperation(value = "根据id获取品牌信息", notes = "根据id获取品牌信息")
    @GetMapping(value = "/{markId}")
    public Result<BrandInfo> getBrandInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.getBrandInfo(markId);
    }
    
    @ApiOperation(value = "获取品牌下拉列表", notes = "获取品牌下拉列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listCombobox() {
        return showGoodsClient.listBrandCombobox();
    }

}
