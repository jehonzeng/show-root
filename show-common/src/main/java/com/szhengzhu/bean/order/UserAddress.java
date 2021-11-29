package com.szhengzhu.bean.order;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

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

    @NotBlank
    private String userName;

    @NotBlank
    private String phone;

    private Double longitude;

    private Double latitude;

    @NotBlank
    private String area;

    @NotBlank
    private String city;

    @NotBlank
    private String province;

    @NotBlank
    private String userAddress;

    private Integer addressType;

    private String userId;

    private Date createTime;

    private Boolean defaultOr;

    private Boolean serverStatus;
    
    private Boolean sendToday;
    
    private String displayValue;
}