package com.szhengzhu.bean.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 商品栏目基础信息
 * 
 * @author Administrator
 * @date 2019年3月27日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ColumnGoods implements Serializable {

    private static final long serialVersionUID = -6989067496365235365L;

    private String markId;

    private String columnId;

    private String goodsId;
    
    private Integer goodsType;

    private Boolean serverStatus;

    private Integer sort;
}