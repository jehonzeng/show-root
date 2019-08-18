package com.szhengzhu.bean.base;

import java.io.Serializable;

import lombok.Data;

/**
 * 线下代金券门店区域表
 * 
 * @author Administrator
 * @date 2019年6月12日
 */
@Data
public class RegionInfo implements Serializable{

    private static final long serialVersionUID = 5184813677110754072L;

    private String markId;

    private String storeName;
    
    private Boolean serverStatus;

    private String address;

    private String scavenger;
    
}