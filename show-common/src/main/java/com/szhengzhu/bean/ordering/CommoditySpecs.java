package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommoditySpecs implements Serializable {
    
    private static final long serialVersionUID = -1143887657053405166L;

    @NotBlank
    private String commodityId;

    @NotBlank
    private String specsId;
    
    private Integer minValue;

    private Integer maxValue;

    private Date createTime;

    private Date modifyTime;
}