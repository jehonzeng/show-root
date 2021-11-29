package com.szhengzhu.bean.ordering.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class IndentPayVo implements Serializable {

    private static final long serialVersionUID = 1031585956204181345L;

    private String markId;
    
    private BigDecimal payAmount;
    
    private String payId;
    
    private String payName;
}
