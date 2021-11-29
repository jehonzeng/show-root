package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.activity.ActivityInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityModel extends ActivityInfo {

    private static final long serialVersionUID = -6887196608812990305L;
    
    private Integer follow;

    private String initiatorLimit;

    private String limited;

    private String helperLimit;
    
    private String actLimiedDesc;
    
    private String initiatorLimitDesc;
    
    private String helperLimitDesc;
}
