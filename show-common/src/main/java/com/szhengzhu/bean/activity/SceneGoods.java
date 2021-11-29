package com.szhengzhu.bean.activity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SceneGoods implements Serializable {
    
    private static final long serialVersionUID = -7341657340062484505L;

    private String markId;

    private String sceneId;

    @NotBlank
    private String goodsName;

    private BigDecimal basePrice;

    private BigDecimal salePrice;

    private String description;

    private String imagePath;

    private Integer stockSize;

    private Integer receiveSize;

    private Integer sort;
    
    private Boolean serverStatus;
}