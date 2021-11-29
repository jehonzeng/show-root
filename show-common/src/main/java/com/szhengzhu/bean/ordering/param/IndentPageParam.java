package com.szhengzhu.bean.ordering.param;

import java.io.Serializable;

import lombok.Data;

@Data
public class IndentPageParam implements Serializable {

    private static final long serialVersionUID = -8256839169102607683L;

    private String indentTime;
    
    /** 桌台或订单号*/
    private String code;
    
    private String indentStatus;
    
    private String storeId;
}
