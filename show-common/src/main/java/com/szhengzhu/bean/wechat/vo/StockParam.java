package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StockParam implements Serializable {

    private static final long serialVersionUID = -5924488530280096797L;

    private String goodsId;

    @NotBlank
    private String specIds;

    @NotNull
    private Integer productType;
    
    private String addressId;
}
