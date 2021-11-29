package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

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

    @NotBlank
    private String theme;

    private Boolean serverStatus;

    private BigDecimal basePrice;

    private BigDecimal salePrice;

    private Integer stockSize;

    private String description;

    private String imagePath;

    private Integer sort;

    private Date createTime;
}
