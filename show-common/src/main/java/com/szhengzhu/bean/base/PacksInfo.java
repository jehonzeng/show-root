package com.szhengzhu.bean.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class PacksInfo implements Serializable{
    
    private static final long serialVersionUID = 8205474585900347159L;

    private String markId;

    private String theme;

    private Date startTime;

    private Date endTime;

    private Boolean serverStatus;
    
}