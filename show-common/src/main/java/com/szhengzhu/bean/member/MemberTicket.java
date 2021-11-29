package com.szhengzhu.bean.member;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class MemberTicket implements Serializable {
    /**
     * 优惠券id
     */
    private String mark_id;
    /**
     * 优惠券名称
     */
    private String name;
    /**
     * 优惠券类型（0:代金 1：折扣）
     */
    private Integer type;
    /**
     * 优惠券类型名称
     */
    private String typeName;
    /**
     * 优惠券有效期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stop_time;
    /**
     * 优惠券获取方式（0：自动发送，1：手动发送）
     */
    private Integer issue_type;
    /**
     * 优惠券获取方式名称
     */
    private String issueName;
    /**
     * 优惠券获取时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start_time;
    /**
     * 优惠券使用状态（-1：已过期 0：已使用 1：未使用）
     */
    private Integer status;
    /**
     * 优惠券使用状态名称
     */
    private String statusName;
    /**
     * 优惠券使用的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date use_time;

}
