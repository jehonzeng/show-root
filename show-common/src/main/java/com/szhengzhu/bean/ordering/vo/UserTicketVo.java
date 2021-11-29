package com.szhengzhu.bean.ordering.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class UserTicketVo implements Serializable {

    private static final long serialVersionUID = -4524994609841865791L;

    private String markId;
    
    private String name;
    
    private Integer type;
    
    private String description;
    
    private BigDecimal limitPrice;
    
    private BigDecimal discount;
    
    private Date startTime;
    
    private Date stopTime;
    
    private Integer status;
}
