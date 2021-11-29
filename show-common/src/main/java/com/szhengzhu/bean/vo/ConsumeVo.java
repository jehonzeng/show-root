package com.szhengzhu.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jehon
 */
@Data
public class ConsumeVo implements Serializable {

    private static final long serialVersionUID = 3698737153307583743L;

    @NotBlank
    /* 会员id */
    private String accountId;

    @NotBlank
    private String userId;

    /* 消费金额 */
    private BigDecimal amount;

    /* 消费积分 */
    private Integer integral;

    private String employeeId;

    private String storeId;
}
