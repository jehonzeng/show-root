package com.szhengzhu.bean.member;

import com.szhengzhu.code.MemberCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDetail implements Serializable {

    private static final long serialVersionUID = -4464590231151778779L;

    private String markId;

    private String parentId;

    @NotBlank
    private String accountId;

    private BigDecimal amount;

    private Date createTime;

    private String creator;

    private String storeId;

    private Integer type;

    private String remark;
    
    private String indentId;

    private Integer status;

    /** 操作人员（创建，撤回） */
    private String operator;

    /** 余额 */
    private BigDecimal surplusAmount;

    private BigDecimal bonusAmount;

    public void setType(Integer type) {
        this.type = type;
        this.remark = MemberCode.getValue(type);
    }

    public BigDecimal getBonusAmount() {
        if (this.bonusAmount == null) {
            this.bonusAmount = new BigDecimal("0.00");
        }
        return this.bonusAmount;
    }
}