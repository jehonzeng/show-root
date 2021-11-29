package com.szhengzhu.bean.ordering.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 菜品销量
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodSaleRankParam implements Serializable {

    private static final long serialVersionUID = -6408669073109800633L;
    /**
     * 菜品名称
     */
    private String goodsName;
    /**
     *菜品规格
     */
    private String goodsUnit;
    /**
     *菜品类型
     */
    private String goodsType;
    /**
     *菜品数量
     */
    private int num;
    /**
     *菜品价格
     */
    private BigDecimal price;
    /**
     *菜品总销量
     */
    private BigDecimal saleTotalPrice;
}
