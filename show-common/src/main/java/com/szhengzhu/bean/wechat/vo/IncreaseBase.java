package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class IncreaseBase implements Serializable {
    
    private static final long serialVersionUID = -1387363111812238982L;

    private String increaseId;
    
    private String theme;
    
    private BigDecimal limitPrice;
    
    private BigDecimal price;
    
    private String imagePath;
    
    private List<GoodsDefault> goodsList;
}
