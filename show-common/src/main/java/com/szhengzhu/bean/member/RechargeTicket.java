package com.szhengzhu.bean.member;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class RechargeTicket implements Serializable {
    
    private static final long serialVersionUID = -8749841057137598878L;

    private String ruleId;

    private String templateId;
    
    private Integer quantity;
}