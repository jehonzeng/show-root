package com.szhengzhu.bean.goods;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class FoodsInfo implements Serializable {

    private static final long serialVersionUID = 7723640545715689667L;

    private String markId;

    @NotBlank
    private String foodName;

    private Boolean serverStatus;

    private BigDecimal purchaseRate;

    private String imagePath;

    private String unit;

}