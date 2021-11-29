package com.szhengzhu.bean.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SceneInfo implements Serializable {

    private static final long serialVersionUID = 7469417294936976447L;

    private String markId;

    @NotBlank
    private String theme;

    private Integer promotionType;

    private BigDecimal discount;

    private String limitRegion;

    private Date createTime;

    private Date startTime;

    private Date stopTime;

    private Boolean serverStatus;
    
    private String regionDesc;
}