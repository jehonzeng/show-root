package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class AccessoryModel implements Serializable {
    
    private static final long serialVersionUID = 5956336182993829641L;

    private String accessoryId;
    
    private Integer quantity = 1;
    
    private String name;
    
    private BigDecimal salePrice;
    
    private Integer status = 0; // 0:正常  2：库存不足  3：该商品已下架
}
