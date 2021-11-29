package com.szhengzhu.bean.ordering;

import java.io.Serializable;

import lombok.Data;

@Data
public class StoreEmployee implements Serializable {
    
    private static final long serialVersionUID = -725751417165937674L;

    private String storeId;

    private String employeeId;
}