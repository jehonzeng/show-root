package com.szhengzhu.bean.member.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Data
public class MemberDetailParam implements Serializable {

    private static final long serialVersionUID = 4867533493682989551L;

    @NotBlank
    private String accountId;
    
    private String employeeId;

    @NotNull
    private BigDecimal amount;
    
    /** 赠送金额 */
    private BigDecimal bonusAmount;

    /** 赠送积分 */
    private Integer bonusIntegral;

    private String storeId;
}
