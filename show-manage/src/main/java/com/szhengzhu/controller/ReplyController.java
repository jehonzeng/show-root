package com.szhengzhu.controller;

import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.bean.base.ReplyInfo;
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
@Api(tags = {"自动回复管理：ReplyController"})
@RestController
@RequestMapping("/v1/reply")
public class ReplyController {

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "获取自动回复信息分页列表", notes = "获取自动回复信息分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<ReplyInfo>> page(@RequestBody PageParam<ReplyInfo> page) {
        return showBaseClient.pageReply(page);
    }

    @ApiOperation(value = "获取自动回复信息", notes = "获取自动回复信息")
    @GetMapping(value = "/{markId}")
    public Result<ReplyInfo> getInfo(@PathVariable("markId") @NotBlank String markId) {
        return showBaseClient.getReplyInfo(markId);
    }

    @ApiOperation(value = "添加自动回复信息", notes = "添加自动回复信息")
    @PostMapping(value = "")
    public Result add(@RequestBody @Validated ReplyInfo replyInfo) {
        return showBaseClient.addReply(replyInfo);
    }

    @ApiOperation(value = "修改自动回复信息", notes = "修改自动回复信息")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated ReplyInfo replyInfo) {
        return showBaseClient.modifyReply(replyInfo);
    }
}
