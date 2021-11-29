package com.szhengzhu.bean.ordering;

import java.io.Serializable;

import lombok.Data;

@Data
public class TagCommodity implements Serializable {
    
    private static final long serialVersionUID = 552756364404612778L;

    private String commodityId;

    private String tagId;
}