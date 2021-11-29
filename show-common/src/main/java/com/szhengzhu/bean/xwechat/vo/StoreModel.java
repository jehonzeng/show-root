package com.szhengzhu.bean.xwechat.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class StoreModel implements Serializable {

    private static final long serialVersionUID = 1676413205143938921L;

    private String storeId;
    
    private String storeName;
    
    private String fullAddress;
    
    private Date openBusiness;

    private Date closeBusiness;
    
    private String phone;
    
    private String description;
}
