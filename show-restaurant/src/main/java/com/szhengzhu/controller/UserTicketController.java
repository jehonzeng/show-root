package com.szhengzhu.controller;

import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.bean.member.MemberTicket;
import com.szhengzhu.bean.ordering.param.GiveParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ShowAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "会员优惠券：UserTicketController")
@RestController
@RequestMapping("/v1/userTicket")
public class UserTicketController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "点餐平台：手动给用户发优惠券")
    @PostMapping(value = "/res/give")
    public Result giveUserTicket(@RequestBody @Validated GiveParam giveParam) {
        Result<MemberAccount> accountResult = showMemberClient.getMemberInfo(giveParam.getAccountId());
        ShowAssert.checkResult(accountResult);
        giveParam.setUserId(accountResult.getData().getUserId());
        return showOrderingClient.giveUserTicket(giveParam);
    }

    @ApiOperation(value = "根据会员id查询优惠券")
    @GetMapping(value = "/select/{markId}")
    public Result<List<MemberTicket>> memberTicket(@PathVariable("markId") @NotBlank String markId) {
        return showOrderingClient.memberTicket(markId);
    }

    @ApiOperation(value = "根据优惠券的id回收会员的优惠券")
    @DeleteMapping(value = "/del/{markId}")
    public Result deleteMemberTicket(@PathVariable("markId") @NotBlank String markId) {
        return showOrderingClient.deleteMemberTicket(markId);
    }
}
