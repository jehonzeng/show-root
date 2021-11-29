package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Store implements Serializable {

    private static final long serialVersionUID = 7449322752174597551L;

    private String markId;

    @NotBlank
    private String name;

    @NotBlank
    private String province;

    @NotBlank
    private String city;

    @NotBlank
    private String area;

    @NotBlank
    private String address;

    private Date openBusiness;

    private Date closeBusiness;

    private String license;

    private String businessCircle;

    private BigDecimal perCapita;
    
    private BigDecimal perFee;

    private String phone;

    private String description;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
    
    private String fullAddress;
}