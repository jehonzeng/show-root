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
public class TicketExchange implements Serializable {
    private static final long serialVersionUID = -60268433641985650L;

    /*  主键id */
    private String markId;

    /*  用户id */
    private String userId;

    /*  t_integarl_exchange的主键 */
    private String exchangeId;

    /*  创建时间 */
    private Date createTime;

    /* 积分兑换信息 */
    private IntegralExchange integralExchange;
}
