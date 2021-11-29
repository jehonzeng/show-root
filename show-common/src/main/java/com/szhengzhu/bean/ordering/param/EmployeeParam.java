package com.szhengzhu.bean.ordering.param;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployeeParam implements Serializable {

    private static final long serialVersionUID = 8005719780301697848L;

    private String name;
    
    private String code;
    
    private String phone;
    
    private String storeId;
}
