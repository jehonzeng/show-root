package com.szhengzhu.bean.ordering.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.szhengzhu.bean.ordering.Booking;
import lombok.Data;

@Data
public class TableBaseVo implements Serializable {

    private static final long serialVersionUID = 5879088051584354731L;

    private String tableId;

    private String tableCode;

    private Integer seats;

    private Integer manNum;

    private Date openTime;

    private String tableStatus;

    private String tableColor;

    private String indentTotal;

    private String statusName;

    private List<Date> date;

    private String booker;

    private String phone;
}
