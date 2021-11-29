package com.szhengzhu.bean.ordering.param;

import java.io.Serializable;

import lombok.Data;

@Data
public class StoreParam implements Serializable {

    private static final long serialVersionUID = -992963460160309505L;
    
    private String name;
    
    private String phone;
    
    private String address;
    
    private Integer status;
}
