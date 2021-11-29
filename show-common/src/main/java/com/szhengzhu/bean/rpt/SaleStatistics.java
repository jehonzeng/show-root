package com.szhengzhu.bean.rpt;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaleStatistics extends SaleInfo implements Serializable {

    private static final long serialVersionUID = -515620971943715785L;

    private String productId;
    
    private String productName;
    
    private Integer count;
}
