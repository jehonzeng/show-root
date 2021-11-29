package com.szhengzhu.bean.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodsItem implements Serializable{
  
    private static final long serialVersionUID = 580560748681408897L;

    private String markId;

    @NotBlank
    private String foodId;

    @NotBlank
    private String goodsId;
    
    private String specificationIds;

    private BigDecimal useSize;
    
    private Boolean serverStatus;

}