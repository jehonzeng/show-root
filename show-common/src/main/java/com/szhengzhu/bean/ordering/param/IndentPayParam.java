package com.szhengzhu.bean.ordering.param;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IndentPayParam implements Serializable {

    private static final long serialVersionUID = 4608531389248734997L;

    @NotBlank
    private String indentId;
    
    private String userId;

    private BigDecimal payAmount;

    private String employeeId;

    private String payId;
    
    /** 当使用券时，记录券id */
    private String discountId;

    /** 保留，用于存小程序支付记录用户code */
    private String code;
    
    /** 会员支付明细 */
    private String consumptionId;
}
