package com.szhengzhu.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = -2958052964957807592L;
    
    private String productId;
    
    private String specIds;
    
    private String storehouseId;
    
    private Integer quantity;
    
    private String activityId;

}
