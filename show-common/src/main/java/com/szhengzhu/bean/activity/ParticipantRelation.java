package com.szhengzhu.bean.activity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参与者关系表
 * 
 * @author Administrator
 * @date 2019年9月25日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParticipantRelation implements Serializable{

    private static final long serialVersionUID = 1635757161907512921L;

    private String markId;

    private String fatherId;

    private String sonId;

    private String activityId;

    private Date addTime;

    private Date refreshTime;

    private Integer point;

    private Integer serverStatus;

    private Integer serverType;

    private String activityName;
}