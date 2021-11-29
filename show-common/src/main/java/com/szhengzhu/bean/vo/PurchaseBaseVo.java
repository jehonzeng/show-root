package com.szhengzhu.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseBaseVo implements Serializable{
  
    private static final long serialVersionUID = 6046610842341321114L;
    
    private String orderNo;
    
    private String orderStatus;
    
    private String productName;
    
    private Integer productType;

    /** 每种商品数量 */
    private Integer quantity;
    
    private String foodId;

    /** 每种食材总量 */
    private BigDecimal count;

}
