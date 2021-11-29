package com.szhengzhu.bean.ordering.param;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜品类型销量
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodTypeSaleParam implements Serializable{

	private static final long serialVersionUID = 480945136470989486L;

	/**
	 * 商品类型
	 */
	private String goodsType;

	/**
	 * 商品销售数量
	 */
	private int saleNum;

	/**
	 * 商品金额
	 */
	private BigDecimal salePrice;

	/**
	 * 商品销售金额
	 */
	private BigDecimal saleTotalPrice;
}
