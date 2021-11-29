package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author makejava
 * @since 2021-04-21 10:57:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeTicket implements Serializable {
    private static final long serialVersionUID = 195457208694162524L;

    /**
     * 会员等级id
     */
    private String gradeId;

    /**
     * 优惠券id
     */
    private String templateId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 优惠券名称
     */
    private String templateName;
}
