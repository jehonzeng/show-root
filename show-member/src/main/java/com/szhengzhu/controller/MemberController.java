package com.szhengzhu.controller;

import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.bean.member.MemberByType;
import com.szhengzhu.bean.member.MemberDetail;
import com.szhengzhu.bean.member.param.MemberDetailParam;
import com.szhengzhu.bean.member.param.RechargeParam;
import com.szhengzhu.bean.member.vo.MemberAccountVo;
import com.szhengzhu.bean.member.vo.MemberTicketVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.MemberService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @PostMapping(value = "/account/page")
    public PageGrid<MemberAccount> pageAccount(@RequestBody PageParam<MemberByType> param) {
        return memberService.pageAccount(param);
    }

    @PostMapping(value = "/account/add")
    public Result<String> addAccount(@RequestBody @Validated MemberAccount account) {
        return new Result<>(memberService.addAccount(account));
    }

    @PatchMapping(value = "/account/modify")
    public MemberAccount modifyAccount(@RequestBody MemberAccount base) {
        return memberService.modifyAccount(base);
    }

    @GetMapping(value = "/account/{markId}")
    public MemberAccount getInfo(@PathVariable("markId") @NotBlank String markId) {
        return memberService.getInfo(markId);
    }

    @GetMapping(value = "/account/vo/{markId}")
    public MemberAccountVo getVoInfo(@PathVariable("markId") @NotBlank String markId) {
        return memberService.getVoInfoById(markId);
    }

    @GetMapping(value = "/account/u/{userId}")
    public MemberAccount getInfoByUserId(@PathVariable("userId") @NotBlank String userId) {
        return memberService.getInfoByUserId(userId);
    }

    @GetMapping(value = "/account/p/{phone}")
    public List<MemberAccountVo> getInfoByPhone(@PathVariable("phone") @NotBlank String phone) {
        return memberService.getInfoByNoOrPhone(phone, null);
    }

    @GetMapping(value = "/account/n/{accountNo}")
    public List<MemberAccountVo> getInfoByNo(@PathVariable("accountNo") @NotBlank String accountNo) {
        return memberService.getInfoByNoOrPhone(null, accountNo);
    }

    @GetMapping(value = "/account/info/{memberId}/res")
    public MemberTicketVo resInfoById(@PathVariable("memberId") @NotBlank String memberId) {
        return memberService.resInfoById(memberId);
    }

    @PostMapping(value = "/recharge/res")
    public MemberAccount recharge(@RequestBody @Validated MemberDetailParam detailParam) {
        return memberService.recharge(detailParam);
    }

    @PostMapping(value = "/recharge/rule/res")
    public void rechargeByRule(@RequestBody @Validated RechargeParam param) {
        memberService.rechargeByRule(param);
    }

    @GetMapping(value = "/recharge/rule/x")
    public MemberDetail rechargeByRule(@RequestParam("ruleId") @NotBlank String ruleId, @RequestParam("userId") @NotBlank String userId,
                                       @RequestParam("xopenId") String xopenId, @RequestParam(value = "indentTotal", required = false) BigDecimal indentTotal) {
        return memberService.rechargeByRule(ruleId, userId, xopenId, indentTotal);
    }

    @PostMapping(value = "/consume")
    public Result<String> consume(@RequestBody @Validated MemberDetail detail) {
        return new Result<>(memberService.consume(detail));
    }

    @GetMapping(value = "/account/total")
    public BigDecimal getTotalByUserId(@RequestParam("userId") @NotBlank String userId) {
        return memberService.getTotalByUserId(userId);
    }

    @GetMapping(value = "/create/bar/mark")
    public Result<String> createBarMark(@RequestParam("userId") @NotBlank String userId) {
        return new Result<>(memberService.createBarMark(userId));
    }

    @GetMapping(value = "/scancode")
    public Result<String> scanCode(@RequestParam(value = "mark", required = false) String mark,
                                   @RequestParam(value = "phone", required = false) String phone) {
        return new Result<>(memberService.scanCodeByMark(mark, phone));
    }

    @GetMapping(value = "/account/info")
    public Map<String, Object> getMemberInfo() {
        return memberService.getMemberInfo();
    }

    @GetMapping(value = "/discount")
    public BigDecimal selectMemberDiscount(@RequestParam("markId") String markId) {
        return memberService.selectMemberDiscount(markId);
    }
}
