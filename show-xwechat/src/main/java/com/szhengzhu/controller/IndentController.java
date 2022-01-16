package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.szhengzhu.annotation.NoRepeatSubmit;
import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.CartServerData;
import com.szhengzhu.bean.ordering.Indent;
import com.szhengzhu.bean.ordering.UserIndent;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.xwechat.vo.IndentModel;
import com.szhengzhu.bean.xwechat.vo.UnifiedIndent;
import com.szhengzhu.code.IndentStatus;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.redis.RedisKey;
import com.szhengzhu.util.UserUtils;
import com.szhengzhu.util.WechatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"订单：IndentController"})
@RestController
@RequestMapping("/v1/indent")
public class IndentController {

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Resource
    private ShowOrderingClient showOrderingClient;

    @Resource
    private WechatConfig wechatConfig;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "获取已点商品")
    @GetMapping(value = "/detail/comm")
    public Result<List<Map<String, Object>>> listIndentComm(@RequestParam("indentId") @NotBlank String indentId) {
        return showOrderingClient.listIndentComm(indentId);
    }

    @ApiOperation(value = "用户进入买单状态")
    @GetMapping(value = "/status/to/pay")
    public Result orderToPay(@RequestParam("indentId") @NotBlank String indentId) {
        Result<Indent> result = showOrderingClient.getIndentInfo(indentId);
        Indent indent = result.getData();
        ShowAssert.checkTrue(IndentStatus.BILL.code.equals(indent.getIndentStatus()), StatusCode._4054);
        return showOrderingClient.modifyIndentStatus(indentId, IndentStatus.PAYING.code);
    }

    @ApiOperation(value = "用户退出买单状态")
    @GetMapping(value = "/status/to/indent")
    public Result orderToIndent(@RequestParam("indentId") @NotBlank String indentId) {
        Result<Indent> result = showOrderingClient.getIndentInfo(indentId);
        Indent indent = result.getData();
        ShowAssert.checkTrue(IndentStatus.BILL.code.equals(indent.getIndentStatus()), StatusCode._4054);
        return showOrderingClient.modifyIndentStatus(indentId, IndentStatus.CREATE.code);
    }

    @ApiOperation(value = "小程序下单")
    @NoRepeatSubmit
    @GetMapping(value = "/x/create/{tableId}")
    public Result<String> create(HttpServletRequest request, @PathVariable("tableId") @NotBlank String tableId) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        String createKey = "indent:create:" + tableId;
        try {
            boolean lock = redis.lock(createKey, 3);
            if (!lock) {
                Result<Indent> indentResult = showOrderingClient.getTableInfoByTable(tableId);
                return new Result<>(indentResult.getData().getMarkId());
            }
            Result<String> result = showOrderingClient.createIndent(tableId, userInfo.getMarkId());
            sendCartMessage(userInfo, "create", tableId, result.getData());
            return result;
        } finally {
            redis.del(createKey);
        }
    }

    @ApiOperation(value = "小程序获取用户订单分页列表")
    @PostMapping(value = "/x/page")
    public Result<PageGrid<IndentModel>> pageBase(HttpServletRequest request,
                                                  @RequestBody PageParam<String> param) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        param.setData(userToken.getUserId());
        return showOrderingClient.pageIndentBase(param);
    }

    @ApiOperation(value = "获取订单商品列表(订单明细及支付方式)")
    @GetMapping(value = "/x/model/{indentId}")
    public Result<IndentModel> getIndentInfo(@PathVariable("indentId") @NotBlank String indentId) {
        return showOrderingClient.getIndentModel(indentId);
    }

    @ApiOperation(value = "选择优惠券计算订单支付金额")
    @NoRepeatSubmit
    @PostMapping(value = "/x/calc")
    public Result calc(HttpServletRequest request, @RequestBody @Validated UnifiedIndent indentCalc) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        MemberAccount account = showMemberClient.getMemberInfoByUser(userToken.getUserId()).getData();
        indentCalc.setUserId(userToken.getUserId());
        indentCalc.setMemberId(account.getMarkId());
        return showOrderingClient.calcIndent(indentCalc);
    }

    @ApiOperation(value = "小程序微信支付")
    @NoRepeatSubmit
    @PostMapping(value = "/xpay/unifiedorder")
    public Result wechatAppletPay(HttpServletRequest request, @RequestBody @Validated UnifiedIndent unifiedIndent) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        String userId = userInfo.getMarkId();
        String xopenId = userInfo.getXopenId();
        // 小程序用户未授权
        ShowAssert.checkTrue(StrUtil.isEmpty(xopenId), StatusCode._4012);
        String payKey = RedisKey.X_INDENT_PAY + unifiedIndent.getIndentId();
        Object data = redis.get(payKey);
        ShowAssert.checkTrue(ObjectUtil.isNotNull(data) && !userId.equals(data), StatusCode._4051);
        // 回调时要调用userIdQW
        redis.set(payKey, userId, 6 * 60);
        unifiedIndent.setUserId(userId);
        unifiedIndent.setCode(xopenId);
        Result<BigDecimal> payResult = showOrderingClient.wechatPay(unifiedIndent);
        ShowAssert.checkResult(payResult);
        BigDecimal payPrice = payResult.getData();
        // 统一下单
        return WechatUtils.wechatPay(wechatConfig, unifiedIndent.getIndentId(), request.getRemoteAddr(), xopenId, payPrice);
    }

    private void sendCartMessage(UserInfo userInfo, String operate, String tableId, String indentId) {
        if (!StrUtil.isEmpty(tableId)) {
            CartServerData data = CartServerData.builder().userId(userInfo.getMarkId()).nickName(userInfo.getNickName())
                    .headerImg(userInfo.getHeaderImg()).opt(operate).tableId(tableId).indentId(indentId).build();
            redis.convertAndSend("sendCartMsg", JSON.toJSONString(data));
        }
    }

    @ApiOperation(value = "小程序用户订单状态信息")
    @PostMapping(value = "/user")
    public Result<UserIndent> userIndent(HttpServletRequest request) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        String userId = userInfo.getMarkId();
        return showOrderingClient.userIndent(userId);
    }
}
