package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.IndentPay;
import com.szhengzhu.bean.ordering.PayBack;
import com.szhengzhu.bean.ordering.PayRefund;
import com.szhengzhu.bean.ordering.param.IndentPayParam;
import com.szhengzhu.bean.xwechat.vo.CalcVo;
import com.szhengzhu.bean.xwechat.vo.UnifiedIndent;

import java.math.BigDecimal;

public interface IndentPayService {

    /**
     * 获取支付记录明细
     *
     * @param indentPayId
     * @return
     */
    IndentPay getInfo(String indentPayId);

    /**
     * 点餐平台：现金支付
     *
     * @param payParam
     * @return
     */
    String resPay(IndentPayParam payParam);

    /**
     * 点餐平台：优惠支付
     *
     * @param payParam
     * @return
     */
    void resCouponPay(IndentPayParam payParam);

    /**
     * 点餐平台：代金券支付
     *
     * @param payParam （payAmount为券的数量）
     * @return
     */
    void resVoucherPay(IndentPayParam payParam);

    /**
     * 点餐平台：会员券支付
     *
     * @param payParam
     * @return
     */
    void resTicketPay(IndentPayParam payParam);

    /**
     * 点餐平台删除支付
     *
     * @param markId
     * @param employeeId
     * @return
     */
    void deletePay(String markId, String employeeId);

    /**
     * 订单支付优惠计算
     *
     * @param indentCalc
     * @return
     */
    CalcVo xcalc(UnifiedIndent indentCalc);

    /**
     * 小程序用户微信支付
     *
     * @param unifiedIndent
     * @return
     */
    BigDecimal xwechatPay(UnifiedIndent unifiedIndent);

    /**
     * 小程序微信支付回调
     *
     * @param indentId
     * @return
     */
    void xwechatBack(String indentId);

    /**
     * 小程序会员支付
     *
     * @return
     */
    void xmemberPay(UnifiedIndent unifiedIndent);

    /**
     * 添加支付操作记录
     *
     * @param payBack
     * @return
     */
    void addPayBack(PayBack payBack);

    /**
     * 退款申请记录
     *
     * @param payRefund
     * @return
     */
    void addRefundBack(PayRefund payRefund);

    /**
     * 获取退款单号（如果存在就返回）
     *
     * @param payId
     * @return
     */
    String getRefundNo(String payId);

    BigDecimal selectAmount(String indentId);
}
