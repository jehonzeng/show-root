package com.szhengzhu.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.member.SignDetail;
import com.szhengzhu.bean.member.SignMember;
import com.szhengzhu.bean.member.SignRule;
import com.szhengzhu.bean.member.param.SignInfoParam;
import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = {"签到：SignController"})
@RestController
@RequestMapping(value = "/v1/sign")
public class SignController {
    @Resource
    private Sender sender;

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation("查询会员签到的信息")
    @PostMapping(value = "/info")
    public Result<List<SignInfoParam>> selectBySignMember(@RequestBody(required = false) SignMember signMember) {
        return showMemberClient.selectBySignMember(signMember);
    }

    @ApiOperation("查询签到规则")
    @PostMapping(value = "/rule/info")
    public Result<List<SignRule>> querySignRule(@RequestBody SignRule signRule) {
        return showMemberClient.querySignRule(signRule);
    }

    @ApiOperation("根据主键id查询会员签到规则")
    @GetMapping(value = "/rule/{markId}")
    public Result<SignRule> queryBySignRuleId(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.queryBySignRuleId(markId);
    }

    @ApiOperation("添加签到规则信息")
    @PostMapping(value = "/rule")
    public Result addSignRule(@RequestBody SignRule signRule) {
        return showMemberClient.addSignRule(signRule);
    }

    @ApiOperation("修改签到规则信息")
    @PatchMapping(value = "/rule")
    public Result modifySignRule(@RequestBody SignRule signRule) {
        return showMemberClient.modifySignRule(signRule);
    }

    @ApiOperation("分页展示用户签到领取礼包的详细信息")
    @PostMapping(value = "/detail/page")
    public Result<PageGrid<SignDetail>> querySignDetailList(PageParam<SignDetail> param) {
        return showMemberClient.querySignDetailList(param);
    }

    @ApiOperation("删除签到规则")
    @DeleteMapping(value = "/rule/{markId}")
    public Result deleteBySignRuleId(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.deleteBySignRuleId(markId);
    }

    @ApiOperation("测试")
    @GetMapping(value = "/test")
    public Result addSignMember(@RequestParam("userId") String userId, @RequestParam("date") String date) {
        DateTime dates = DateUtil.parse(date);
        Integer year = DateUtil.year(dates);
        Integer month = DateUtil.month(dates);
        Integer day = DateUtil.dayOfMonth(dates);
        SignInfoParam signMember = showMemberClient.queryUserSign(SignMember.builder().userId(userId).year(year).month(month).build()).getData();
        if (ObjectUtil.isNotEmpty(signMember)) {
            StringBuffer sign = new StringBuffer(signMember.getSign());
            //判断用户今天是否已经签到
            ShowAssert.checkTrue(sign.substring(day - 1, day).equals(Contacts.SIGN_IN), StatusCode._5042);
        }
        sender.memberSign(userId, date);
        return new Result<>();
    }
}
