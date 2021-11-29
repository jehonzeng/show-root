package com.szhengzhu.bean.member;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 奖品信息实体类
 *
 * @author makejava
 * @since 2021-03-15 14:35:07
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("奖品信息表")
public class PrizeInfo implements Serializable {

    private static final long serialVersionUID = -94230685661695242L;
    /* 主键id  */
    private String markId;
    /* 奖品图片 */
    private String imgId;
    /* 抽奖活动id */
    private String lotteryId;
    /*奖品图片地址 */
    private String imgUrl;
    /* 奖品名称 */
    private String prizeName;
    /* 奖品类型(0谢谢惠顾，1积分，2优惠券) */
    private Integer prizeType;
    /* 抽奖赠送的积分 */
    private Integer integral;
    /* 抽奖赠送的优惠券id */
    private String templateId;
    /* 抽奖赠送的优惠券名称 */
    private String templateName;
    /* 中奖概率 */
    private BigDecimal probability;
    /* 库存数量 */
    private Integer quantity;
    /* 使用状态（0：停用 1：启用） */
    private Integer status;
    /* 创建时间 */
    private Date createTime;
    /* 修改时间 */
    private Date modifyTime;
    /* 排序数量 */
    private Integer limitNum;
}
