package com.szhengzhu.bean.ordering;

import java.io.Serializable;

import lombok.Data;

@Data
public class CommodityImg implements Serializable{
    
    private static final long serialVersionUID = 2917752428733120384L;

    private String commodityId;

    private String imgId;
    
    private Integer type;

    private Integer sort;
}