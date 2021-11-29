package com.szhengzhu.bean.activity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 商城活动信息表
 * 
 * @author Administrator
 * @date 2019年9月25日
 */
@Data
public class ActivityInfo implements Serializable{
  
    private static final long serialVersionUID = 673080244069044335L;

    private String markId;

    @NotBlank
    private String theme;

    /** 开始时间 */
    @NotNull
    private Date startTime;

    /** 截止时间 */
    private Date stopTime;

    /** 兑换时间 */
    private Date checkTime;

    /** 结束时间 */
    private Date endTime;

    private Boolean serverStatus;

    private String imagePath;

    private String awardUrl;
}