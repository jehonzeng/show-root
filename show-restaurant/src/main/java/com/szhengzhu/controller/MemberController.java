package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.member.param.MemberDetailParam;
import com.szhengzhu.bean.member.param.MemberPaymentParam;
import com.szhengzhu.bean.member.param.MemberRecordParam;
import com.szhengzhu.bean.member.param.RechargeParam;
import com.szhengzhu.bean.member.vo.MemberAccountVo;
import com.szhengzhu.bean.member.vo.MemberTicketVo;
import com.szhengzhu.bean.ordering.Indent;
import com.szhengzhu.bean.ordering.vo.UserTicketVo;
import com.szhengzhu.bean.vo.ConsumeVo;
import com.szhengzhu.code.IntegralCode;
import com.szhengzhu.code.MemberCode;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "会员管理：MemberController")
@RestController
@RequestMapping("/v1/member")
public class MemberController {

    @Resource
    private ShowMemberClient showMemberClient;

    @Resource
    private ShowOrderingClient showOrderingClient;

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @ApiOperation(value = "获取会员信息", notes = "获取会员详细信息")
    @GetMapping(value = "/account/{markId}")
    public Result<MemberAccountVo> getAccountInfo(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.getMemberVoInfo(markId);
    }

    @ApiOperation(value = "获取充值会员列表", notes = "获取充值会员列表(可通过手机查询)")
    @PostMapping(value = "/account/page")
    public Result<PageGrid<MemberAccount>> pageAccount(@RequestBody PageParam<MemberByType> param) {
        boolean flag = ObjectUtil.isNotNull(param.getData().getTemp()) && (param.getData().getIs_NewMember() ||
                param.getData().getIs_NowConsume() || param.getData().getIs_NowRecharge());
        ShowAssert.checkTrue(flag, StatusCode._4004);
        return showMemberClient.pageMemberAccount(param);
    }

//    @ApiOperation(value = "新增充值会员", notes = "新增充值会员")
//    @PostMapping(value = "/account")
//    public Result<?> addMemberAccount(HttpServletRequest req, @RequestBody MemberAccountParam param) {
//        String userId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
//        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
//        if (StringUtils.isEmpty(userId) || param == null || StringUtils.isEmpty(param.getPhone())
//                || param.getAmount() == null) {
//            return new Result<>(StatusCode._4005);
//        }
//        String userId = "167950079553941504";
//        String storeId ="165429560740921344";
//        param.setCreator(userId);
//        Result<String> result = showBaseClient.getStoreByStaff(userId);
//        if (!result.isSuccess()) {
//            return result;
//        }
//        String storeId = result.getData();
//        param.setStoreId(storeId);
//        return showMemberClient.addMember(param);
//    }

    @ApiOperation(value = "修改会员基本信息", notes = "修改会员基本信息")
    @PatchMapping(value = "/account")
    public Result<MemberAccount> updateAccount(@RequestBody MemberAccount base) {
        return showMemberClient.modifyMemberAccount(base);
    }

    @ApiOperation(value = "通过会员账户Id获取充值详情", notes = "通过会员账户Id获取充值详情")
    @PostMapping(value = "/detail/page")
    public Result<PageGrid<MemberDetail>> pageDetail(@RequestBody PageParam<MemberDetail> param) {
        return showMemberClient.pageMemberDetail(param);
    }

    @ApiOperation(value = "删除会员充值记录", notes = "删除会员充值记录")
    @DeleteMapping(value = "/detail/{detailId}")
    public Result deleteDetail(@PathVariable("detailId") @NotBlank String detailId, HttpSession session) {
        String userId = (String) session.getAttribute(Contacts.RESTAURANT_USER);
        Result<MemberDetail> memberDetail = showMemberClient.selectByMarkId(detailId);
        Result<MemberAccount> memberAccount = showMemberClient.getMemberInfo(memberDetail.getData().getAccountId());
        if (memberDetail.getData().getType().equals(MemberCode.CONSUME.code)) {
            sender.memberConsumeRefund(memberDetail.getData().getIndentId(), memberAccount.getData().getUserId(), memberDetail.getData().getAmount());
        }
        return showMemberClient.deleteMemberDetail(detailId, userId);
    }

