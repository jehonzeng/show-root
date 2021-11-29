package com.szhengzhu.bean.rpt;

import java.io.Serializable;

import lombok.Data;

/**
 * 首页展示统计数据
 * 
 * @author Administrator
 * @date 2019年10月18日
 */
@Data
public class IndexDisplay implements Serializable{
    
    private static final long serialVersionUID = -606272782151499259L;
    
    private String name;
    
    private Integer quantity;

}
