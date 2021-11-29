package com.szhengzhu.bean.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 促销商品中间表
 * 
 * @author Administrator
 * @date 2019年4月26日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialItem implements Serializable {

    private static final long serialVersionUID = -3295678627982565106L;

    private String markId;

    private String specialId;

    private String goodsId;

}