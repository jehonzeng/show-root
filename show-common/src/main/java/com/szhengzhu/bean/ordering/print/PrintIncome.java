package com.szhengzhu.bean.ordering.print;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PrintIncome {

    /** 总单数*/
    private Integer quantity;

    /** 营收总额 */
    private BigDecimal income;

    /** 实收总额*/
    private BigDecimal paidIncome;
    
    private Date startDate;
    
    private Date endDate;
    
    private List<PrintPay> payList;
}
