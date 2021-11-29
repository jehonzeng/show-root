package com.szhengzhu.bean.excel;

import java.io.Serializable;

import com.szhengzhu.annotation.Excel;

import lombok.Data;

/**
 * 酱料瓶数对应的订单总数
 * 
 * @author Administrator
 * @date 2019年9月11日
 */
@Data
public class SauceVo implements Serializable {

    private static final long serialVersionUID = -7157708212939406518L;

    @Excel(name = "酱料瓶数", sort = 1)
    private String sauceName;

    @Excel(name = "订单数量", sort = 2)
    private Integer orderCount;
}
