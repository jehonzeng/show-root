package com.szhengzhu.bean.ordering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoucherCode implements Serializable {

    private static final long serialVersionUID = -4843862095940573157L;

    private String code;

    private Date useTime;

    private String voucherId;

    private Integer balance;

    private Integer status;
}