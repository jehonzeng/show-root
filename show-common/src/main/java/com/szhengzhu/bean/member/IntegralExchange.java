package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegralExchange implements Serializable {
    private static final long serialVersionUID = -38593306571229027L;

    /*  主键 */
    private String markId;

    /* 名称 */
    private String name;

    /*  菜品id */
    private String commodityId;

    /*  菜品券id */
    private String templateId;

    /* 菜品券名称 */
    private String templateName;

    /* 图片id */
    private String imgId;

    /* 价格 */
    private Integer price;

    /* 图片路径 */
    private String imgPath;

    /* 展示类型 */
    private String type;

    /*  所需的积分 */
    private Integer consumeIntegral;

    /*  可兑换的数量 */
    private Integer exchangeQuantity;

    /*  可兑换开始的时间 */
    private Date startTime;

    /*  可兑换结束的时间 */
    private Date endTime;

    /*  创建时间 */
    private Date createTime;

    /*  修改时间 */
    private Date modifyTime;

    /*  状态（0：停用 1：启用） */
    private Boolean status;

    /*  创建人 */
    private String creator;

    /*  修改人 */
    private String modifier;
}
