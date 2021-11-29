package com.szhengzhu.controller;

import com.szhengzhu.bean.member.*;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author jehon
 */
@Validated
@Slf4j
@Api(tags = {"积分账户：IntegralController"})
@RestController
@RequestMapping("/v1/integral")
public class IntegralController {

    @Resource
    private Sender sender;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "获取会员积分汇总")
    @PostMapping(value = "/account/sum")
    public Result<Map<String, Integer>> getSumByUser(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showMemberClient.getIntegralSumByUser(userToken.getUserId());
    }

    @ApiOperation(value = "获取会员积分分页记录")
    @PostMapping(value = "/detail/user/page")
    public Result<PageGrid<IntegralDetail>> pageUserIntegral(HttpServletRequest request,
                                                             @RequestBody PageParam<String> param) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        param.setData(userToken.getUserId());
        return showMemberClient.pageUserIntegral(param);
    }

    @ApiOperation(value = "查询积分兑换菜品券（代金券）列表")
    @GetMapping(value = "/exchange/list")
    public Result<List<IntegralExchange>> queryIntegralExchangeList() {
        return showMemberClient.queryIntegralExchangeList(IntegralExchange.builder().status(true).build());
    }

    @ApiOperation(value = "点击兑换菜品券（代金券）")
    @GetMapping(value = "/exchange")
    public Result<String> integralExchange(HttpServletRequest request, @RequestParam("exchangeId") @NotBlank String exchangeId) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        IntegralExchange exchange = showMemberClient.queryByIntegralExchangeId(exchangeId).getData();
        Integer totalIntegral = showMemberClient.getIntegralTotalByUserId(userToken.getUserId()).getData();
        ShowAssert.checkTrue(!exchange.getStatus(), StatusCode._5043);
        //判断是否是会员
        ShowAssert.checkResult(showMemberClient.getMemberInfoByUser(userToken.getUserId()));
        //判断积分是否满足兑换条件
        ShowAssert.checkTrue(totalIntegral < exchange.getConsumeIntegral(), StatusCode._4057);
        //判断兑换券是否充足
        ShowAssert.checkTrue(exchange.getExchangeQuantity() <= 0, StatusCode._5041);
        sender.integralExchange(userToken.getUserId(), exchangeId);
        return new Result<>();
    }

    @ApiOperation(value = "展示用户用积分兑换菜品券（代金券）详情")
    @GetMapping(value = "/exchange/ticket/list")
    public Result<List<TicketExchange>> queryTicketExchangeList(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showMemberClient.queryTicketExchangeList(TicketExchange.builder().userId(userToken.getUserId()).build());
    }

    @ApiOperation(value = "展示用户将要过期的积分数")
    @GetMapping(value = "/expire/show")
    public Result<List<Map<String, String>>> selectPushUser(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showMemberClient.selectPushUser(userToken.getUserId());
    }

    @ApiOperation(value = "查询积分过期信息详情")
    @GetMapping(value = "/expire/info")
    public Result<IntegralExpire> queryByIntegralExpireId() {
        return showMemberClient.queryByIntegralExpireId();
    }
}
