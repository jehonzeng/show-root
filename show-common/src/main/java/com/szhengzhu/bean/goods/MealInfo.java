package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class MealInfo implements Serializable{

    private static final long serialVersionUID = -5183426891493929961L;

    private String markId;

    private String theme;

    private BigDecimal basePrice;

    private BigDecimal salePrice;

    private Integer stockSize;

    private Boolean serverStatus;

    private String description;

    private Integer sort;

    private String mealNo;
}