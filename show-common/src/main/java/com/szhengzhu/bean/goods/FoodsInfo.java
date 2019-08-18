package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class FoodsInfo implements Serializable {

    private static final long serialVersionUID = 7723640545715689667L;

    private String markId;

    private String foodName;

    private Boolean serverStatus;

    private BigDecimal purchaseRate;

    private String imagePath;

    private String unit;

}