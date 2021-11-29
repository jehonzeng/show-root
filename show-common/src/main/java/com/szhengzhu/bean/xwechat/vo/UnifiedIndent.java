package com.szhengzhu.bean.xwechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Data
public class UnifiedIndent implements Serializable {

    private static final long serialVersionUID = -1454058905323174917L;

    @NotBlank
    private String indentId;
    
    private String userId;
    
    private String code;
    
    private String memberId;
    
    private String consumptionId;
    
    private BigDecimal amount;
    
    private String ticketId;
}
