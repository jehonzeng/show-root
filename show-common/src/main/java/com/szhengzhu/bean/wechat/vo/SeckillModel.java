package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SeckillModel implements Serializable {

    private static final long serialVersionUID = -308621614028539218L;

    private String markId;
    
    private Date deliveryDate;

    @NotBlank
    private String addressId;
    
    private String remark;
    
    private String orderSource;
    
    private String userId;
}
