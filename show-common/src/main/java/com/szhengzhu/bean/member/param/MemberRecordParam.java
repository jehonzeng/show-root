package com.szhengzhu.bean.member.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 充值消费明细
 */
@Data
public class MemberRecordParam implements Serializable {
    /**
     * 会员ID
     */
    private String mark_id;
    /**
     * 会员号
     */
    private String account_no;
    /**
     * 用户ID
     */
    private String user_id;
    /**
     * 会员姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 创建时间
     */
    private String create_time;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 类型备注
     */
    private String typeName;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 操作人员
     */
    private String creator;
}
