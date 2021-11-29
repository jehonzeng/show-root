package com.szhengzhu.bean.excel;

import java.io.Serializable;
import java.util.List;

import com.szhengzhu.annotation.Excel;

import lombok.Data;


/**
 * 
 * 导出的配送信息模板
 * 
 * @author Administrator
 * @date 2019年9月11日
 */
@Data
public class DeliveryModel implements Serializable{

    private static final long serialVersionUID = -9182456325443091279L;

    @Excel(name = "订单号", sort = 1)
    private String orderNo;

    @Excel(name = "联系人", sort = 2)
    private String userName;

    @Excel(name = "联系方式", sort = 3)
    private String phone;

    @Excel(name = "联系地址", sort = 4)
    private String userAddress;
    
    @Excel(name = "物流名称",select = true, sort = 5)
    private List<String> company;

    @Excel(name = "运单号", sort = 6)
    private String trackNo;

    @Excel(name = "问题",sort = 7)
    private String feedback;
}
