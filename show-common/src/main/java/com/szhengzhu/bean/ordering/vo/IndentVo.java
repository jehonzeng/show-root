package com.szhengzhu.bean.ordering.vo;

import com.szhengzhu.code.IndentStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class IndentVo implements Serializable {

    private static final long serialVersionUID = 2995202739872683358L;

    private String indentId;
    
    private String tableCode;
    
    private BigDecimal indentTotal;
    
    private String indentNo;
    
    private String indentStatus;
    
    private Date indentTime;
    
    private String indentUser;
    
    private String billTime;
    
    private String billBy;
    
    private String statusName;
    
    public void setIndentStatus(String indentStatus) {
        this.indentStatus = indentStatus;
        this.statusName = IndentStatus.getValue(indentStatus);
    }
}
