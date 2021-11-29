package com.szhengzhu.bean.xwechat.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CartParam implements Serializable {

    private static final long serialVersionUID = -436367154264988505L;

    private String cartId;

    private String tableId;

    @NotBlank
    private String commodityId;
    
    private String priceId;

    private String specsItems;
    
    private Integer quantity;

    private String userId;
}
