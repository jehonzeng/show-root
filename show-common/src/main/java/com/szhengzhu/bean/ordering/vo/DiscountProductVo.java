package com.szhengzhu.bean.ordering.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountProductVo implements Serializable {
    private static final long serialVersionUID = 6125549882010509690L;

    /* 订单详情id */
    private String detailId;

    /* 商品id */
    private String commodityId;

    /* 售价 */
    private BigDecimal salePrice;

    /* 会员价 */
    private BigDecimal memberDiscountPrice;

    /* 是否有会员折扣 0：无  1：有*/
    private Integer inDiscount;

    /* 是否使用券 0：无  1：有*/
    private Integer useTicket;
}
