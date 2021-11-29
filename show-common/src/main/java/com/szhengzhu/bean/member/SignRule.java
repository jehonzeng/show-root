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
public class SignRule implements Serializable {

    private static final long serialVersionUID = -6716807161128320981L;

    /* 主键id */
    private String markId;

    /* 规则名 */
    private String name;

    /* 图片id */
    private String imgId;

    /* 图片路径 */
    private String imgPath;

    /* 赠送类型(1积分，2优惠券) */
    private Integer giveType;

    /* 赠送的积分 */
    private Integer giveIntegral;

    /* 赠送的券 */
    private String templateId;

    /* 优惠券名称 */
    private String templateName;

    /* 天数(签到满足天数即可领取礼包) */
    private Integer days;

    /* 创建时间 */
    private Date createTime;

    /* 修改时间 */
    private Date modifyTime;

    /* 备注 */
    private String remark;

    /* 排序 */
    private String sort;

    /*  状态（0：禁用 1：启用） */
    private Boolean status;
}
