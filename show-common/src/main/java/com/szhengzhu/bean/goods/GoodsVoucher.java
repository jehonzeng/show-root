package com.szhengzhu.bean.goods;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品券信息
 * 
 * @author Administrator
 * @date 2019年3月12日
 */
@Data
public class GoodsVoucher implements Serializable {

    private static final long serialVersionUID = 3457926459116219660L;
    
    private String markId;

    private String productId;
    
    private String specificationIds;
    
    private Integer productType;

    @NotBlank
    private String voucherName;

    private BigDecimal price;

    private Integer stock;

    private Integer currentStock;

    private Boolean serverStatus;

    private Integer sort;
    
    private String content;
    
    private String voucherNo;
    
    private BigDecimal basePrice;
    
}