package com.szhengzhu.bean.ordering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IndentDetail implements Serializable {

    private static final long serialVersionUID = -2149664439203984715L;

    private String markId;

    private String indentId;

    private String timeCode;

    private String userId;

    private Integer commodityType;

    private String commodityId;

    private String commodityName;

    private String priceId;

    private String specsItems;

    private Integer quantity;

    private BigDecimal costPrice;

    private Integer priceType;

    private BigDecimal salePrice;

    private BigDecimal memberDiscountPrice;

    private Integer integralPrice;

    private String discountId;

    private String creator;

    private Date createTime;

    private String modifier;

    private Date modifyTime;

    private Integer status;
}
