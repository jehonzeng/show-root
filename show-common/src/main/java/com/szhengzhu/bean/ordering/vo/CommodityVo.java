package com.szhengzhu.bean.ordering.vo;

import java.util.List;

import com.szhengzhu.bean.ordering.Commodity;
import com.szhengzhu.bean.ordering.CommodityPrice;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommodityVo extends Commodity {

    private static final long serialVersionUID = -3677553706506201513L;

    private List<CommodityPrice> priceList;
    
    private List<CommoditySpecsVo> specsList;
    
    private String[] cateList;
    
    private String[] tagList;
}
