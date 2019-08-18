package com.szhengzhu.bean.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class PurchaseBaseVo implements Serializable{
  
    private static final long serialVersionUID = 6046610842341321114L;
    
    private String orderNo;
    
    private String orderStatus;
    
    private String productName;
    
    private Integer productType; 
    
    private Integer quantity;//每种商品数量
    
    private String foodId;
    
    private BigDecimal count;//每种食材总量

}
