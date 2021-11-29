package com.szhengzhu.bean.activity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TeambuyInfo implements Serializable {
    
    private static final long serialVersionUID = 2308177675065139134L;

    private String markId;

    @NotBlank
    private String theme;
    
    private String description;

    @NotBlank
    private String goodsId;

    private String goodsName;

    private String specificationIds;

    private Integer type;

    @NotNull
    private Date startTime;

    @NotNull
    private Date stopTime;

    @NotNull
    private Integer reqCount;

    @NotNull
    private Integer vaildTime;

    private BigDecimal price;
    
    private Integer totalStock;

    private Integer limited;

    private Boolean free;

    private Boolean serverStatus;
    
    private String specs;
}