package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class SpecialBase implements Serializable {

    private static final long serialVersionUID = 4743487717960283820L;

    private String theme;

    private Integer promotionMode;

    private BigDecimal limitPrice;

    private BigDecimal price;

    private BigDecimal discount;
    
    private Date startTime;

    private Date endTime;
    
    private String goodsId;
}
