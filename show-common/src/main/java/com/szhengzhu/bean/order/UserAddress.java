package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 用户地址信息
 * 
 * @author Administrator
 * @date 2019年2月19日
 */
@Data
public class UserAddress implements Serializable {
    
    private static final long serialVersionUID = 3494085234687710832L;

    private String markId;

    private String userName;

    private String phone;

    private Double longitude;

    private Double latitude;

    private String area;

    private String city;

    private String province;

    private String userAddress;

    private Integer addressType;

    private String userId;

    private Date createTime;

    private Boolean defaultOr;

    private Boolean serverStatus;
    
    private Boolean sendToday;
    
    private String displayValue;
}