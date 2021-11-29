package com.szhengzhu.bean.goods;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品促销活动表
 * 
 * @author Administrator
 * @date 2019年4月26日
 */
@Data
public class SpecialInfo implements Serializable {

    private static final long serialVersionUID = -3991850202220112518L;

    private String markId;

    private String theme;

    private Integer promotionType;

    private Integer promotionMode;

    private Boolean serverStatus;

    private BigDecimal limitPrice;

    private BigDecimal price;

    private BigDecimal discount;

    private Date startTime;

    private Date endTime;

    private Integer userType;

    private String imagePath;
    
    private Boolean shareType;

}