    @ApiOperation(value = "会员充值")
    @PostMapping(value = "/recharge")
    public Result<MemberAccount> recharge(HttpServletRequest req, @RequestBody @Validated MemberDetailParam detail) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        detail.setEmployeeId(employeeId);
        detail.setStoreId(storeId);
        Result<MemberAccount> accountResult = showMemberClient.recharge(detail);
        if (ObjectUtil.isNotNull(detail.getBonusIntegral()) && detail.getBonusIntegral() > 0) {
            IntegralDetail integral = new IntegralDetail();
            integral.setUserId(accountResult.getData().getUserId());
            integral.setIntegralLimit(detail.getBonusIntegral());
            integral.setIntegralType(IntegralCode.OTHER_GIVE);
            integral.setStatus(1);
            showMemberClient.addIntegral(integral);
        }
        /*if (accountResult.isSuccess()) {
            // 短信提醒
        }*/
        return accountResult;
    }

    @ApiOperation(value = "会员充值模板充值", notes = "根据充值规则充值")
    @PostMapping(value = "/recharge/rule")
    public Result resRechargeByRule(HttpServletRequest req, @RequestBody @Validated RechargeParam param) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        param.setEmployeeId(employeeId);
        param.setStoreId(storeId);
        return showMemberClient.rechargeByRule(param);
    }

    @ApiOperation(value = "会员消费", notes = "会员消费")
    @PostMapping(value = "/consumption")
    public Result consume(HttpServletRequest req, @RequestBody @Validated ConsumeVo consumeVo) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        Result<String> memberResult = new Result<>();
        if (consumeVo.getAmount() != null && consumeVo.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            MemberDetail detail = MemberDetail.builder().accountId(consumeVo.getAccountId()).amount(consumeVo.getAmount()).type(MemberCode.CONSUME.code)
                    .creator(employeeId).storeId(storeId).build();
            memberResult = showMemberClient.memberConsume(detail);
            ShowAssert.checkSuccess(memberResult);
            //会员消费 赠送...
            sender.memberConsume(consumeVo.getUserId(), consumeVo.getAmount());
        }
        if (consumeVo.getIntegral() != null && consumeVo.getIntegral() > 0) {
            IntegralDetail integralDetail = IntegralDetail.builder().userId(consumeVo.getUserId())
                    .integralLimit(consumeVo.getIntegral()).status(1).build();
            integralDetail.setIntegralType(IntegralCode.EXCHANGE);
            Result result = showMemberClient.integralConsume(integralDetail);
            if (!result.getCode().equals(Contacts.SUCCESS_CODE)) {
                if (memberResult.isSuccess()) {
                    showMemberClient.deleteMemberDetail(memberResult.getData(), null);
                }
                return result;
            }
        }
        return new Result();
    }

    @ApiOperation(value = "扫码获取会员信息")
    @GetMapping(value = "/account/mark")
    public Result<MemberAccountVo> getInfoByMark(@RequestParam("mark") @NotBlank String mark) {
        Object accountId = redis.get("member:account:bar:" + mark);
        ShowAssert.checkTrue(ObjectUtil.isNull(accountId), StatusCode._4047);
        return showMemberClient.getMemberVoInfo(String.valueOf(accountId));
    }

    @ApiOperation(value = "根据手机号获取会员信息")
    @GetMapping(value = "/account/phone")
    public Result<List<MemberAccountVo>> getInfoByPhone(@RequestParam("phone") @NotBlank String phone) {
        return showMemberClient.getMemberInfoByPhone(phone);
    }

    @ApiOperation(value = "根据会员号获取会员信息")
    @GetMapping(value = "/account/no")
    public Result<List<MemberAccountVo>> getInfoByNo(@RequestParam("accountNo") @NotBlank String accountNo) {
        return showMemberClient.getMemberInfoByNo(accountNo);
    }

    @ApiOperation(value = "点餐平台：结账获取会员信息")
    @GetMapping(value = "/res/info")
    public Result<MemberTicketVo> resMemberInfo(@RequestParam("indentId") @NotBlank String indentId) {
        Result<MemberTicketVo> result = new Result<>();
        Result<Indent> indentResult = showOrderingClient.getIndentInfo(indentId);
        if (indentResult.isSuccess() && !StringUtils.isEmpty(indentResult.getData().getMemberId())) {
            String memberId = indentResult.getData().getMemberId();
            result = showMemberClient.memberInfoById(memberId);
            if (result.isSuccess()) {
                String userId = result.getData().getUserId();
                Result<List<UserTicketVo>> ticketResult = showOrderingClient.getUserTicket(userId);
                result.getData().setTicketList(ticketResult.getData());
            }
        }
        return result;
    }

    @ApiOperation(value = "查询会员消费充值明细记录")
    @PostMapping(value = "/detail/record")
    public Result<PageGrid<MemberRecordParam>> memberRecord(HttpServletRequest req, @RequestBody PageParam<MemberRecord> param) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        MemberRecord memberRecord = new MemberRecord();
        if (!param.getData().getPhone().equalsIgnoreCase("string")) {
            memberRecord.setPhone(param.getData().getPhone());
        }
        memberRecord.setBeginDate(param.getData().getBeginDate());
        memberRecord.setEndDate(param.getData().getEndDate());
        memberRecord.setStoreId(storeId);
        param.setData(memberRecord);
        return showMemberClient.memberRecord(param);
    }

    @ApiOperation(value = "查询消费充值总额")
    @PostMapping(value = "/detail/total")
    public Result<Map<String, Object>> memberDetailTotal(HttpServletRequest req, @RequestBody MemberRecord memberRecord) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        memberRecord.setStoreId(storeId);
        return showMemberClient.memberDetailTotal(memberRecord);
    }

    @ApiOperation(value = "查询会员积分明细")
    @PostMapping(value = "/integral/record")
    public Result<PageGrid<IntegralDetail>> integralRecord(@RequestBody PageParam<MemberRecord> param) {
//        MemberRecord memberRecord = new MemberRecord();
//        if (!param.getData().getUserId().equalsIgnoreCase("string")) {
//            memberRecord.setPhone(param.getData().getUserId());
//        }
//        param.setData(memberRecord);
        return showMemberClient.integralRecord(param);
    }

    @ApiOperation(value = "根据用户id查询会员信息")
    @GetMapping(value = "/account/u/{userId}")
    public Result<MemberAccount> getInfoByUserId(@PathVariable("userId") @NotBlank String userId) {
        return showMemberClient.getMemberInfoByUserId(userId);
    }

    @ApiOperation(value = "查询会员支付")
    @PostMapping(value = "/detail/payment")
    public Result<PageGrid<MemberPaymentParam>> memberPayment(HttpServletRequest req, @RequestBody PageParam<MemberRecord> param) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        MemberRecord memberRecord = MemberRecord.builder().beginDate(param.getData().getBeginDate()).endDate(param.getData().getEndDate()).storeId(storeId).build();
        param.setData(memberRecord);
        return showMemberClient.memberPayment(param);
    }

    @ApiOperation(value = "查询会员充值和消费信息（包括今日）")
    @GetMapping(value = "/account/info")
    public Result<Map<String, Object>> selectMemberInfo() {
        return showMemberClient.selectMemberInfo();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //转换日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ApiOperation(value = "查看会员活动信息")
    @PostMapping(value = "/activity/info")
    public Result<PageGrid<MemberActivity>> dishesActivity(@RequestBody PageParam<MemberActivity> param) {
        return showMemberClient.dishesActivity(param);
    }

    @ApiOperation(value = "添加会员活动信息")
    @PostMapping(value = "/activity/")
    public Result addActivity(HttpSession session, @RequestBody @Validated MemberActivity memberActivity) {
        memberActivity.setCreator((String) session.getAttribute(Contacts.RESTAURANT_USER));
        memberActivity.setStoreId((String) session.getAttribute(Contacts.RESTAURANT_STORE));
        return showMemberClient.addActivity(memberActivity);
    }

    @ApiOperation(value = "修改会员活动信息")
    @PatchMapping(value = "/activity/")
    public Result modifyActivity(HttpSession session, @RequestBody MemberActivity memberActivity) {
        memberActivity.setModifier((String) session.getAttribute(Contacts.RESTAURANT_USER));
        return showMemberClient.modifyActivity(memberActivity);
    }

    @ApiOperation(value = "修改会员活动信息的状态")
    @PatchMapping(value = "/activity/{markId}")
    public Result statusActivity(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.statusActivity(markId);
    }

    @ApiOperation(value = "根据主键查询会员等级")
    @GetMapping(value = "/grade/{markId}")
    public Result<MemberGrade> selectOne(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.selectOne(markId);
    }

    @ApiOperation(value = "查询会员等级列表")
    @PostMapping(value = "/grade/queryAll")
    public Result<List<MemberGrade>> queryAll(@RequestBody MemberGrade memberGrade) {
        return showMemberClient.queryAll(memberGrade);
    }

    @ApiOperation(value = "添加会员等级")
    @PostMapping(value = "/grade")
    public Result add(HttpSession session, @RequestBody MemberGrade memberGrade) {
        memberGrade.setCreator((String) session.getAttribute(Contacts.RESTAURANT_USER));
        return showMemberClient.add(memberGrade);
    }

    @ApiOperation(value = "修改会员等级")
    @PatchMapping(value = "/grade")
    public Result modify(HttpSession session, @RequestBody MemberGrade memberGrade) {
        memberGrade.setModifier((String) session.getAttribute(Contacts.RESTAURANT_USER));
        return showMemberClient.modify(memberGrade);
    }

    @ApiOperation(value = "删除会员等级")
    @DeleteMapping(value = "/grade/{markId}")
    public Result delete(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.delete(markId);
    }

    @ApiOperation(value = "查询可用会员等级列表")
    @PostMapping(value = "/grade/list")
    public Result<List<MemberGrade>> queryGrade(@RequestBody MemberGrade memberGrade) {
        memberGrade.setStatus(1);
        return showMemberClient.queryAll(memberGrade);
    }

    @ApiOperation(value = "删除会员活动信息")
    @DeleteMapping(value = "/activity/{markId}")
    public Result deleteActivity(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.deleteActivity(markId);
    }
}
