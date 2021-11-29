package com.szhengzhu.bean.member;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class RechargeRefund implements Serializable {

    private static final long serialVersionUID = 7859792590911041587L;

    private String markId;

    private String detailId;

    private String refundNo;

    private Integer refundStatus;

    private BigDecimal totalFee;

    private Date createTime;

    private String refundInfo;
}