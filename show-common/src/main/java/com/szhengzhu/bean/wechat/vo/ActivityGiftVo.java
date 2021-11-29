package com.szhengzhu.bean.wechat.vo;

import java.math.BigDecimal;

import com.szhengzhu.bean.activity.ActivityGift;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityGiftVo extends ActivityGift{

    private static final long serialVersionUID = 7082730728454307473L;

    private String productId;
    
    private Integer giftType;
    
    private  Boolean giftStatus;
    
    private BigDecimal price;
    
    private String specificationIds;
    
    private String imagePath;
    
    private String description;
    
    private String specList;
    
}
