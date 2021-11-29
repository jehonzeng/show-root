package com.szhengzhu.bean.ordering;

import java.io.Serializable;

import lombok.Data;

@Data
public class PrinterItem implements Serializable{
    
    private static final long serialVersionUID = 124081317538996004L;

    private String printerId;

    private String commodityId;
    
    private  Integer sort;

}