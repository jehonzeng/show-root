package com.szhengzhu.bean.ordering;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 6351210170723609731L;

    private String markId;

    @NotBlank
    private String name;

    private Integer sort;

    private String imgId;
    
    private String storeId;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
}