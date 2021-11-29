package com.szhengzhu.bean.ordering.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class PrinterCommodityVo implements Serializable{
    /** */
    private static final long serialVersionUID = 8890200573862265794L;
    
    private String commodityId;
    
    private Integer sort;

}
