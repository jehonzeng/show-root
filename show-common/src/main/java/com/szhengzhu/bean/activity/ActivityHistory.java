package com.szhengzhu.bean.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动记录表
 * 
 * @author Administrator
 * @date 2019年9月25日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActivityHistory implements Serializable{
   
    private static final long serialVersionUID = -7651271931892642771L;

    private String markId;

    private String activityId;

    private String userId;

    private Date addTime;

    private Date deliveryTime;

    private Integer serverStatus;

    private String actGiftId;
    
    private Integer type;
    
    private String activityName;

}