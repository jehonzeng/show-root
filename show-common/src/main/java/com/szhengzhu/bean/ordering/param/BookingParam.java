package com.szhengzhu.bean.ordering.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingParam implements Serializable {
    private static final long serialVersionUID = -6697262968292566140L;
    /**
     * 主键id
     */
    private String markId;

    /**
     * 预订时间
     */
    private Date bookingTime;

    /**
     * 桌台id
     */
    private String tableId;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 商店id
     */
    private String storeId;

    /**
     * 状态
     */
    private Integer status;
}
