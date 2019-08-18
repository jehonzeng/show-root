package com.szhengzhu.bean.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 通用下拉选择框列表
 * 
 * @author Administrator
 * @date 2019年3月1日
 */
@Data
public class Combobox implements Serializable{

    private static final long serialVersionUID = -6539209832504277341L;
    
    private String code;
    
    private String name;
    
}
