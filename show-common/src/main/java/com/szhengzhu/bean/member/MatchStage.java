package com.szhengzhu.bean.member;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MatchStage implements Serializable {

    private static final long serialVersionUID = 2696280344798725650L;

    private String markId;

    private String matchId;

    private String stageName;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Date modifyTime;

    private Boolean status;

    private String matchName;
}