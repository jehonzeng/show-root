package com.szhengzhu.bean.ordering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IndentInfo implements Serializable {
    private static final long serialVersionUID = -1480773110394497143L;
    /**
     * 订单id
     */
    private String markId;
    /**
     * 会员id
     */
    private String memberId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 商店id
     */
    private String storeId;
    /**
     * 商店名称
     */
    private String storeName;
    /**
     * 下单时间
     */
    private Date time;
    /**
     * 订单金额
     */
    private BigDecimal total;
    /**
     * 订单状态
     */
    private String indentStatus;
    /**
     * 用户id列表
     */
    private List<String> userList;
}
