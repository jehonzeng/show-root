package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.bean.base.ScanReply;
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
@Api(tags = {"扫码回复管理：ScanReplyController"})
@RestController
@RequestMapping("/v1/scanreply")
public class ScanReplyController {

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "获取扫码回复详细信息", notes = "获取扫码回复详细信息")
    @GetMapping(value = "/{markId}")
    public Result<ScanReply> getInfo(@PathVariable("markId") @NotBlank String markId) {
        return showBaseClient.getScanReplyInfo(markId);
    }

    @ApiOperation(value = "获取扫码回复分页列表", notes = "获取扫码回复分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<ScanReply>> page(@RequestBody PageParam<ScanReply> page) {
        return showBaseClient.pageScanReply(page);
    }

    @ApiOperation(value = "添加扫码回复信息", notes = "添加扫码回复信息")
    @PostMapping(value = "")
    public Result add(@RequestBody @Validated ScanReply replyInfo) {
        return showBaseClient.addScanReply(replyInfo);
    }

    @ApiOperation(value = "修改扫码回复信息", notes = "修改扫码回复信息")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated ScanReply replyInfo) {
        return showBaseClient.modifyScanReply(replyInfo);
    }
}
