package com.szhengzhu.bean.member;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MatchTeam implements Serializable {

    private static final long serialVersionUID = 7797551669041082275L;

    private String markId;

    private String teamName;

    private String imgId;

    private Integer sort;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private Boolean status;

    private String imgPath;
}