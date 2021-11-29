package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDetail implements Serializable {

    private static final long serialVersionUID = 5675030060403604664L;

    private String markId;

    private String exchangeId;

    private Integer quantity;

    private Date createTime;

    private String employeeId;

    private String remark;
}
