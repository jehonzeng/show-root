package com.szhengzhu.bean.member;

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
public class PayBack implements Serializable {

    private static final long serialVersionUID = -2476165328015837868L;

    private String markId;

    /* 1: 会员充值  2：竞赛支付*/
    private Integer type;

    private String payId;

    private String ruleId;

    private String backInfo;

    private Integer backType;

    private Date addTime;

    private String userId;

    private String code;
}