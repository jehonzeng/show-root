package com.szhengzhu.controller;

import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.bean.activity.ScanWin;
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
@Api(tags = {"扫码中奖：ScanWinController"})
@RestController
@RequestMapping("/v1/scanwin")
public class ScanWinController {

    @Resource
    private ShowActivityClient showActivityClient;

    @ApiOperation(value = "获取扫码中奖活动分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<ScanWin>> page(@RequestBody PageParam<ScanWin> page) {
        return showActivityClient.pageScanWin(page);
    }

    @ApiOperation(value = "获取扫码中奖活动详细信息")
    @GetMapping(value = "/{markId}")
    public Result<ScanWin> getInfo(@PathVariable("markId") @NotBlank String markId) {
        return showActivityClient.getScanWinInfo(markId);
    }

    @ApiOperation(value = "添加扫码中奖活动信息")
    @PostMapping(value = "")
    public Result add(@RequestBody @Validated ScanWin win) {
        return showActivityClient.addScanWin(win);
    }

    @ApiOperation(value = "修改扫码中奖活动信息")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated ScanWin win) {
        return showActivityClient.modifyScanWin(win);
    }
}
