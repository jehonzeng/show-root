package com.szhengzhu.bean.ordering;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class MarketInfo implements Serializable {

    private static final long serialVersionUID = -9214700832910075832L;

    private String markId;

    @NotBlank
    private String name;

    @NotNull
    private Integer buyQuantity;

    @NotNull
    private Integer discountQuantity;

    private Integer discountType;

    private BigDecimal amount;

    private String storeId;

    private Date startTime;

    private Date stopTime;

    private String creator;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
    
    private Integer quantity;
    
    private List<String> commIds;
    
    private List<String> discountCommIds;
}