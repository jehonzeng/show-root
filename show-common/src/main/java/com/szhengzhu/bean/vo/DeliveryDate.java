package com.szhengzhu.bean.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DeliveryDate implements Serializable {

    private static final long serialVersionUID = 8665489547251082716L;

    private String tag;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    private Date date;
    
    private String day;
    
    private Boolean flag = false;
}
