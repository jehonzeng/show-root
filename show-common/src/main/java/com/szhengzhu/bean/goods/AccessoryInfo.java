package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 附属品信息
 * 
 * @author Administrator
 * @date 2019年4月26日
 */
@Data
public class AccessoryInfo implements Serializable {

    private static final long serialVersionUID = -7875957678735263417L;

    private String markId;

    private String theme;

    private Boolean serverStatus;

    private BigDecimal basePrice;

    private BigDecimal salePrice;

    private Integer stockSize;

    private String description;
    
    private String imagePath;

    private Integer sort;

}