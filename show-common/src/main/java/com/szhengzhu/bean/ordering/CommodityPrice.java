package com.szhengzhu.bean.ordering;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CommodityPrice implements Serializable {
    
    private static final long serialVersionUID = 6092094114291451243L;

    private String markId;

    @NotBlank
    private String commodityId;

    private String unit;

    private Integer minPoint;

    private Integer basePoint;

    private Integer maxPoint;
    
    private BigDecimal basePrice;

    private Integer priceType;

    private BigDecimal salePrice;

    private Integer integralPrice;

    private Boolean defaults;

    private Date createTime;

    private Date modifyTime;
}