package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.annotation.NoRepeatSubmit;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.bean.member.MemberDetail;
import com.szhengzhu.bean.ordering.Indent;
import com.szhengzhu.bean.ordering.param.*;
import com.szhengzhu.bean.ordering.vo.IndentBaseVo;
import com.szhengzhu.bean.ordering.vo.IndentVo;
import com.szhengzhu.bean.xwechat.vo.IndentModel;
import com.szhengzhu.code.MemberCode;
import com.szhengzhu.code.PayTypeCode;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;

@Validated
@Api(tags = "订单：IndentController")
@RestController
@RequestMapping("/v1/indent")
public class IndentController {

    @Resource
    private ShowMemberClient showMemberClient;

    @Resource
    private ShowOrderingClient showOrderingClient;

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台：直接下单")
    @PostMapping(value = "/res/create")
    public Result createBatch(HttpServletRequest req, @RequestBody @Validated IndentParam indentParam) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        indentParam.setEmployeeId(employeeId);
        return showOrderingClient.createIndentBatch(indentParam);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台：修改订单商品")
    @PatchMapping(value = "/res")
    public Result modify(@RequestBody @Validated DetailParam detailParam) {
        return showOrderingClient.modifyDetail(detailParam);
    }

    //    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台：删除订单商品(数量减1)")
    @DeleteMapping(value = "/res/{detailId}")
    public Result delete(HttpServletRequest req, @PathVariable("detailId") @NotBlank String detailId) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        return showOrderingClient.deleteDetail(detailId, employeeId);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台：确认小程序用户订单")
    @GetMapping(value = "/res/confirm")
    public Result confirm(HttpServletRequest req, @RequestParam("tableId") @NotBlank String tableId) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        return showOrderingClient.confirmIndent(tableId, employeeId);
    }

    @ApiOperation(value = "点餐平台：获取订单及商品列表")
    @GetMapping(value = "/res/detail/list/{indentId}")
    public Result<IndentBaseVo> listDetailById(@PathVariable("indentId") @NotBlank String indentId) {
        return showOrderingClient.listIndentDetailById(indentId);
    }

    @ApiOperation(value = "点餐平台:获取订单预览单信息")
    @GetMapping(value = "/res/model/{indentId}")
    public Result<IndentModel> getIndentInfo(@PathVariable("indentId") @NotBlank String indentId) {
        return showOrderingClient.getIndentModel(indentId);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台支付：现金支付")
    @PostMapping(value = "/res/pay/" + PayTypeCode.CASH)
    public Result<String> cashPay(HttpServletRequest req, @RequestBody @Validated IndentPayParam payParam) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        payParam.setEmployeeId(employeeId);
        return showOrderingClient.payIndent(payParam);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台支付：会员支付")
    @PostMapping(value = "/res/pay/" + PayTypeCode.MEMBER)
    public Result<String> memberPay(HttpServletRequest req, @RequestBody @Validated IndentPayParam payParam) {
        // 检查是否绑定会员
        Result<Indent> indentResult = showOrderingClient.getIndentInfo(payParam.getIndentId());
        String memberId = indentResult.getData().getMemberId();
        ShowAssert.checkTrue(StrUtil.isEmpty(memberId), StatusCode._5027);
        // 检查会员余额是否够
        Result<MemberAccount> accountResult = showMemberClient.getMemberInfo(memberId);
        MemberAccount account = accountResult.getData();
        ShowAssert.checkTrue(payParam.getPayAmount().compareTo(account.getTotalAmount()) > 0, StatusCode._4035);
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        MemberDetail detail = MemberDetail.builder().accountId(indentResult.getData().getMemberId())
                .amount(payParam.getPayAmount()).creator(employeeId).type(MemberCode.CONSUME.code)
                .storeId(storeId).indentId(payParam.getIndentId()).build();
        Result<String> memberResult = showMemberClient.memberConsume(detail);
        ShowAssert.checkResult(memberResult);
        String detailId = memberResult.getData();
        payParam.setUserId(account.getUserId());
        payParam.setEmployeeId(employeeId);
        payParam.setConsumptionId(detailId);
        Result<String> result = showOrderingClient.payIndent(payParam);
        if (!result.getCode().equals(Contacts.SUCCESS_CODE)) {
            ShowAssert.checkResult(showMemberClient.deleteMemberDetail(detail.getMarkId(), employeeId));
        }
        return result;
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台支付：优惠类支付")
    @PostMapping(value = "/res/pay/" + PayTypeCode.COUPON)
    public Result couponPay(HttpServletRequest req, @RequestBody @Validated IndentPayParam payParam) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        payParam.setEmployeeId(employeeId);
        return showOrderingClient.couponPayIndent(payParam);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台支付：代金类支付")
    @PostMapping(value = "/res/pay/" + PayTypeCode.VOUCHER)
    public Result voucherPay(HttpServletRequest req, @RequestBody @Validated IndentPayParam payParam) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        payParam.setEmployeeId(employeeId);
        return showOrderingClient.voucherPayIndent(payParam);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台支付：会员券支付")
    @PostMapping(value = "/res/pay/" + PayTypeCode.TICKET)
    public Result ticketPay(HttpServletRequest req, @RequestBody @Validated IndentPayParam payParam) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        payParam.setEmployeeId(employeeId);
        return showOrderingClient.ticketPayIndent(payParam);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台：结账退款")
    @DeleteMapping(value = "/res/pay/{markId}")
    public Result deletePay(HttpServletRequest req, @PathVariable("markId") @NotBlank String markId) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        return showOrderingClient.deleteIndentPay(markId, employeeId);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台：结账")
    @GetMapping(value = "/res/bill/{indentId}")
    public Result bill(HttpServletRequest req, @PathVariable("indentId") @NotBlank String indentId) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        return showOrderingClient.billIndent(indentId, employeeId);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台：反结账")
    @GetMapping(value = "/res/cancel/bill/{indentId}")
    public Result<String> cancelBill(HttpServletRequest req, @PathVariable("indentId") @NotBlank String indentId) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        return showOrderingClient.cancelBillIndent(indentId, employeeId);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台：打印预览单")
    @GetMapping(value = "/res/print/preview")
    public Result printPreview(HttpServletRequest req, @RequestParam("indentId") @NotBlank String indentId) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        return showOrderingClient.printPreview(indentId, employeeId);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台：打印结账单")
    @GetMapping(value = "/res/print/bill")
    public Result printBill(HttpServletRequest req, @RequestParam("indentId") @NotBlank String indentId) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        return showOrderingClient.printBill(indentId, employeeId);
    }

    @ApiOperation(value = "点餐平台：获取账单管理分页列表")
    @PostMapping(value = "/res/page")
    public Result<PageGrid<IndentVo>> page(HttpServletRequest req, @RequestBody PageParam<IndentPageParam> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        IndentPageParam param = ObjectUtil.isNull(pageParam.getData()) ? new IndentPageParam() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageIndent(pageParam);
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台：扫码绑定会员")
    @GetMapping(value = "res/member/bind")
    public Result bindIndent(@RequestParam("indentId") @NotBlank String indentId,
                             @RequestParam(value = "mark", required = false) String mark,
                             @RequestParam(value = "phone", required = false) String phone) {
        Result<String> result = showMemberClient.scanCode(mark, phone);
        ShowAssert.checkTrue(!result.isSuccess(), StatusCode._4047);
        return showOrderingClient.bindMember(indentId, result.getData());
    }

    @NoRepeatSubmit
    @ApiOperation(value = "点餐平台：商品优惠")
    @PatchMapping(value = "/res/detail/discount")
    public Result discount(HttpServletRequest req, @RequestBody @Validated DetailDiscountParam discountParam) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        discountParam.setEmployeeId(employeeId);
        return showOrderingClient.discountDetail(discountParam);
    }

    @ApiOperation(value = "点餐平台：打印收入总单")
    @PostMapping(value = "/res/income")
    public Result printIncome(HttpServletRequest req, @RequestBody IncomeParam incomeParam) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        incomeParam.setEmployeeId(employeeId);
        incomeParam.setStoreId(storeId);
        return showOrderingClient.printIncome(incomeParam);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //转换日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
