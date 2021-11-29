package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.member.SignMember;
import com.szhengzhu.bean.member.SignRule;
import com.szhengzhu.bean.member.param.SignInfoParam;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = {"签到：SignController"})
@RestController
@RequestMapping(value = "/v1/sign")
public class SignController {

    @Resource
    private Sender sender;

    @Resource
    private ShowMemberClient showMemberClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation("添加用户签到的信息")
    @GetMapping(value = "")
    public Result<String> addSignMember(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        Integer day = DateUtil.dayOfMonth(DateUtil.date());
        SignInfoParam signMember = showMemberClient.queryUserSign(SignMember.builder().userId(userToken.getUserId()).
                year(DateUtil.year(DateUtil.date())).month(DateUtil.month(DateUtil.date())).build()).getData();
        if (ObjectUtil.isNotEmpty(signMember)) {
            StringBuffer sign = new StringBuffer(signMember.getSign());
            //判断用户今天是否已经签到
            ShowAssert.checkTrue(sign.substring(day - 1, day).equals(Contacts.SIGN_IN), StatusCode._5042);
        }
        sender.memberSign(userToken.getUserId());
        return new Result<>();
    }

    @ApiOperation("查询会员签到规则")
    @GetMapping(value = "/rule/info")
    public Result<List<SignRule>> querySignRule() {
        return showMemberClient.querySignRule(SignRule.builder().build());
    }

    @ApiOperation("展示用户签到的信息")
    @GetMapping(value = "/user/show")
    public Result<SignInfoParam> queryUserSign(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        Integer year = DateUtil.year(DateUtil.date());
        Integer month = DateUtil.month(DateUtil.date());
        return showMemberClient.queryUserSign(SignMember.builder().userId(userToken.getUserId()).
                year(year).month(month).build());
    }
}
