package com.szhengzhu.bean.base;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CouponTemplate implements Serializable {

    private static final long serialVersionUID = -263088807836213786L;

    private String markId;

    private String couponName;

    /** 生成规则 */
    private Integer couponTotal;

    /** 优惠券类型 */
    private Integer couponType;

    /** 优惠形式 */
    private Integer serverType;

    private BigDecimal couponPrice;

    private BigDecimal couponDiscount;

    private BigDecimal limitPrice;

    private Boolean serverStatus;

    /** 限领取次数 */
    private Integer limitCount;

    /** 生成规则 */
    private Integer validityType;

    private Date startTime;

    private Date stopTime;

    /** 有效天数 */
    private Integer validityDay;

    /** 使用规则0全品1单品2部分(暂不使用) */
    private Integer rangeType;

    /** 关联商品或者类别(暂不使用) */
    private String rangeId;

    private String description;

    /** 指定商品或者名称(暂不使用) */
    private String rangeName;
    
    private String limitRegion;

    /** 指定使用时间（date1,date2） */
    private String limitTime;
}