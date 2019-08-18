package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 采购信息记录表
 * 
 * @author Administrator
 * @date 2019年5月7日
 */
@Data
public class PurchaseHistory implements Serializable {

    private static final long serialVersionUID = -7995812803302495809L;

    private String markId;

    private String foodId;

    private BigDecimal purchaseVolume;

    private Date buyTime;

    private String userId;
    
    private String purchaser;//冗余
    
}