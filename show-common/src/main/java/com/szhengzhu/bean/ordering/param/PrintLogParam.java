package com.szhengzhu.bean.ordering.param;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class PrintLogParam implements Serializable {
    
    private static final long serialVersionUID = 9191154724930394936L;

    private Integer statusCode;
    
    private Date businessTime;
    
    private String storeId;
}
