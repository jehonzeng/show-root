package com.szhengzhu.bean.ordering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Indent implements Serializable {
    
    private static final long serialVersionUID = -3578956502788288749L;

    private String markId;

    private String indentNo;

    private String storeId;

    private String tableId;

    private Integer manNum;

    private String indentUser;

    private Date indentTime;

    private String memberId;

    private BigDecimal baseTotal;

    private BigDecimal indentTotal;

    private BigDecimal marketDiscount;
    
    private String marketIds;

    private String employeeId;

    private String tempNum;
    
    private Date billTime;
    
    private String billBy;

    private String indentStatus;
    
    private String modifier;
    
    private Date modifyTime;

    private String remark;
}