package com.szhengzhu.bean.ordering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketExpire implements Serializable {
    private static final long serialVersionUID = -8352167866221851630L;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 总金额
     */
    private BigDecimal total;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 券名称
     */
    private String name;
}
