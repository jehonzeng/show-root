package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 采购信息记录表
 * 
 * @author Administrator
 * @date 2019年5月7日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
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