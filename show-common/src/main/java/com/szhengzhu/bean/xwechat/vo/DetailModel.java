package com.szhengzhu.bean.xwechat.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class DetailModel implements Serializable {

    private static final long serialVersionUID = 8319993093501810751L;

    private String markId;

    private Integer commodityType;

    private String commodityId;

    private String commodityName;

    private String priceId;

    private String unit;

    private String specsItems;

    private String specsNames;

    private BigDecimal costPrice;

    private Integer priceType;

    private BigDecimal salePrice;

    private BigDecimal memberDiscountPrice;

    private BigDecimal integralPrice;

    private String discountId;

    private Integer quantity;

    private Date createTime;

    private Integer status;

    private String discountName;

    private String userId;

    private String nickName;

    private String headerImg;
}
