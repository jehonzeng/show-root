package com.szhengzhu.bean.vo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 商品详细信息视图
 * 
 * @author Administrator
 * @date 2019年3月7日
 */
@Data
public class GoodsBaseVo {

    private String markId;

    private String attrList;

    private String depotName;

    private String serverStatus; // 库存商品有效否

    private BigDecimal basePrice;// 规格成本价

    private BigDecimal salePrice;// 规格售价

    private Integer totalStock;

    private Integer currentStock;

    private String goodsName;

    private String goodsStyle;// 菜系可为null

    private Integer supplyType;// 供货类型 :0自营 1非自营

    private String goodsStatus;

    private BigDecimal singleBasePrice;// 单成本价

    private BigDecimal singleSalePrice;// 单售价

    private String unit;// 商品规格单位

    private Date upperTime;

    private Date downTime;

    private Date preUpperTime;

    private Date preDownTime;

    private Date createTime;

    private Date modifyTime;

    private String modifierName;

    private String creatorName;

    private String cookerName;

    private String typeName;

    private String categoryName;

    private String brandName;

    private Integer sort;
    
    private String province;
    
    private String city;
    
    private String area;

}
