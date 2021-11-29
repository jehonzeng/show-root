package com.szhengzhu.bean.member;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 奖品领取表实体类
 *
 * @author makejava
 * @since 2021-03-15 14:35:07
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("奖品领取表")
public class PrizeReceive implements Serializable {
    private static final long serialVersionUID = 824524818712729613L;

    /**
     * 主键id
     */
    private String markId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 奖品id
     */
    private String prizeId;

    /**
     * 创建时间/领取时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 优惠券名称
     */
    private String templateName;
}
