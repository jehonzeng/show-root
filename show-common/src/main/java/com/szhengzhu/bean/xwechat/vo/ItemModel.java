package com.szhengzhu.bean.xwechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ItemModel implements Serializable {

    private static final long serialVersionUID = 590633400170641256L;

    private String itemId;
    
    private String itemName;
    
    private BigDecimal markupPrice;
}
