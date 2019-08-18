package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class VoucherBase implements Serializable {

    private static final long serialVersionUID = 9081800694058422119L;

    private String voucherId;
    
    private String voucherName;
    
    private String productType;
    
    private String productId;
    
    private String specificationIds;
    
    private String specs;
}
