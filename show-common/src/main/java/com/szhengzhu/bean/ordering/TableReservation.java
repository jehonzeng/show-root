package com.szhengzhu.bean.ordering;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class TableReservation implements Serializable {
    /**
     * 餐店id
     */
    private String storeId;

    /**
     * 菜品名称
     */
    private String name;

    /**
     * 桌台区域id
     */
    private String areaId;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 日期
     */
    private Date[] dates;
}
