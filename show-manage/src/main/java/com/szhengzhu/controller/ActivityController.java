package com.szhengzhu.controller;

import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.bean.activity.*;
import com.szhengzhu.bean.vo.ActivityModel;
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
 * @author Terry
 */
@Validated
@Api(tags = {"商城活动管理:ActivityController"})
@RestController
@RequestMapping(value = "/v1/acts")
public class ActivityController {

    @Resource
    private ShowActivityClient showActivityClient;

    @ApiOperation(value = "获取活动分页列表", notes = "获取活动分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<ActivityModel>> getActivityPage(@RequestBody PageParam<ActivityInfo> base) {
        return showActivityClient.getActivityPage(base);
    }

    @ApiOperation(value = "添加活动信息", notes = "添加活动信息")
    @PostMapping(value = "")
    public Result<ActivityInfo> addAct(@RequestBody @Validated ActivityInfo base) {
        return showActivityClient.addAct(base);
    }

    @ApiOperation(value = "修改活动信息", notes = "修改活动信息")
    @PatchMapping(value = "")
    public Result<ActivityInfo> updateAct(@RequestBody @Validated ActivityInfo base) {
        return showActivityClient.updateAct(base);
    }

    @ApiOperation(value = "获取活动信息", notes = "获取活动信息")
    @GetMapping(value = "/{markId}")
    public Result<ActivityInfo> getActInfo(@PathVariable("markId") @NotBlank String markId) {
        return showActivityClient.getActivityInfo(markId);
    }

    @ApiOperation(value = "获取活动奖品分页列表", notes = "获取活动奖品分页列表")
    @PostMapping(value = "/gift/page")
    public Result<PageGrid<ActivityGift>> getActGiftPage(@RequestBody PageParam<ActivityGift> base) {
        return showActivityClient.getActGiftPage(base);
    }

    @ApiOperation(value = "添加活动奖品分页", notes = "添加品牌")
    @PostMapping(value = "/gift")
    public Result<ActivityGift> addActGift(@RequestBody @Validated ActivityGift base) {
        return showActivityClient.addActGift(base);
    }

    @ApiOperation(value = "修改活动奖品", notes = "修改活动奖品")
    @PatchMapping(value = "/gift")
    public Result<ActivityGift> updateActGift(@RequestBody @Validated ActivityGift base) {
        return showActivityClient.updateActGift(base);
    }

    @ApiOperation(value = "获取品牌分页列表", notes = "获取品牌分页列表")
    @GetMapping(value = "/gift/{markId}")
    public Result<ActivityGift> getActGiftById(@PathVariable("markId") @NotBlank String markId) {
        return showActivityClient.getActGiftById(markId);
    }

    @ApiOperation(value = "编辑活动规则", notes = "编辑活动规则")
    @PatchMapping(value = "/rule")
    public Result<ActivityRule> updateActRule(@RequestBody ActivityRule base) {
        return showActivityClient.updateActRule(base);
    }

    @ApiOperation(value = "获取活动记录分页列表", notes = "获取活动记录分页列表")
    @PostMapping(value = "/history/page")
    public Result<?> getHistoryPage(@RequestBody PageParam<ActivityHistory> base) {
        return showActivityClient.getHistoryPage(base);
    }

    @ApiOperation(value = "添加助力分数生成规则", notes = "添加助力分数生成规则")
    @PostMapping(value = "/help/rule")
    public Result addHelpPointRule(@RequestBody @Validated HelpLimit base) {
        return showActivityClient.addHelpPointRule(base);
    }

    @ApiOperation(value = "修改助力分数生成规则", notes = "修改助力分数生成规则")
    @PatchMapping(value = "/help/rule")
    public Result<HelpLimit> updateHelpPointRule(@RequestBody @Validated HelpLimit base) {
        return showActivityClient.updateHelpPointRule(base);
    }

    @ApiOperation(value = "助力分数生成规则列表", notes = "助力分数生成规则列表")
    @PostMapping(value = "/help/rulePage")
    public Result<PageGrid<HelpLimit>> getHelpPointRulePage(@RequestBody PageParam<HelpLimit> base) {
        return showActivityClient.getHelpPointRulePage(base);
    }
}
