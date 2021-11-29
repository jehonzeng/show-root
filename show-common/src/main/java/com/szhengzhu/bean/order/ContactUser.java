package com.szhengzhu.bean.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactUser implements Serializable {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 客户姓名
     */
    private String name;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 详细内容
     */
    private String content;
    /**
     * 异常原因
     */
    private String reason;
    /**
     * 订单id
     */
    @NotBlank
    private String orderId;
}
