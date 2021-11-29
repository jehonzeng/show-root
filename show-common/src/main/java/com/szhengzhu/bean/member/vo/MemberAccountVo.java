package com.szhengzhu.bean.member.vo;

import com.szhengzhu.bean.member.MemberGrade;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MemberAccountVo {

    private String markId;

    private String accountNo;

    private String  userId;

    private String name;

    private Integer gender;

    private String phone;

    private Date birthday;

    private BigDecimal totalAmount;

//    @JsonFormat()
    private Date createTime;

    private Date modifyTime;

    /** 消费总金额 */
    private BigDecimal consumptionAmount;

    /** 充值总金额 */
    private BigDecimal rechargeAmount;

    private Integer integralTotal;

    /**
     * 最后一次充值时间
     */
    private Date time;

    private String gradeId;

    private Integer consumeAmount;

    private MemberGrade memberGrade;
}
