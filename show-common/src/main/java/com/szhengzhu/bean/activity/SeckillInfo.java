package com.szhengzhu.bean.activity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class SeckillInfo implements Serializable {

    private static final long serialVersionUID = -8886625996233932215L;

    private String markId;

    @NotBlank
    private String theme;
    
    private String description;

    @NotBlank
    private String goodsId;

    private String goodsName;

    private String specificationIds;

    @NotNull
    private BigDecimal price;
    
    private Integer totalStock;

    @NotNull
    private Date startTime;

    @NotNull
    private Date stopTime;
    
    private Integer limited;

    private Boolean free;

    private Boolean serverStatus;
    
    private String specs;
}