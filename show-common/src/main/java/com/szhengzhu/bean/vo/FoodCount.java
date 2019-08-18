package com.szhengzhu.bean.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class FoodCount implements Serializable{

    private static final long serialVersionUID = 946687518393780804L;
    
    private String foodId;//食材或者附属品id

    private BigDecimal totalCount;
    
}
