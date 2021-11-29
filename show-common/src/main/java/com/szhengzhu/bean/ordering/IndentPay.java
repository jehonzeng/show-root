package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IndentPay implements Serializable {
    
    private static final long serialVersionUID = 8057412873950764240L;

    private String markId;

    private String indentId;

    private String payId;

    private String payName;

    private String discountId;

    private Integer quantity;

    private BigDecimal amount;

    private BigDecimal payAmount;

    private String userId;
    
    private String consumptionId;

    private String employeeId;

    private String code;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
}