package com.szhengzhu.bean.ordering.print;

import cn.hutool.core.util.NumberUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrintIndent {

    /**
     * 门店
     */
    private String storeName;

    /**
     * 订单号
     */
    private String indentNo;

    /**
     * 桌台
     */
    private String tableCode;

    /**
     * 用餐人数
     */
    private Integer manNum;

    /**
     * 日期：下单时间、结账时间
     */
    private Date date;

    /**
     * 班别、餐段
     */
    private String schedule;

    /**
     * 点菜、开单、收银(人员)
     */
    private String operator;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 应收金额
     */
    private BigDecimal receivedAmount;

    /**
     * 实付金额
     */
    private BigDecimal paidAmount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 打印次数
     */
    private Integer printCount;

    private List<PrintDetail> detailList;

    /**
     * 支付流水
     */
    private List<PrintPay> payList;

    public BigDecimal getDiscount() {
        this.discount = this.totalAmount.subtract(this.receivedAmount);
        return this.discount;
    }
}
