package com.szhengzhu.bean.activity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 
 * 活动礼物表
 * 
 * @author Administrator
 * @date 2019年9月25日
 */
@Data
public class GiftInfo implements Serializable {

    private static final long serialVersionUID = 1823654422172174942L;

    private String markId;

    @NotBlank
    private String giftName;

    @NotBlank
    private String productId;

    private Integer giftType;

    private BigDecimal price;

    private String specificationIds;

    private String imagePath;

    private String description;
    
    private Boolean giftStatus;
    
    private String productName;
    
    private String specList;
}