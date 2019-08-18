package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class StockParam implements Serializable {

    private static final long serialVersionUID = -5924488530280096797L;

    private String goodsId;
    
    private String specIds;
    
    private Integer productType;
    
    private String addressId;
}
