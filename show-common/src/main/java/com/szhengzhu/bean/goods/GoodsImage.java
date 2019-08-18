package com.szhengzhu.bean.goods;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 商品图片信息
 * 
 * @author Administrator
 * @date 2019年3月5日
 */
@Data
@ApiModel
public class GoodsImage implements Serializable {

    private static final long serialVersionUID = -6741440752666829711L;

    @ApiModelProperty(value = "ID", name = "markId")
    private String markId;

    @ApiModelProperty(value = "关联基础商品", name = "goodsId")
    private String goodsId;

    @ApiModelProperty(value = "关联属性集", name = "specificationIds")
    private String specificationIds;

    @ApiModelProperty(value = "关联图片路径id", name = "imagePath")
    private String imagePath;

    @ApiModelProperty(value = "类型（0：小展示图；1：大展示图；2：规格图）", name = "serverType",allowableValues="0,1,2")
    private Integer serverType;

    @ApiModelProperty(value="显示顺序",name = "sort")
    private Integer sort;
    
}