package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeambuyModel implements Serializable {

    private static final long serialVersionUID = -7022455487658234181L;

    private String markId;
    
    private String groupId;
    
    private Date deliveryDate;

    @NotBlank
    private String addressId;
    
    private String remark;
    
    private String orderSource;
    
    private String userId;
}
