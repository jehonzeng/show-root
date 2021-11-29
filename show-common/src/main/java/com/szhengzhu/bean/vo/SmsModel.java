package com.szhengzhu.bean.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class SmsModel implements Serializable{
    
    private static final long serialVersionUID = -828690720999678654L;

    private String templateId;
    
    private String phone;
    
    private String content;
}
