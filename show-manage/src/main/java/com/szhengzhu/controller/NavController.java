package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.NavInfo;
import com.szhengzhu.bean.base.NavItem;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "首页导航管理:NavController" })
@RestController
@RequestMapping(value = "/v1/navs")
public class NavController {
    
    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "添加标识信息", notes = "添加标识信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<NavInfo> add(@RequestBody NavInfo base) {
        return showBaseClient.addNav(base);
    }

    @ApiOperation(value = "修改标识信息", notes = "修改标识信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<NavInfo> modify(@RequestBody NavInfo base) {
        return showBaseClient.modifyNav(base);
    }

    @ApiOperation(value = "获取标识信息分页列表", notes = "获取标识信息分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<NavInfo>> page(@RequestBody PageParam<NavInfo> base) {
        return showBaseClient.page(base);
    }

    @ApiOperation(value = "添加主题信息", notes = "添加主题信息")
    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public Result<NavItem> addItem(@RequestBody NavItem base) {
        return showBaseClient.addItem(base);
    }

    @ApiOperation(value = "获取主题信息分页列表", notes = "获取主题信息分页列表")
    @RequestMapping(value = "/item/page", method = RequestMethod.POST)
    public Result<PageGrid<NavItem>> itemPage(@RequestBody PageParam<NavItem> base) {
        return showBaseClient.itemPage(base);
    }

    @ApiOperation(value = "修改主题信息", notes = "修改主题信息")
    @RequestMapping(value = "/item", method = RequestMethod.PATCH)
    public Result<NavItem> modifyItem(@RequestBody NavItem base) {
        return showBaseClient.modifyItem(base);
    }

    @ApiOperation(value = "根据id获取主题信息", notes = "根据id获取主题信息")
    @RequestMapping(value = "/item/{markId}", method = RequestMethod.GET)
    public Result<NavItem> getItem(@PathVariable("markId") String markId) {
        return showBaseClient.getItem(markId);
    }
    
    @ApiOperation(value="删除主题信息",notes = "删除主题信息")
    @RequestMapping(value = "/item/{markId}", method = RequestMethod.DELETE)
    public Result<?> deleteItem(@PathVariable("markId") String markId) {
        return showBaseClient.deleteItem(markId);
    }
}
