package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Identity implements Serializable {

    private static final long serialVersionUID = 8642034699215121892L;

    private String markId;

    @NotBlank
    private String name;

    private String code;

    private String description;

    private String authority;

    private String creator;
    
    private String storeId;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
}