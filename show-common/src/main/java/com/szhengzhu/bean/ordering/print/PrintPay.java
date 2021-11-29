package com.szhengzhu.bean.ordering.print;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Data
public class PrintPay {

    /** 可以是订单支付明细id，也可支付方式id */
    private String payId;

    private String payName;

    private Integer quantity;

    /** 实收金额*/
    private BigDecimal amount;

    /** 营收金额 */
    private BigDecimal payAmount;
}
