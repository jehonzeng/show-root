package com.szhengzhu.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.szhengzhu.annotation.NoRepeatSubmit;
import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.EncryUser;
import com.szhengzhu.bean.WxaDPhone;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.xwechat.vo.CalcVo;
import com.szhengzhu.bean.xwechat.vo.UnifiedIndent;
import com.szhengzhu.code.MemberCode;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.redis.RedisKey;
import com.szhengzhu.util.SmsUtils;
import com.szhengzhu.util.UserUtils;
import com.szhengzhu.util.WechatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.Jscode2sessionResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Slf4j
@Api(tags = {"会员账户：MemberController"})
@RestController
@RequestMapping("/v1/member")
public class MemberController {

    @Resource
    private WechatConfig config;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowMemberClient showMemberClient;

    @Resource
    private ShowOrderingClient showOrderingClient;

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    private static final String CODE_CACHE_PRE = "member:code:send:";

    @ApiOperation(value = "解密手机号")
    @PostMapping(value = "/phone/decrypt")
    public Result<String> decryptPhone(@RequestBody EncryUser encryUser) {
        String code = encryUser.getCode();
        Jscode2sessionResult sessionResult = SnsAPI.jscode2session(config.getAppId(), config.getSecret(), code);
        ShowAssert.checkTrue(ObjectUtil.isNull(sessionResult), StatusCode._5028);
        ShowAssert.checkTrue(StrUtil.isNotEmpty(sessionResult.getErrcode()), StatusCode._5028);
        String sessionKey = sessionResult.getSession_key();
        WxaDPhone wxadPhone = WechatUtils.decryptPhone(sessionKey, encryUser.getEncryptedData(), encryUser.getIv());
        ShowAssert.checkTrue(ObjectUtil.isNull(wxadPhone), StatusCode._5028);
        log.info(wxadPhone.getPhoneNumber());
        return new Result<>(wxadPhone.getPhoneNumber());
    }

    @ApiOperation(value = "用户手机发送验证码")
    @NoRepeatSubmit
    @GetMapping(value = "/phone/code")
    public void getCode(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        ShowAssert.checkTrue(!Validator.isMobile(phone), StatusCode._4000);
        int sendTimes = 6;
        String codeCacheKey = CODE_CACHE_PRE + phone;
        Object obj = redis.get(codeCacheKey);
        LoginBase loginBase = ObjectUtil.isNull(obj) ? new LoginBase(phone) : JSON.parseObject(JSON.toJSONString(obj), LoginBase.class);
        ShowAssert.checkTrue(loginBase.isOften(sendTimes), StatusCode._4001);
        loginBase.refresh(phone);
        redis.set(codeCacheKey, loginBase, 3 * 60);
        SmsUtils.send(phone, loginBase.getCode());
        log.info("会员注册{}的验证码：{}", phone, loginBase.getCode());
    }

    @ApiOperation(value = "验证手机")
    @GetMapping(value = "/phone/auth")
    public Result<String> login(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        String codeCacheKey = CODE_CACHE_PRE + phone;
        Object obj = redis.get(codeCacheKey);
        ShowAssert.checkTrue(ObjectUtil.isNull(obj), StatusCode._4003);
        LoginBase loginBase = JSON.parseObject(JSON.toJSONString(obj), LoginBase.class);
        ShowAssert.checkTrue(!loginBase.equals(phone, code), StatusCode._4002);
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        ShowAssert.checkNull(userInfo, StatusCode._4013);
        // 添加会员
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        MemberAccount account = new MemberAccount();
        account.setMarkId(markId);
        account.setUserId(userInfo.getMarkId());
        account.setPhone(phone);
        Result<String> memberResult = showMemberClient.addMemberAccount(account);
        ShowAssert.checkResult(memberResult);
        redis.del(codeCacheKey);
        sender.memberGrade(markId);
        return new Result<>(memberResult.getData());
    }

