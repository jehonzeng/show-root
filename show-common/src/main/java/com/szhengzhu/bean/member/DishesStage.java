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
public class DishesStage implements Serializable {
    private static final long serialVersionUID = 174990785793103956L;
    /**
     * 主键
     */
    private String markId;
    /**
     * 菜品ID
     */
    @NotBlank
    private String dishesId;
    /**
     * 阶段
     */
    @NotBlank
    private String stage;
    /**
     * 天数
     */
    private Integer days;
    /**
     * 开始天数
     */
    private Integer beginDays;
    /**
     * 结束天数
     */
    private Integer endDays;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 菜品名称
     */
    private String dishesName;
    /**
     * 菜品表
     */
    private DishesInfo dishesInfo;
}
