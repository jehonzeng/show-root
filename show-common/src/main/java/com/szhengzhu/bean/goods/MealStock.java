package com.szhengzhu.bean.goods;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class MealStock implements Serializable {

    private static final long serialVersionUID = 119440177751408037L;

    private String markId;

    @NotBlank
    private String storehouseId;

    @NotBlank
    private String mealId;

    private Boolean serverStatus;

    @NotNull
    private Integer totalStock;

    @NotNull
    private Integer currentStock;
    
    private String depotName;
    
    private String mealTheme;
}