    @ApiOperation(value = "生成会员账户唯一识别码")
    @GetMapping(value = "/create/bar/mark")
    public Result<String> createBarMark(HttpServletRequest request) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        return showMemberClient.createMemberBarMark(userInfo.getMarkId());
    }

    @ApiOperation(value = "获取会员充值消费分页记录")
    @PostMapping(value = "/detail/user/page")
    public Result<PageGrid<MemberDetail>> pageUserDetail(HttpServletRequest request,
                                                         @RequestBody PageParam<String> param) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        param.setData(userInfo.getMarkId());
        return showMemberClient.pageUserMemberDetail(param);
    }

    @ApiOperation(value = "修改会员信息")
    @PostMapping(value = "/account")
    public Result<MemberAccount> modifyAccount(HttpServletRequest request, @RequestBody MemberAccount account) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        account.setUserId(userToken.getUserId());
        return showMemberClient.modifyMemberAccount(account);
    }

    @ApiOperation(value = "获取会员信息")
    @GetMapping(value = "/account/info")
    public Result<MemberAccount> getAccountInfo(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showMemberClient.getMemberInfoByUserId(userToken.getUserId());
    }

    @ApiOperation(value = "获取会员充值模板")
    @GetMapping(value = "/recharge/list")
    public Result<List<RechargeRule>> list() {
        return showMemberClient.listRechargeRule();
    }

    @ApiOperation(value = "小程序会员充值微信支付下单")
    @NoRepeatSubmit
    @GetMapping(value = "/recharge/unifiedorder")
    public Result wechatAppletPay(HttpServletRequest request, @RequestParam("ruleId") @NotBlank String ruleId,
                                  @RequestParam(value = "indentId", required = false) String indentId) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        String userId = userInfo.getMarkId();
        String xopenId = userInfo.getXopenId();
        // 小程序用户未授权或登录失效
        ShowAssert.checkTrue(StrUtil.isEmpty(xopenId), StatusCode._4012);
        BigDecimal indentTotal = BigDecimal.ZERO;
        if (StrUtil.isNotEmpty(indentId)) {
            Result<BigDecimal> result = showOrderingClient.getIndentCostTotal(indentId);
            ShowAssert.checkResult(result);
            indentTotal = result.getData();
        }
        Result<MemberDetail> detailResult = showMemberClient.rechargeByRule(ruleId, userId, xopenId, indentTotal);
        ShowAssert.checkResult(detailResult);
        MemberDetail detail = detailResult.getData();
        // 统一下单
        return WechatUtils.memberPay(config, detail.getMarkId(), request.getRemoteAddr(), xopenId, detail.getAmount());
    }

    @ApiOperation(value = "小程序会员支付")
    @NoRepeatSubmit
    @PostMapping(value = "/xpay")
    public Result<CalcVo> memberPay(HttpServletRequest request, @RequestBody UnifiedIndent unifiedIndent) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String userId = userToken.getUserId();
        String payKey = RedisKey.X_INDENT_PAY + unifiedIndent.getIndentId();
        Object data = redis.get(payKey);
        ShowAssert.checkTrue(ObjectUtil.isNotNull(data) && !userId.equals(data), StatusCode._4051);
        unifiedIndent.setUserId(userId);
        Result<MemberAccount> accountResult = showMemberClient.getMemberInfoByUserId(userId);
        ShowAssert.checkResult(accountResult);
        unifiedIndent.setMemberId(accountResult.getData().getMarkId());
        Result<CalcVo> result = showOrderingClient.calcIndent(unifiedIndent);
        CalcVo calc = result.getData();
        BigDecimal payTotal = calc.getTotal();
        unifiedIndent.setAmount(payTotal);
        // 检查会员余额是否够
        MemberAccount account = accountResult.getData();
        ShowAssert.checkTrue(payTotal.compareTo(account.getTotalAmount()) > 0, StatusCode._4035);
        MemberDetail detail = new MemberDetail();
        if (payTotal.compareTo(BigDecimal.ZERO) > 0) {
            detail.setAccountId(account.getMarkId());
            detail.setAmount(payTotal);
            detail.setIndentId(unifiedIndent.getIndentId());
            detail.setType(MemberCode.CONSUME.code);
            Result<String> memberResult = showMemberClient.memberConsume(detail);
            ShowAssert.checkResult(memberResult);
            String detailId = memberResult.getData();
            unifiedIndent.setConsumptionId(detailId);
        }
        unifiedIndent.setMemberId(account.getMarkId());
        Result payResult = showOrderingClient.memberPay(unifiedIndent);
        if (!payResult.getCode().equals(Contacts.SUCCESS_CODE)) {
            showMemberClient.deleteMemberDetail(detail.getMarkId(), null);
        }
        return result;
    }

    @ApiOperation(value = "展示会员等级")
    @GetMapping(value = "/grade/show")
    public Result<List<MemberGradeShow>> memberGradeShow(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showMemberClient.memberGradeShow(userToken.getUserId());
    }

    @ApiOperation(value = "展示会员消费（成长值）")
    @PostMapping(value = "/grade/consume/record")
    public Result<PageGrid<GradeRecord>> queryGradeRecord(HttpServletRequest request, @RequestBody PageParam<GradeRecord> param) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String userId = userToken.getUserId();
        Result<MemberAccount> memberAccount = showMemberClient.getMemberInfoByUserId(userId);
        param.setData(GradeRecord.builder().memberId(memberAccount.getData().getMarkId()).build());
        return showMemberClient.queryGradeRecord(param);
    }

    @ApiOperation(value = "展示有效的会员等级信息")
    @PostMapping(value = "/grade/info")
    public Result<List<MemberGrade>> queryMemberGrade(@RequestBody MemberGrade memberGrade) {
        memberGrade.setStatus(1);
        return showMemberClient.queryMemberGrade(memberGrade);
    }

    @ApiOperation(value = "展示会员购买的套餐")
    @GetMapping(value = "/combo")
    public Result<List<ComboReceive>> queryMemberCombo(HttpServletRequest request,
                                                       @RequestParam(value = "status", required = false) Integer status) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        return showMemberClient.queryMemberCombo(userInfo.getMarkId(), status);
    }

    @ApiOperation(value = "展示会员套餐购买信息")
    @GetMapping(value = "/combo/list")
    public Result<List<ComboBuy>> queryComboList(HttpServletRequest request) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        String userId = userInfo.getMarkId();
        List<ComboBuy> comboList = showMemberClient.queryComboInfo().getData();
        for (ComboBuy combo : comboList) {
            Integer quantity = showMemberClient.queryComboQuantity(userId, combo.getMarkId()).getData();
            if (quantity.compareTo(combo.getBuyQuantity()) == -1) {
                combo.setBuyChance(combo.getBuyQuantity() - quantity);
            } else {
                combo.setBuyChance(0);
            }
        }
        return new Result<>(comboList);
    }


    @ApiOperation(value = "会员购买套餐")
    @GetMapping(value = "/combo/pay/unifiedorder")
    public Result comboPay(HttpServletRequest request, @RequestParam("markId") String markId, @RequestParam("number") Integer number) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        ShowAssert.checkTrue(StrUtil.isEmpty(userInfo.getXopenId()), StatusCode._4012);
        Result<ComboPay> result = showMemberClient.memberBuyCombo(markId, number, userInfo.getMarkId(), userInfo.getXopenId());
        ShowAssert.checkResult(result);
        ComboPay comboPay = result.getData();
        PayBack payBack = new PayBack();
        payBack.setType(3);
        payBack.setPayId(comboPay.getMarkId());
        payBack.setUserId(userInfo.getMarkId());
        payBack.setCode(userInfo.getXopenId());
        showMemberClient.comboPayBack(payBack);
        return WechatUtils.comboPay(config, comboPay.getMarkId(), request.getRemoteAddr(), userInfo.getXopenId(), comboPay.getAmount());
    }


    @ApiOperation(value = "根据id查询活动")
    @GetMapping(value = "/combo/{markId}")
    public Result<ComboBuy> queryByComboId(HttpServletRequest request, @PathVariable("markId") @NotBlank String markId) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        Result<ComboBuy> combo = showMemberClient.queryByComboId(markId);
        Integer quantity = showMemberClient.queryComboQuantity(userInfo.getMarkId(), markId).getData();
        if (quantity.compareTo(combo.getData().getBuyQuantity()) == -1) {
            combo.getData().setBuyChance(combo.getData().getBuyQuantity() - quantity);
        } else {
            combo.getData().setBuyChance(0);
        }
        return combo;
    }
}
