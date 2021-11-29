package com.szhengzhu.bean.vo;

import java.io.Serializable;

import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.core.Contacts;

import lombok.Data;

/**
 * 推送物流信息模板
 * 
 * @author Administrator
 * @date 2019年9月6日
 */

@Data
public class DeliveryMsgVo implements Serializable{
    
    private static final long serialVersionUID = -751013554109179707L;

    private String phone;//联系电话
    
    private String name;//联系人
    
    private String trackNo;//物流编号
    
    private String customerService;//客服联系方式
    
    public  DeliveryMsgVo() {}
    
    public DeliveryMsgVo(OrderDelivery base) {
        this.phone = base.getPhone();
        this.name = base.getContact();
        this.trackNo = base.getTrackNo();
        this.customerService = Contacts.CUSTOMER_SERVICE;
    }  
}
