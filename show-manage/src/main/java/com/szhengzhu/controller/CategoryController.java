package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.CategoryInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "商品分类管理:CategoryController" })
@RestController
@RequestMapping(value = "/v1/categorys")
public class CategoryController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "类别分页信息列表", notes = "可根据不同条件获取类别分页信息列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<CategoryInfo>> page(@RequestBody PageParam<CategoryInfo> base) {
        return showGoodsClient.getCategoryPage(base);
    }

    @ApiOperation(value = "添加类别信息", notes = "添加类别信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<CategoryInfo> addCategory(@RequestBody CategoryInfo categoryInfo) {
        return showGoodsClient.addCategory(categoryInfo);
    }
    
    @ApiOperation(value = "获取分类详细信息", notes = "获取分类详细信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<CategoryInfo> getCategoryInfo(@PathVariable("markId") String markId) {
        return showGoodsClient.getCategoryInfo(markId);
    }

    @ApiOperation(value = "修改类别信息", notes = "修改品类别信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<CategoryInfo> modifyCategory(@RequestBody CategoryInfo categoryInfo) {
        return showGoodsClient.modifyCategory(categoryInfo);
    }

    @ApiOperation(value = "获取所有或有效类下拉列表", notes = "获取所有或有效获取类下拉列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Combobox>> downList(
            @RequestParam(value = "serverStatus", required = false) String serverStatus) {
        return showGoodsClient.getDwonList(serverStatus);
    }
    
    @ApiOperation(value = "获取上级类下拉列表", notes = "获取上级类下拉列表")
    @RequestMapping(value = "/supeList", method = RequestMethod.GET)
    public Result<List<Combobox>> supeList() {
        return showGoodsClient.getSuperList();
    }
}
