package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * (TGradeRecord)实体类
 *
 * @author makejava
 * @since 2021-05-12 15:13:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeRecord implements Serializable {
    private static final long serialVersionUID = 949586629207894795L;

    /* 主键 */
    private String markId;

    /* 会员id */
    private String memberId;

    /* 订单id */
    private String indentId;

    /* 消费金额（成长值） */
    private BigDecimal consumeAmount;

    /* 备注 */
    private String remark;

    /* 创建时间 */
    private Date createTime;

    /* 修改时间 */
    private Date modifyTime;
}
