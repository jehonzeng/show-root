package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品分类标签信息
 * 
 * @author Administrator
 * @date 2019年3月27日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LabelGoods implements Serializable {

    private static final long serialVersionUID = -7065986279439465411L;

    private String markId;

    private String goodsId;

    private String labelId;

    private Boolean serverStatus;
    
    private Integer goodsType;

    private Integer sort;
}