package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BackHistory implements Serializable {
    
    private static final long serialVersionUID = 7535020217573887664L;

    private String markId;

    private String orderNo;

    private Date addTime;

    private Integer payStatus;

    private String cid;
}