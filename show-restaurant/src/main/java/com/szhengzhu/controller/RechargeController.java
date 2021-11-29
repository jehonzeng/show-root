package com.szhengzhu.controller;

import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.bean.member.RechargeRule;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = { "充值模板：RechargeController" })
@RestController
@RequestMapping(value = "/v1/recharge")
public class RechargeController {

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "添加充值活动信息")
    @PostMapping(value = "")
    public Result addRecharge(HttpSession session, @RequestBody @Validated RechargeRule base) {
        String employId = (String) session.getAttribute(Contacts.RESTAURANT_USER);
        base.setCreator(employId);
        return showMemberClient.addRechargeRule(base);
    }

    @ApiOperation(value = "修改充值活动信息")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated RechargeRule base) {
        return showMemberClient.modifyRechargeRule(base);
    }

    @ApiOperation(value = "获取会员充值模板列表，不包括活动充值")
    @GetMapping(value = "/list")
    public Result<List<RechargeRule>> list() {
        return showMemberClient.listRechargeRule();
    }

    @ApiOperation(value = "获取会员充值模板列表（键值对）")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listCombobox() {
        return showMemberClient.listRechargeRuleCombobox();
    }

    @ApiOperation(value = "获取充值活动分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<RechargeRule>> page(@RequestBody PageParam<RechargeRule> base) {
        return showMemberClient.pageRecharge(base);
    }

    @ApiOperation(value = "获取充值活动信息")
    @GetMapping(value = "/{markId}")
    public Result<?> getInfo(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.getRechargeRuleInfo(markId);
    }

    @ApiOperation(value = "删除充值活动信息")
    @DeleteMapping(value = "/{rechargeId}")
    public Result delete(@PathVariable("rechargeId") @NotBlank String rechargeId) {
        return showMemberClient.deleteRechargeRule(rechargeId);
    }

    @ApiOperation(value = "批量修改充值活动状态")
    @PatchMapping(value = "/batch/{status}")
    public Result modifyStatus(@RequestBody @NotEmpty String[] ruleIds, @PathVariable("status") @NotNull Integer status) {
        ShowAssert.checkTrue(status == -1, StatusCode._4004);
        return showMemberClient.modifyRechargeRuleStatus(ruleIds, status);
    }

    @ApiOperation(value = "批量删除充值活动信息")
    @PatchMapping(value = "/batch/delete")
    public Result deleteBatch(@RequestBody @NotEmpty String[] ruleIds) {
        return showMemberClient.modifyRechargeRuleStatus(ruleIds, -1);
    }
}
