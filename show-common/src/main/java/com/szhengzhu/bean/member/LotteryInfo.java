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
public class LotteryInfo implements Serializable {
    private static final long serialVersionUID = -43844161222798950L;

    /*  主键 */
    private String markId;
    /*  抽奖id */
    private String lotteryId;
    /*  背景图片 */
    private String bgImg;
    /*  左边图片 */
    private String leftImg;
    /*  中间图片 */
    private String midImg;
    /*  右边图片 */
    private String rightImg;
    /*  边框颜色 */
    private String rimColor;
    /*  点颜色 */
    private String dotColor;
    /* 底层背景颜色 */
    private String bgColor;
    /*  中间颜色 */
    private String midColor;
    /*  底部颜色 */
    private String bottomColor;
    /* 小背景图片 */
    private String sbgImg;
    /* 抽奖图片 */
    private String lotteryImg;
    /* 字体颜色 */
    private String fontColor;
    /* 字体背景颜色 */
    private String fontBgColor;
    /* 创建时间 */
    private Date createTime;

    private Date startTime;

    private Date endTime;

    private String bgPath;
    private String leftPath;
    private String midPath;
    private String rightPath;
    private String sbgPath;
    private String lotteryPath;
}
