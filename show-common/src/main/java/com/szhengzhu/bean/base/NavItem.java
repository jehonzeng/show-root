package com.szhengzhu.bean.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 导航详情信息
 * 
 * @author Administrator
 * @date 2019年4月3日
 */
@Data
public class NavItem implements Serializable {

    private static final long serialVersionUID = 392483355215013760L;

    private String markId;

    private String navId;

    private String theme;
    
    private Integer serverType;

    private String linkUrl;

    private String imagePath;

    private Date startTime;

    private Date endTime;

    private Boolean serverStatus;

    private Integer showType;
    
    private String description;

    private Integer sort;
}