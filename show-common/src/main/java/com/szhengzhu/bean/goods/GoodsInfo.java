package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品基础信息
 * 
 * @author Administrator
 * @date 2019年2月28日
 */
@Data
@ApiModel
public class GoodsInfo implements Serializable {

    private static final long serialVersionUID = -6198589820280778546L;

    @ApiModelProperty(value = "ID", name = "markId")
    private String markId;

    @ApiModelProperty(value = "商品名称", name = "goodsName")
    private String goodsName;

    @ApiModelProperty(value = "商品描述", name = "description")
    private String description;

    // 状态动态属性编码获取
    @ApiModelProperty(value = "商品状态码", name = "serverStatus")
    private String serverStatus;

    // 菜系动态属性编码获取
    @ApiModelProperty(value = "菜系编码", name = "cookStyle")
    private String cookStyle;

    @ApiModelProperty(value = "供货类型（0：自营；1：非自营）", name = "serverType", allowableValues = "0,1")
    private Integer serverType;

    @ApiModelProperty(value = "基础成本价", name = "basePrice")
    private BigDecimal basePrice;

    @ApiModelProperty(value = "基础售价", name = "salePrice")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "商品单位", name = "unit")
    private String unit;

    @ApiModelProperty(value = "手动上架时间", name = "upperTime")
    private Date upperTime;

    @ApiModelProperty(value = "手动下架时间", name = "downTime")
    private Date downTime;

    @ApiModelProperty(value = "待上架时间", name = "preUpperTime")
    private Date preUpperTime;

    @ApiModelProperty(value = "待下架时间", name = "preDownTime")
    private Date preDownTime;

    @ApiModelProperty(value = "创建时间", name = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "关联创建人", name = "creator")
    private String creator;

    @ApiModelProperty(value = "修改时间", name = "modifyTime")
    private Date modifyTime;

    @ApiModelProperty(value = "关联修改人", name = "modifier")
    private String modifier;

    @ApiModelProperty(value = "关联分类", name = "categoryId")
    private String categoryId;

    @ApiModelProperty(value = "关联商品类型", name = "typeId")
    private String typeId;

    @ApiModelProperty(value = "关联品牌", name = "brandId")
    private String brandId;

    @ApiModelProperty(value = "关联厨师", name = "cooker")
    private String cooker;

    @ApiModelProperty(value = "显示顺序", name = "sort")
    private Integer sort;

}