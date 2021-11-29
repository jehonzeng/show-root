package com.szhengzhu.bean.ordering;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Income implements Serializable {
    private static final long serialVersionUID = 5674210669768575921L;
    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 是否是实收
     */
    private boolean received;
    /**
     * 员工ID
     */
    private String employeeId;
    /**
     * 餐店ID
     */
    private String storeId;
}
