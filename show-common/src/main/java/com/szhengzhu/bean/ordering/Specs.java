package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 规格信息
 * @author Administrator
 *
 */
@Data
public class Specs implements Serializable {
    
    private static final long serialVersionUID = 5212621982899871040L;

    private String markId;

    @NotBlank
    private String name;
    
    private String storeId;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
    
    private Integer sort;
}