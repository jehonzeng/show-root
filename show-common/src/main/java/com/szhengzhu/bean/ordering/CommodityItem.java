package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommodityItem implements Serializable {
    
    private static final long serialVersionUID = -3455608026336416812L;

    @NotBlank
    private String commodityId;

    @NotBlank
    private String specsId;

    @NotBlank
    private String itemId;
    
    private String name;

    private BigDecimal markupPrice;

    private Integer sort;
    
    private Boolean checked;
}