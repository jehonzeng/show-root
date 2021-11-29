package com.szhengzhu.bean.member.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员支付
 */
@Data
public class MemberPaymentParam implements Serializable {
    /**
     * 订单ID
     */
    private String mark_id;
    /**
     * 订单号
     */
    private String indent_no;
    /**
     * 下单时间
     */
    private Date indent_time;
    /**
     * 结账时间
     */
    private Date bill_time;
    /**
     * 会员支付时间
     */
    private Date create_time;
    /**
     * 订单总额
     */
    private BigDecimal indent_total;
    /**
     * 会员支付金额
     */
    private BigDecimal amount;
}
