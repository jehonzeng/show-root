package com.szhengzhu.bean.rpt;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class SaleInfo implements Serializable {

    private static final long serialVersionUID = -1526226415099053120L;

    private BigDecimal baseAmount;

    private BigDecimal amount;

    private BigDecimal profit;
}
