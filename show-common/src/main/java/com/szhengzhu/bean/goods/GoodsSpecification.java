package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @author Administrator
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoodsSpecification implements Serializable {
    
    private static final long serialVersionUID = 3144188292649419371L;

    private String markId;

    @NotBlank
    private String goodsId;
    
    private String specificationIds;

    private Boolean serverStatus;

    private BigDecimal basePrice;

    private BigDecimal salePrice;
    
    private String specs;
    
    private String goodsNo;
}