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
public class MemberLottery implements Serializable {
    private static final long serialVersionUID = 193260861306632485L;

    /*  主键 */
    private String markId;

    /*  名称 */
    private String name;

    /*  消费的积分 */
    private Integer consumeIntegral;

    /* 概率倍数 */
    private Integer multiple;

    /*  创建时间 */
    private Date createTime;

    /*  修改时间 */
    private Date modifyTime;

    /*  创建人 */
    private String creator;

    /*  状态（0：禁用 1：启用） */
    private Boolean status;

    /* 开始时间 */
    private Date startTime;

    /* 结束时间 */
    private Date endTime;

    /* 是否自动赠送机会0：不赠送 1：赠送 */
    private Boolean giveChance;

    /* 抽奖类型 0：积分抽奖 1：新品尝鲜抽奖 */
    private Integer type;
}
