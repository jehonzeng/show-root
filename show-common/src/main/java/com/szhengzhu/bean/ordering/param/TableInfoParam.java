package com.szhengzhu.bean.ordering.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 餐桌信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableInfoParam implements Serializable {
    private static final long serialVersionUID = 8009711830992619699L;
    /**
     * 餐桌类型名称
     */
    private String name;
    /**
     * 座位
     */
    private String seats;
    /**
     * 使用次数
     */
    private Integer useNum;
    /**
     * 使用人数
     */
    private Integer peopleNum;
    /**
     * 平均客单价
     */
    private BigDecimal avgPrice;
    /**
     * 平均用餐时长
     */
    private BigDecimal avgMinute;

    private Map<String,Object> map;
}
