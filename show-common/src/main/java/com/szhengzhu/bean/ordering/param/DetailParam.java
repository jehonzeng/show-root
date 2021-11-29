package com.szhengzhu.bean.ordering.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class DetailParam implements Serializable {

    private static final long serialVersionUID = -5784354304865676772L;
    
    private String markId;

    @NotBlank
    private String commodityId;

    @NotBlank
    private String priceId;

    private String specsItems;

    @NotNull
    private Integer quantity;

    /** 商品优惠 */
    private String discountId;
}
