package com.szhengzhu.bean.goods;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品类型信息
 * 
 * @author Administrator
 * @date 2019年3月12日
 */
@Data
@ApiModel
public class GoodsType implements Serializable {

    private static final long serialVersionUID = -2043059443451101741L;

    @ApiModelProperty(value = "ID", name = "markId")
    private String markId;

    @ApiModelProperty(value = "有效否（fasle:无效；true:有效）", name = "serverStatus",allowableValues="false,true")
    private Boolean serverStatus;

    @ApiModelProperty(value = "类型名称", name = "typeName")
    private String typeName;

    @ApiModelProperty(value = "排序", name = "sort")
    private Integer sort;

}