package com.szhengzhu.bean.ordering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offset {
    /**
     * 百分比
     */
    private BigDecimal percent;
    /**
     * 跨度（范围）
     */
    private int span;
    /**
     * 区间开始
     */
    private int start;
    /**
     * 区间结束
     */
    private int end;
    /**
     * 获取百分比
     */
    public float getPercent() {
        return percent.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
