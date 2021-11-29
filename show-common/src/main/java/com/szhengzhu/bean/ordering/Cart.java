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
public class Cart implements Serializable{

    private static final long serialVersionUID = 9165444489492788314L;

    private String markId;

    private String tableId;
    
    private String storeId;
    
    private String userId;

    private Integer commodityType;

    private String commodityId;

    private String commodityName;
    
    private String priceId;

    private String specsItems;

    private BigDecimal costPrice;

    private Integer priceType;

    private BigDecimal salePrice;

    private Integer integralPrice;

    private Integer quantity;

    private Date createTime;
    
    private Date modifyTime;
}