package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowActivityClient;
import com.szhengzhu.bean.activity.GiftInfo;
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
@Api(tags = {"商城礼物管理:GiftController"})
@RestController
@RequestMapping(value = "/v1/gifts")
public class GiftController {

    @Resource
    private ShowActivityClient showActivityClient;

    @ApiOperation(value = "获取奖品分页列表", notes = "获取奖品分页列表")
    @PostMapping(value = "/page")
    public Result<?> getGiftPage(@RequestBody PageParam<GiftInfo> base) {
        return showActivityClient.getGiftPage(base);
    }

    @ApiOperation(value = "添加奖品信息", notes = "添加奖品信息")
    @PostMapping(value = "")
    public Result<GiftInfo> addGift(@RequestBody @Validated GiftInfo base) {
        return showActivityClient.addGift(base);
    }

    @ApiOperation(value = "修改奖品信息", notes = "修改奖品信息")
    @PatchMapping(value = "")
    public Result<GiftInfo> updateGift(@RequestBody @Validated GiftInfo base) {
        return showActivityClient.updateGift(base);
    }

    @ApiOperation(value = "根据id获取奖品信息", notes = "根据id获取奖品信息")
    @GetMapping(value = "/{markId}")
    public Result<GiftInfo> getGiftInfo(@PathVariable("markId") @NotBlank String markId) {
        return showActivityClient.getGiftInfo(markId);
    }

    @ApiOperation(value = "获取奖品下拉列表", notes = "获取奖品下拉列表")
    @GetMapping(value = "/combobox")
    public Result<?> getGiftCombobox() {
        return showActivityClient.getGiftCombobox();
    }
}
