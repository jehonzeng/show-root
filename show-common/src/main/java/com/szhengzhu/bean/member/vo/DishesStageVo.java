package com.szhengzhu.bean.member.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishesStageVo implements Serializable {
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
}
