package com.szhengzhu.bean.ordering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserTicket implements Serializable {
    /** */
    private static final long serialVersionUID = 1586073129306121894L;

    /** 优惠券id */
    private String markId;

    /**用户id */
    private String userId;

    /**优惠券模板id*/
    private String templateId;

    //优惠券名称
    private String name;

    //优惠券类型（0:代金 1：折扣）
    private Integer type;

    private String description;

    private BigDecimal limitPrice;

    private BigDecimal discount;

    //优惠券获取时间
    private Date startTime;

    //优惠券有效期
    private Date stopTime;

    private Date useTime;

    private Integer overlayUse;

    private String specialDate;

    private String rankIds;

    private Date createTime;

    /** 优惠券发放方式（0：自动发送，1：手动发送）*/
    private Boolean issueType;

    /**优惠券使用状态（-1：已过期 0：已使用 1：未使用） */
    private Integer status;

    private String limitStore;
}
