package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;

/**
 * 促销商品中间表
 * 
 * @author Administrator
 * @date 2019年4月26日
 */
@Data
public class SpecialItem implements Serializable {

    private static final long serialVersionUID = -3295678627982565106L;

    private String markId;

    private String specialId;

    private String goodsId;

}