package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.CategoryInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.feign.ShowGoodsClient;
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
@Api(tags = { "类别管理:CategoryController" })
@RestController
@RequestMapping(value = "/v1/categorys")
public class CategoryController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "可根据不同条件获取类别分页信息列表", notes = "可根据不同条件获取类别分页信息列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<CategoryInfo>> page(@RequestBody PageParam<CategoryInfo> base) {
        return showGoodsClient.getCategoryPage(base);
    }

    @ApiOperation(value = "添加类别信息", notes = "添加类别信息")
    @PostMapping(value = "")
    public Result<CategoryInfo> addCategory(@RequestBody @Validated CategoryInfo categoryInfo) {
        return showGoodsClient.addCategory(categoryInfo);
    }
    
    @ApiOperation(value = "获取类别信息", notes = "获取类别信息")
    @GetMapping(value = "/{markId}")
    public Result<CategoryInfo> getCategoryInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.getCategoryInfo(markId);
    }

    @ApiOperation(value = "修改类别信息", notes = "修改品类别信息")
    @PatchMapping(value = "")
    public Result<CategoryInfo> modifyCategory(@RequestBody @Validated CategoryInfo categoryInfo) {
        return showGoodsClient.modifyCategory(categoryInfo);
    }

    @ApiOperation(value = "获取所有或有效类下拉列表", notes = "获取所有或有效获取类下拉列表")
    @GetMapping(value = "/list")
    public Result<List<Combobox>> downList(
            @RequestParam(value = "serverStatus", required = false) String serverStatus) {
        return showGoodsClient.getDwonList(serverStatus);
    }
}
