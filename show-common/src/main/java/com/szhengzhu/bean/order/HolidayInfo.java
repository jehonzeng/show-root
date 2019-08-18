package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class HolidayInfo implements Serializable {
    
    private static final long serialVersionUID = -2424200848947176379L;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    private Date holiday;
    
    public HolidayInfo() {}
    
    public HolidayInfo(Date holiday) {
        this.holiday = holiday;
    }
}