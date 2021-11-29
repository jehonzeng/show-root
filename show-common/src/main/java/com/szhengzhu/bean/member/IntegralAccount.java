package com.szhengzhu.bean.member;

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
public class IntegralAccount implements Serializable {

    private static final long serialVersionUID = 7506095359512949686L;

    private String markId;

    private String userId;

    private String accountNo;

    private Integer totalIntegral;

    private Date createTime;

    private Date modifyTime;
}
