package com.szhengzhu.controller;

import com.szhengzhu.annotation.NoRepeatSubmit;
import com.szhengzhu.bean.ordering.IndentPay;
import com.szhengzhu.bean.ordering.PayBack;
import com.szhengzhu.bean.ordering.PayRefund;
import com.szhengzhu.bean.ordering.param.IndentPayParam;
import com.szhengzhu.bean.xwechat.vo.CalcVo;
import com.szhengzhu.bean.xwechat.vo.UnifiedIndent;
import com.szhengzhu.code.PayTypeCode;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.IndentPayService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/indent/pay")
public class IndentPayController {

    @Resource
    private IndentPayService indentPayService;

    @GetMapping(value = "/{indentPayId}")
    public IndentPay getInfo(@PathVariable("indentPayId") @NotBlank String indentPayId) {
        return indentPayService.getInfo(indentPayId);
    }

    @PostMapping(value = "/" + PayTypeCode.COUPON)
    @NoRepeatSubmit
    public void resCouponPay(@RequestBody @Validated IndentPayParam payParam) {
        indentPayService.resCouponPay(payParam);
    }

    @NoRepeatSubmit
    @PostMapping(value = "/" + PayTypeCode.VOUCHER)
    public void resVoucherPay(@RequestBody @Validated IndentPayParam payParam) {
        indentPayService.resVoucherPay(payParam);
    }

    @NoRepeatSubmit
    @PostMapping(value = "/" + PayTypeCode.TICKET)
    public void resTicketPay(@RequestBody @Validated IndentPayParam payParam) {
        indentPayService.resTicketPay(payParam);
    }

    @NoRepeatSubmit
    @PostMapping(value = "")
    public Result<String> resPay(@RequestBody @Validated IndentPayParam payParam) {
        return new Result<>(indentPayService.resPay(payParam));
    }

    @NoRepeatSubmit
    @DeleteMapping(value = "/{markId}")
    public void deletePay(@PathVariable("markId") @NotBlank String markId, @RequestParam("employeeId") String employeeId) {
        indentPayService.deletePay(markId, employeeId);
    }

    @NoRepeatSubmit
    @PostMapping(value = "/calc/x")
    public CalcVo xcalc(@RequestBody @Validated UnifiedIndent indentCalc) {
        return indentPayService.xcalc(indentCalc);
    }

    @NoRepeatSubmit
    @PostMapping(value = "/wechat/x")
    public BigDecimal xwechatPay(@RequestBody @Validated UnifiedIndent unifiedIndent) {
        return indentPayService.xwechatPay(unifiedIndent);
    }

    @PostMapping(value = "/wechat/back/x")
    public void xwechatBack(@RequestParam("indentId") @NotBlank String indentId) {
        indentPayService.xwechatBack(indentId);
    }

    @NoRepeatSubmit
    @PostMapping(value = "/member/x")
    public void xmemberPay(@RequestBody @Validated UnifiedIndent unifiedIndent) {
        indentPayService.xmemberPay(unifiedIndent);
    }

    @PostMapping(value = "/back")
    public void addBack(@RequestBody PayBack payBack) {
        indentPayService.addPayBack(payBack);
    }

    @PostMapping(value = "/refund")
    public void addRefundBack(@RequestBody PayRefund refund) {
        indentPayService.addRefundBack(refund);
    }

    @GetMapping(value = "/refund/no/byid")
    public Result<String> getRefundNo(@RequestParam("payId") @NotBlank String payId) {
        return new Result<>(indentPayService.getRefundNo(payId));
    }

    @GetMapping("/amount/{indentId}")
    public BigDecimal selectAmount(@PathVariable("indentId") String indentId) {
        return indentPayService.selectAmount(indentId);
    }
}
