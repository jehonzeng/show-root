package com.szhengzhu.bean.ordering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIndent implements Serializable {
    /**
     * 订单id
     */
    private String indentId;
    /**
     * 餐店名称
     */
    private String storeName;
    /**
     * 桌台id
     */
    private String tableId;
    /**
     * 餐桌号码
     */
    private String tableNum;
    /**
     * 桌台状态
     */
    private String tableStatus;
    /**
     * 下单时间
     */
    private Date time;
}
