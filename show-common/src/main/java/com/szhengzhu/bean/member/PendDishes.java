package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author makejava
 * @since 2020-12-10 14:08:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendDishes implements Serializable {
    private static final long serialVersionUID = 885266667944934485L;
    /**
     * 主键
     */
    private String markId;
    /**
     * 订单id
     */
    private String indentId;
    /**
     * 用户ID
     */
    @NotBlank
    private String userId;
    /**
     * 领取状态（0：已失效 1：待领取 2：已领取）
     */
    private Integer status;
    /**
     * 活动id
     */
    private String activityId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 生成的天数
     */
    private Integer days;
    /**
     * 菜品活动
     */
    private MemberActivity memberActivity;
}
