package com.szhengzhu.bean.member;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MatchResult implements Serializable {

    private static final long serialVersionUID = 9073457531404089871L;

    private String markId;

    private String stageId;

    private String teamId;

    private Date lastTime;

    private Integer teamStatus;
}