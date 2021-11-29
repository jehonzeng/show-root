package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.GoodsInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsVo extends GoodsInfo {

    private static final long serialVersionUID = -8226154501094027461L;

    private String goodsStatus;

    private String goodsStyle;

    private String goodsUnit;

    private String creatorName;

    private String modifierName;
    
    private String typeName;

    private String categoryName;

    private String brandName;

    private String cookerName;
    
    private String vaildName;//验证是否为菜品

}
