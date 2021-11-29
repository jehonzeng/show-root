package com.szhengzhu.bean.ordering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountCompare implements Serializable {
    private static final long serialVersionUID = 3358668428523539317L;

    /* 时间(年月) */
    private String time;

    /* 实际总收入 */
    private BigDecimal amount;

    /* 总收入 */
    private BigDecimal payAmount;

    /* 总人数 */
    private Integer quantity;

    /* 客单价 */
    private BigDecimal avgPrice;
}
