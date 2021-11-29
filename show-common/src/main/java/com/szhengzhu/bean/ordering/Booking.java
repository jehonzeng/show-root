package com.szhengzhu.bean.ordering;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author makejava
 * @since 2020-12-01 15:03:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking implements Serializable {
    private static final long serialVersionUID = 880843208959320396L;
    /**
     * 主键id
     */
    private String markId;
    /**
     * 预订人（称呼）
     */
    @NotBlank
    private String booker;
    /**
     * 电话
     */
    @NotBlank
    private String phone;
    /**
     * 桌台id
     */
    private String tableId;
    /**
     * 店员id
     */
    private String employeeId;
    /**
     * 预订人数
     */
    private Integer manNum;
    /**
     * 预订时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bookingTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 预订状态（-1：已取消  0：已失效  1：预订中  2：已使用）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 时间
     */
    private String time;
    /**
     * 店员名称
     */
    private String employeeName;
    /**
     * 桌台名称
     */
    private String tableName;
    /**
     * 餐厅id
     */
    private String storeId;
}
