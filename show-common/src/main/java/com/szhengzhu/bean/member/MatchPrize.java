package com.szhengzhu.bean.member;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MatchPrize implements Serializable {

    private static final long serialVersionUID = 5830930897125761987L;

    private String markId;

    private String stageId;

    private String prizeName;

    private String imgId;

    private String templateId;

    private Integer sort;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private Boolean status;
}