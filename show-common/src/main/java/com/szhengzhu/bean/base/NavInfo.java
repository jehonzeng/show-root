package com.szhengzhu.bean.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 首页导航信息
 * 
 * @author Administrator
 * @date 2019年4月3日
 */
@Data
public class NavInfo implements Serializable {

    private static final long serialVersionUID = 2133501713907045762L;

    private String markId;

    private String navCode;

    private Integer serverType;

    private Date addTime;

    private String remark;
    
    private Boolean serverStatus;

}