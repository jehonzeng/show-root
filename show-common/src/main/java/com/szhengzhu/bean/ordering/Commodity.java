package com.szhengzhu.bean.ordering;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
public class Commodity implements Serializable {
    
    private static final long serialVersionUID = 9027497916613078715L;

    private String markId;
    
    private String code;

    @NotBlank
    private String name;

    private String storeId;

    private Integer type;

    private String introduction;
    
    private Integer quantity;

    private Boolean inDiscount;

    private String creator;

    private Date createTime;

    private String modifier;

    private Date modifyTime;

    private Integer status;
    
    private String[] imgList;
}