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
public class DishesInfo implements Serializable {
    private static final long serialVersionUID = 524258129970073860L;
    /**
     * 主键
     */
    private String markId;
    /**
     * 菜品名称
     */
    @NotBlank
    private String dishesName;
    /**
     * 菜品描述
     */
    private String description;
    /**
     * 菜品券ID
     */
    private String templateId;
    /**
     * 天数
     */
    private Integer days;
    /**
     * 使用状态（0：停用  1：启用）
     */
    private Integer status;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
}
