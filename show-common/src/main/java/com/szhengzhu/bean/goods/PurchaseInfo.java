package com.szhengzhu.bean.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购信息表
 * 
 * @author Administrator
 * @date 2019年5月7日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseInfo implements Serializable{
 
    private static final long serialVersionUID = -4300771361655455682L;

    private String markId;

    @NotBlank
    private String foodId;

    private BigDecimal purchaseTotal;

    private BigDecimal buyTotal;

    private Date buyTime;

    private Integer serverStatus;

    private Date reflashTime;

    private String userId;
    
    private String purchaser;//冗余

}