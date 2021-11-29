package com.szhengzhu.bean.vo;

import java.io.Serializable;
import java.util.List;

import com.szhengzhu.bean.order.OrderItem;

import lombok.Data;

/**
 * 普通订单打印模板
 * 
 * @author Administrator
 * @date 2019年9月5日
 */
@Data
public class OrderExportVo implements Serializable{

    private static final long serialVersionUID = 5657765941530832198L;
    
    private String userName;
    
    private String userPhone;
    
    private String userArea;
    
    private String userAddress;
    
    private List<OrderItem> orders;
}
