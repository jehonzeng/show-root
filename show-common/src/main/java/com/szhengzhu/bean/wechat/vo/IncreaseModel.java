package com.szhengzhu.bean.wechat.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class IncreaseModel implements Serializable {

    private static final long serialVersionUID = -7857470981013133781L;

    private String increaseId;
    
    private String goodsId;
    
    private String goodsName;
    
    private BigDecimal basePrice;
    
    private BigDecimal salePrice;
    
    private Integer status = 0;// 0:正常  1：不可配送  2：库存不足  3：该商品已下架 4: 未达到购买门槛
    
    private String storehouseId;
}
