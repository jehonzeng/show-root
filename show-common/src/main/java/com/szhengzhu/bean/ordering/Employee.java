package com.szhengzhu.bean.ordering;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = -7072053504036678697L;

    private String markId;

    private String code;

    @NotBlank
    private String name;

    private Integer gender;

    @NotBlank
    private String phone;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
    
    private String storeId;
    
    private String identityId;
    
    private String identityName;
}