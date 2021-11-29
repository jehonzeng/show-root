package com.szhengzhu.bean.goods;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 套餐单品信息
 * 
 * @author Administrator
 * @date 2019年4月17日
 */
@Data
public class MealItem implements Serializable {
    
    private static final long serialVersionUID = -2839488330135879702L;

    private String markId;

    @NotBlank
    private String mealId;

    @NotBlank
    private String goodsId;

    @NotBlank
    private String specificationIds;

    private Integer quantity;

    private Integer sort;

}