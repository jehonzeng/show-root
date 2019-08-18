package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 套餐评论信息表
 * 
 * @author Administrator
 * @date 2019年4月22日
 */
@Data
public class MealJudge implements Serializable {

    private static final long serialVersionUID = 3468867670783202879L;

    private String markId;

    private String orderId;

    private String mealId;
    
    private String userId;

    private Date addTime;

    private Boolean serverStatus;

    private String description;

    private String commentator;

    private Integer star;

    private Integer sort;

}