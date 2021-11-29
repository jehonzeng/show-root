package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchPay implements Serializable {

    private static final long serialVersionUID = 7688492347382003697L;

    private String markId;

    private String matchId;

    private String userId;

    private Integer quantity;

    private BigDecimal amount;

    private Date createTime;

    private Date modifyTime;

    private Boolean status;
}