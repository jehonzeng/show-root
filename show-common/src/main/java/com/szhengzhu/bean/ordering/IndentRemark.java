package com.szhengzhu.bean.ordering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author makejava
 * @since 2021-03-05 14:12:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndentRemark implements Serializable {
    private static final long serialVersionUID = 438354597415969050L;
    /**
     * 主键
     */
    private String markId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 订单id
     */
    private String indentId;
    /**
     * 商店id
     */
    private String storeId;
    /**
     * 服务满意度（打星）
     */
    private Integer serviceStar;
    /**
     * 餐店满意度（打星）
     */
    private Integer storeStar;
    /**
     * 菜品满意度（打星）
     */
    private Integer dishesStar;
    /**
     * 菜品评论
     */
    private String dishesRemark;
    /**
     * 服务评论
     */
    private String serviceRemark;
    /**
     * 其他评论
     */
    private String other;
    /**
     * 创建时间
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
     * 下单时间
     */
    private Date billTime;
    /**
     * 桌台号
     */
    private String tableName;
    /**
     * 是否会员
     */
    private Integer isMember;
}
