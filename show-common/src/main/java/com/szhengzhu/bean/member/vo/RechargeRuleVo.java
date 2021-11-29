package com.szhengzhu.bean.member.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class RechargeRuleVo implements Serializable {

    private static final long serialVersionUID = -2241252445489638368L;

    private String rechargeId;
    
    private String theme;
    
    private String description;
    
    private BigDecimal limitAmount;
}
