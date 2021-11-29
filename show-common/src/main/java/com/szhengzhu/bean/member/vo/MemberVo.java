package com.szhengzhu.bean.member.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员信息（消费、充值）
 * @author Administrator
 */
@Data
public class MemberVo implements Serializable {
    /**
     * 会员总人数
     */
    private int memberTotalNum;
    /**
     * 会员消费总金额
     */
    private BigDecimal consumeTotal;
    /**
     * 会员充值金额
     */
    private BigDecimal rechargeTotal;
    /**
     * 会员账户余额
     */
    private BigDecimal amount;
    /**
     * 会员有交易人数
     */
    private int tradeNum;
    /**
     * 会员无交易人数
     */
    private int noTradeNum;
    /**
     * 会员新增人数
     */
    private int memberNum;
    /**
     * 会员新增消费
     */
    private BigDecimal consume;
    /**
     * 会员新增充值
     */
    private BigDecimal recharge;
}
