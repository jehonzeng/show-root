package com.szhengzhu.bean.xwechat.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class PriceModel implements Serializable {
    
    private static final long serialVersionUID = 2163810651017663699L;

    private String priceId;

    private String unit;
    
    private Integer minPoint;
    
    private Integer maxPoint;

    private Integer priceType;

    private BigDecimal costPrice;
    
    private BigDecimal salePrice;
    
    private BigDecimal integralPrice;
    
    private Boolean defaults;
}
