package com.szhengzhu.bean.member.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RechargeParam implements Serializable {

    private static final long serialVersionUID = -902766323348142505L;

    @NotBlank
    private String ruleId;
    
    private String employeeId;

    @NotBlank
    private String accountId;
    
//    private String userId;
    
    private String storeId;
}
