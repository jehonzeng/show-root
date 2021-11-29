package com.szhengzhu.bean.ordering.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 餐桌使用信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableUseParam implements Serializable {
    private static final long serialVersionUID = 6597069016249511495L;
    /**
     * 时间
     */
    private String times;
    /**
     * 餐桌类型 / 餐桌区域
     */
    private String name;
    /**
     * 座位数
     */
    private String seats;
    /**
     * 餐桌ID
     */
    private String markId;
    /**
     * 消费金额
     */
    private BigDecimal total;
    /**
     * 使用数量
     */
    private Integer useNum;
}
