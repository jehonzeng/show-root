package com.szhengzhu.bean.goods;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MealInfo implements Serializable{

    private static final long serialVersionUID = -5183426891493929961L;

    private String markId;

    @NotBlank
    private String theme;

    private BigDecimal basePrice;

    private BigDecimal salePrice;

    private Boolean serverStatus;

    private String description;

    private Integer sort;

    private String mealNo;
    
    private Date createTime;
}