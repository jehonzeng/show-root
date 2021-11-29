package com.szhengzhu.bean.order;

import java.io.Serializable;

import lombok.Data;

@Data
public class TrackInfo implements Serializable {
    
    private static final long serialVersionUID = 2515284812354909956L;

    private String markId;

    private String com;

    private String trackNo;

    private Integer state;

    private String info;
}