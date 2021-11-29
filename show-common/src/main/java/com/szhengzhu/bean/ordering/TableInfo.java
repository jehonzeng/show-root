package com.szhengzhu.bean.ordering;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class TableInfo implements Serializable {
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
     * 餐店ID
     */
    private String storeId;
    /**
     * 餐桌类型1  /  餐桌区域2
     */
    @NotNull
    private Integer num;
}
