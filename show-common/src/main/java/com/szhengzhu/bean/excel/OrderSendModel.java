package com.szhengzhu.bean.excel;

import java.io.Serializable;

import com.szhengzhu.annotation.Excel;

import lombok.Data;

@Data
public class OrderSendModel implements Serializable {

    private static final long serialVersionUID = 7082569100960489029L;

    @Excel(name = "菜品信息", sort = 1)
    private String goodsQuantity;

    @Excel(name = "联系人", sort = 2)
    private String deliveryInfo;
    
    @Excel(name = "备注", sort = 3)
    private String remark;
}
