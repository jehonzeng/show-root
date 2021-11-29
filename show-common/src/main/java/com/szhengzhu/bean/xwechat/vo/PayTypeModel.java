package com.szhengzhu.bean.xwechat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayTypeModel implements Serializable {

    private static final long serialVersionUID = 3667313160955569168L;

    /* 类型名称 */
    private String name;

    private String key;

    /* (支付/优惠)金额 */
    private BigDecimal value;
}
