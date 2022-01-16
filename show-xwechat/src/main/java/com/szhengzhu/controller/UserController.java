package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.bean.ordering.vo.UserTicketVo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.vo.UserInfoVo;
import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.feign.ShowOrderClient;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"用户：UserController"})
@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowMemberClient showMemberClient;

    @Resource
    private ShowOrderingClient showOrderingClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @ApiOperation(value = "个人中心用户基本信息")
    @GetMapping(value = "/info")
    public Result getInfo(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String userId = userToken.getUserId();
        // 1.小程序用户信息（头像，姓名，积分）
        Result<UserInfoVo> myResult = showUserClient.getMyInfo(userId);
        ShowAssert.checkResult(myResult);
        UserInfoVo userInfoVo = myResult.getData();
        // 2.会员信息（vipId，余额）
        Result<MemberAccount> memberResult = showMemberClient.getMemberInfoByUserId(userId);
        userInfoVo.setUserId(userId);
        if (memberResult.isSuccess()) {
            MemberAccount account = memberResult.getData();
            userInfoVo.setAccountNo(account.getAccountNo());
            userInfoVo.setAccountTotal(account.getTotalAmount());
            userInfoVo.setNickName(StrUtil.isEmpty(account.getName()) ? userInfoVo.getNickName() : account.getName());
            if (ObjectUtil.isNotEmpty(account.getMemberGrade())) {
                userInfoVo.setGradeName(account.getMemberGrade().getGradeName());
                userInfoVo.setGradeSort(ObjectUtil.isEmpty(account.getMemberGrade().getSort()) ? 0 : account.getMemberGrade().getSort());
                userInfoVo.setConsumeAmount(ObjectUtil.isEmpty(account.getConsumeAmount()) ? 0 : account.getConsumeAmount());
            }
        }
        return new Result<>(userInfoVo);
    }

    @ApiOperation(value = "获取用户券", notes = "status:-1已过期、0已使用、1未使用， 不传该参数表示查询所有")
    @GetMapping(value = "/ticket/list")
    public Result<List<UserTicketVo>> listUserTicket(HttpServletRequest request, @RequestParam(value = "status", required = false) Integer status) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String userId = userToken.getUserId();
        return showOrderingClient.listUserTicket(userId, status);
    }

    @ApiOperation(value = "获取用户订单可用优惠券")
    @GetMapping(value = "/indent/ticket")
    public Result<List<UserTicketVo>> listUserTicketByIndent(HttpServletRequest request, @RequestParam("indentId") @NotBlank String indentId) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String userId = userToken.getUserId();
        return showOrderingClient.listUserTicketByIndent(userId, indentId);
    }

    @ApiOperation(value = "获取用户线下代金券", notes = "获取用户线下优惠券")
    @GetMapping(value = "/voucher/list")
    public Result<?> voucher(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showOrderClient.listCouponByUser(userToken.getUserId(), 0);
    }
}
