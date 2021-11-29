package com.szhengzhu.controller;

import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.bean.base.ActionInfo;
import com.szhengzhu.bean.base.ActionItem;
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
@Api(tags = {"事件管理：ActionController"})
@RestController
@RequestMapping("/v1/action")
public class ActionController {

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "获取事件组分页列表", notes = "获取事件组分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<ActionInfo>> pageAction(@RequestBody PageParam<ActionInfo> page) {
        return showBaseClient.pageAction(page);
    }

    @ApiOperation(value = "添加事件组信息", notes = "添加事件组信息")
    @PostMapping(value = "")
    public Result addAction(@RequestBody @Validated ActionInfo actionInfo) {
        return showBaseClient.addAction(actionInfo);
    }

    @ApiOperation(value = "修改事件组信息", notes = "修改事件组信息")
    @PatchMapping(value = "")
    public Result modifyAction(@RequestBody @Validated ActionInfo actionInfo) {
        return showBaseClient.modifyAction(actionInfo);
    }

    @ApiOperation(value = "获取事件分页列表", notes = "获取事件分页列表")
    @PostMapping(value = "/item/page")
    public Result<PageGrid<ActionItem>> pageItem(@RequestBody PageParam<ActionItem> page) {
        return showBaseClient.pageActionItem(page);
    }

    @GetMapping(value = "/item/{markId}")
    public Result<ActionItem> getItemInfo(@PathVariable("markId") @NotBlank String markId) {
        return showBaseClient.getActionItemInfo(markId);
    }

    @ApiOperation(value = "添加事件信息", notes = "添加事件信息")
    @PostMapping(value = "/item")
    public Result addItem(@RequestBody @Validated ActionItem item) {
        return showBaseClient.addActionItem(item);
    }

    @ApiOperation(value = "修改事件信息", notes = "修改事件信息")
    @PatchMapping(value = "/item")
    public Result modifyItem(@RequestBody @Validated ActionItem item) {
        return showBaseClient.modifyActionItem(item);
    }

    @ApiOperation(value = "删除事件", notes = "删除事件")
    @DeleteMapping(value = "/item/{markId}")
    public Result deleteItem(@PathVariable("markId") @NotBlank String markId) {
        return showBaseClient.deleteActionItem(markId);
    }
}
