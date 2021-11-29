package com.szhengzhu.bean.ordering.param;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class IncomeParam implements Serializable {

    private static final long serialVersionUID = -7819970959482595946L;
    
    private Date startDate;
    
    private Date endDate;
    
    private String storeId;
    
    private String employeeId;

}
