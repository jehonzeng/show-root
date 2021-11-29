package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class TableArea implements Serializable {

    private static final long serialVersionUID = -8619931601828230537L;

    private String markId;
    
    private String code;

    @NotBlank
    private String name;

    private String storeId;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
}