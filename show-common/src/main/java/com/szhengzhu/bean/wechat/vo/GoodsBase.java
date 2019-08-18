package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsBase implements Serializable {

    private static final long serialVersionUID = 5482012643705695086L;

    private String goodsId;
    
    private String goodsName;
    
    private String description;
    
    private Integer serverType; // 0：自营 1：非自营
    
//    private String goodsImage;
    
    private BigDecimal basePrice;  // 原价
    
    private BigDecimal salePrice;  // 售价
    
    private String iconPath;
    
    private Integer productType; // 0：单品， 1：菜品券, 2套餐，3附属品
    
    private BigDecimal praise;
    
    private String cooker;
    
    private String goodsStatus;
}
