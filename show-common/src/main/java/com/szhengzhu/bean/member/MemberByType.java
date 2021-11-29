package com.szhengzhu.bean.member;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 条件查询
 * @author Administrator
 */
@Data
public class MemberByType implements Serializable {
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 选择类型（今日新增会员）
     */
    private Boolean is_NewMember;
    /**
     * 选择类型（今日会员消费）
     */
    private Boolean is_NowConsume;
    /**
     * 选择类型（今日会员充值）
     */
    private Boolean is_NowRecharge;
    /**
     * 选择类型（账户总额）
     */
    private Boolean is_AmountTotal;
    /**
     * 选择类型（默认：null 注册时间：1 充值时间：2 消费时间：3）
     */
    private Integer temp;
    /**
     * 开始和结束日期数组
     */
    private Date[] dates;
    /**
     * 会员等级id
     */
    private String gradeId;
}
