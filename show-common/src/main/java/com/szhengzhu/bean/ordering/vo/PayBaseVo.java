package com.szhengzhu.bean.ordering.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class PayBaseVo implements Serializable{

    private static final long serialVersionUID = 2523834835798940826L;

    private String payId;
    
    private String code;
    
    private String payName;
    
    private String btnColor;
}
