package com.szhengzhu.bean.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class OrderBatch implements Serializable{
    
    private static final long serialVersionUID = 462537066831170065L;
    
    private List<String> ids;
    
    private String orderStatus;
}
