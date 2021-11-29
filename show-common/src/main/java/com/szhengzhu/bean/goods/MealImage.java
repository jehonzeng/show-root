package com.szhengzhu.bean.goods;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 套餐图片信息
 * 
 * @author Administrator
 * @date 2019年4月19日
 */
@Data
public class MealImage implements Serializable {

    private static final long serialVersionUID = 342122462742603896L;

    private String markId;

    @NotBlank
    private String mealId;

    private Integer serverType;

    private String imagePath;

    private Integer sort;

}