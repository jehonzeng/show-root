package com.szhengzhu.bean.ordering.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CommoditySpecsVo implements Serializable {

    private static final long serialVersionUID = 8891096942982533106L;

    private String specsId;
    
    private Integer minValue;

    private Integer maxValue;
    
    private List<CommodityItemVo> itemList;
}
