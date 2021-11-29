package com.szhengzhu.bean.ordering.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class CommodityItemVo implements Serializable {

    private static final long serialVersionUID = -3823876226049625619L;

    private String itemId;

//    private String name;

    private BigDecimal markupPrice;

    private Integer sort;
    
    private Boolean checked;
}
