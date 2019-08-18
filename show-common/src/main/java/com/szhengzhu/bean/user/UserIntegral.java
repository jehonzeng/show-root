package com.szhengzhu.bean.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserIntegral implements Serializable {

    private static final long serialVersionUID = 3489593588051423215L;

    private String markId;

    private String userId;

    private Integer integralLimit;

    private Date createTime;

    private String remark;
}