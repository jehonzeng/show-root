package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Data
public class Pay implements Serializable {

    private static final long serialVersionUID = -1367161192258890520L;

    private String markId;

    @NotBlank
    private String name;

    private String typeId;

    private Integer discountType;

    private BigDecimal receiveAmount;

    private BigDecimal discountLimit;

    private BigDecimal discountAmount;

    private String storeId;

    private Boolean received;

    private Boolean discountHave;

    private String btnColor;

    private Date createTime;

    private Date modifyTime;

    private Integer sort;

    private Integer status;

    private String typeName;
}
