package com.szhengzhu.bean.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 食材统计信息
 * 
 * @author Administrator
 * @date 2019年10月14日
 */
@Data
public class FoodCount implements Serializable{

    private static final long serialVersionUID = 946687518393780804L;
    
    private String foodId;

    private BigDecimal totalCount;
    
}
