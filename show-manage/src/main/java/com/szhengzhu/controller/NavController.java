package com.szhengzhu.controller;

import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.bean.base.NavInfo;
import com.szhengzhu.bean.base.NavItem;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"商城首页管理:NavController"})
@RestController
@RequestMapping(value = "/v1/navs")
public class NavController {

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "添加标识信息", notes = "添加标识信息")
    @PostMapping(value = "")
    public Result<NavInfo> add(@RequestBody @Validated NavInfo base) {
        return showBaseClient.addNav(base);
    }

    @ApiOperation(value = "修改标识信息", notes = "修改标识信息")
    @PatchMapping(value = "")
    public Result<NavInfo> modify(@RequestBody @Validated NavInfo base) {
        return showBaseClient.modifyNav(base);
    }

    @ApiOperation(value = "获取标识信息分页列表", notes = "获取标识信息分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<NavInfo>> page(@RequestBody PageParam<NavInfo> base) {
        return showBaseClient.page(base);
    }

    @ApiOperation(value = "添加主题信息", notes = "添加主题信息")
    @PostMapping(value = "/item")
    public Result<NavItem> addItem(@RequestBody @Validated NavItem base) {
        return showBaseClient.addItem(base);
    }

    @ApiOperation(value = "获取主题信息分页列表", notes = "获取主题信息分页列表")
    @PostMapping(value = "/item/page")
    public Result<PageGrid<NavItem>> itemPage(@RequestBody PageParam<NavItem> base) {
        return showBaseClient.itemPage(base);
    }

    @ApiOperation(value = "修改主题信息", notes = "修改主题信息")
    @PatchMapping(value = "/item")
    public Result<NavItem> modifyItem(@RequestBody @Validated NavItem base) {
        return showBaseClient.modifyItem(base);
    }

    @ApiOperation(value = "根据id获取主题信息", notes = "根据id获取主题信息")
    @GetMapping(value = "/item/{markId}")
    public Result<NavItem> getItem(@PathVariable("markId") @NotBlank String markId) {
        return showBaseClient.getItem(markId);
    }

    @ApiOperation(value = "删除主题信息", notes = "删除主题信息")
    @DeleteMapping(value = "/item/{markId}")
    public Result deleteItem(@PathVariable("markId") @NotBlank String markId) {
        return showBaseClient.deleteItem(markId);
    }
}
