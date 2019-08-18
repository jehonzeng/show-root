package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class GoodsJudge implements Serializable {

    private static final long serialVersionUID = 3893335743552812872L;

    private String markId;

    private String goodsId;
    
    private String specificationIds;

    private String orderId;
    
    private String userId;

    private Boolean serverStatus;

    private String description;

    private String commentator;

    private Date addTime;

    private Integer star;
    
    private Integer sort;
     
}