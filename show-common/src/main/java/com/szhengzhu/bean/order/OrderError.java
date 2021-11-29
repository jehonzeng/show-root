package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class OrderError implements Serializable {

    private static final long serialVersionUID = 6799529550538638740L;

    private String markId;

    private String orderNo;

    private String errorInfo;

    private Integer errorType;

    private Date addTime;

    private String userMark;

    private Integer orderType;
}