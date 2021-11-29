package com.szhengzhu.bean.rpt;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SaleParam implements Serializable {

    private static final long serialVersionUID = 348973679582523149L;

    private String partner;
    
    private Date startDate;
    
    private Date endDate;
}
