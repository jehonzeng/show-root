package com.szhengzhu.bean.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 今日采购商品信息，作为采购商品统计
 * 
 * @author Administrator
 * @date 2019年10月14日
 */
@Data
public class TodayProductVo implements Serializable {
  
    private static final long serialVersionUID = 8807463904898694725L;
    
    private String productId;
    
    private String productName;
    
    private Integer productType;
    
    private Integer quantity;

}
