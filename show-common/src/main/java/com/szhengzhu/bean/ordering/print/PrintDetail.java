package com.szhengzhu.bean.ordering.print;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PrintDetail {

    private String detailId;

    private String name;
    
    private Integer quantity;
    
    private BigDecimal price;
    
    private String unit;
    
    /** 合计 */
    private BigDecimal subtotal;
    
    /** 规格*/
    private String specs; 
    
    public BigDecimal getSubtotal() {
        this.subtotal = price.multiply(new BigDecimal(quantity));
        return this.subtotal;
    }
}
