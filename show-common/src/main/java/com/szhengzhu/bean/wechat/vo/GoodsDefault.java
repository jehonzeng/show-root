package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class GoodsDefault implements Serializable {

    private static final long serialVersionUID = 6455836024233881227L;

    private String goodsId;
    
    private Integer goodsType;
    
    private String goodsName;
    
    private String specificationIds;
    
    private BigDecimal salePrice;
}
