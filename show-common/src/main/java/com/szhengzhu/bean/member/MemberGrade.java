package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author makejava
 * @since 2021-04-21 10:57:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberGrade implements Serializable {
    private static final long serialVersionUID = 290664349688515344L;
    /**
     * 主键id
     */
    private String markId;
    /**
     * 等级名
     */
    private String gradeName;
    /**
     * 等级图片
     */
    private String imgId;
    /**
     * 奖品图片地址
     */
    private String imgUrl;
    /**
     * 成长值（所需消费）
     */
    private Integer consumeTotal;
    /**
     * 赠送的类型（0：两者都没有 1：积分 2：优惠券 3：两者都有）
     */
    private Integer giveType;
    /**
     * 赠送的积分
     */
    private Integer giveIntegral;
    /**
     * 消费比例 赠送积分
     */
    private BigDecimal integralProportion;
    /**
     * 会员折扣
     */
    private BigDecimal memberDiscount;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 领取券列表
     */
    private List<GradeTicket> tickets;
    /**
     * 相差的成长值
     */
    private BigDecimal amount;
}
