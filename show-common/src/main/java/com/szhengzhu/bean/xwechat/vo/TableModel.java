package com.szhengzhu.bean.xwechat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TableModel implements Serializable {

    private static final long serialVersionUID = -4086924609674769725L;

    private String tableId;
    
    private String tableCode;
    
    private Integer seats;
    
    private Integer manNum;
    
    private String tableStatus;
    
    private String storeId;
    
    private String storeName;
    
    private Boolean storeStatus;
    
    private String indentId; 
    
    private String userId;
}
