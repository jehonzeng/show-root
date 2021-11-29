package com.szhengzhu.bean.wechat.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoucherModel implements Serializable {
    
    private static final long serialVersionUID = 1846532815224468351L;

    private String voucherId;
    
    private String voucherName;
    
    private String specs;
    
    private Integer quantity = 1;
    
    private Integer status = 0; // 0: 初始状态（0是过渡状态，只在后台使用）,  1:使用   -1：不可使用
}
