package com.szhengzhu.bean.member.vo;

import com.szhengzhu.bean.member.DishesImage;
import com.szhengzhu.bean.member.DishesInfo;
import com.szhengzhu.bean.member.DishesStage;
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
public class ReceiveVo implements Serializable {
    /**
     * 主键
     */
    private String markId;
    /**
     * 认领人
     */
    private String name;
    /**
     * 待领取ID
     */
    private String pendId;
    /**
     * 菜品ID
     */
    @NotBlank
    private String dishesId;
    /**
     * 菜品名
     */
    private String dishesName;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 领养昵称
     */
    private String adoptedName;
    /**
     * 阶段ID
     */
    private String stageId;
    /**
     * 编号
     */
    private String code;
    /**
     * 状态（0：已失效  1：成长中 2：可领取券  3：已领取券 ）
     */
    private Integer status;
    /**
     * 成长天数
     */
    private Integer growthDays;
    /**
     * 领养时间
     */
    private Date receiveTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 菜品
     */
    private DishesInfo dishesInfo;
    /**
     * 菜品阶段
     */
    private DishesStage dishesStage;
    /**
     * 菜品阶段图片
     */
    private DishesImage dishesImage;
}
