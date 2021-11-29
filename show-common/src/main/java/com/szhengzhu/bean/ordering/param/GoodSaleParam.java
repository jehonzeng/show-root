package com.szhengzhu.bean.ordering.param;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜品销售数量
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodSaleParam implements Serializable {

    private static final long serialVersionUID = 5267578784313747499L;

    /**
     * 菜品名
     */
    private String goodsName;

    /**
     * 今天菜品数量
     */
    private int csaleNum;

    /**
     * 今天菜品数量
     */
    private int lsaleNum;
}
