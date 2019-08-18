package com.szhengzhu.bean.goods;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 商品规格属性
 * 
 * @author Administrator
 * @date 2019年2月28日
 */
@Data
@ApiModel
public class SpecificationInfo implements Serializable {

    private static final long serialVersionUID = -4230959177044182201L;

    private String markId;

    private String attrName;

    private String attrValue;

    private Boolean serverStatus;

    private Integer sort;
    
    private String typeId;
    
    private Boolean defaultOr;
    
}