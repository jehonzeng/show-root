package com.szhengzhu.bean.member;

import com.szhengzhu.code.IntegralCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 积分记录
 * @author Administrator
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IntegralDetail implements Serializable {

    private static final long serialVersionUID = 3489593588051423215L;

    private String markId;

    private String accountId;

    private String userId;

    private Integer type;

    @NotNull
    private Integer integralLimit;

    private Date createTime;

    private String remark;

    /* 1  有效  0 无效 -1 已过期 */
    private Integer status;

    public void setIntegralType(IntegralCode integralCode) {
        this.type = integralCode.code;
        this.remark = integralCode.msg;
    }
}
