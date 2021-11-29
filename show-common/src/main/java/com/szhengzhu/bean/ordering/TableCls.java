package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class TableCls implements Serializable {

    private static final long serialVersionUID = -2906188396388617630L;

    private String markId;

    @NotBlank
    private String name;

    private String storeId;
    
    private Integer seats;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
}