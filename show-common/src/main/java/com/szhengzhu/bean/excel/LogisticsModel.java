package com.szhengzhu.bean.excel;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 物流信息导入模板
 * 
 * @author Administrator
 * @date 2019年9月19日
 */

@Getter
@Setter
public class LogisticsModel {
    
    private String orderNo;

    private String userName;

    private String phone;

    private String userAddress;
    
    private String company;

    private String trackNo;

    private String feedback;
    
    private String companyNo; //公司编号

}
