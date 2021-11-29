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
public class DishesImageVo implements Serializable {
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
}
