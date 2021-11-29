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
 * @since 2020-12-10 14:08:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishesImage implements Serializable {
    private static final long serialVersionUID = 639110637324184371L;
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
     * 阶段图片地址
     */
    @NotBlank
    private String stageImage;
    /**
     * 领取详细表ID
     */
    private String receiveId;
    /**
     * 阶段ID
     */
    private String stageId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 菜品表
     */
    private DishesInfo dishesInfo;
    /**
     * 菜品阶段表
     */
    private DishesStage dishesStage;
    /**
     * 领取菜品表
     */
    private ReceiveDishes receiveDishes;
}
