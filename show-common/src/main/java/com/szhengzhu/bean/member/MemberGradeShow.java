package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberGradeShow {
    /**
     * 会员等级id
     */
    private String gradeId;
    /**
     * 会员图片路径
     */
    private String imgUrl;
    /**
     * 会员当前成长值
     */
    private Integer consumeTotal;
    /**
     * 会员升级赠送积分
     */
    private Integer integral;
    /**
     * 等级名
     */
    private String gradeName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 会员升级赠送几张优惠券
     */
    private Integer ticket;
    /**
     * 所需成长值
     */
    private BigDecimal amount;
}
