package com.szhengzhu.bean.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExportTemplateVo implements Serializable{
  
    private static final long serialVersionUID = 2034901078600541060L;
    
    private String userName;
    
    private String userPhone;
    
    private String userArea;
    
    private String userAddress;
    
    private String productName;
    
    private Integer quantity;

}
