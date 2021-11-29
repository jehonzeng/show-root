package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Tag implements Serializable {
    
    private static final long serialVersionUID = 412908182737215496L;

    private String markId;

    @NotBlank
    private String name;

    private String imgId;
    
    private String imgPath;

    private Integer sort;
    
    private String storeId;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
}