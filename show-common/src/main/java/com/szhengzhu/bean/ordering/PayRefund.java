package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayRefund implements Serializable {

    private static final long serialVersionUID = 1745789486821912438L;

    private String markId;

    private String payId;

    private String refundNo;

    private Integer refundStatus;

    private Integer backStatus;

    private BigDecimal totalFee;

    private Date createTime;

    private Date modifyTime;

    private String refundInfo;

    private String backInfo;
}