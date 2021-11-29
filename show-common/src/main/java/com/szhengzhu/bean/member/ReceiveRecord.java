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
public class ReceiveRecord implements Serializable {
    private static final long serialVersionUID = 261742449934323992L;
    /**
    * 主键
    */
    private String markId;
    /**
    * 用户ID
    */
    @NotBlank
    private String userId;
    /**
    * 动态描述
    */
    private String description;
    /**
    * 创建时间
    */
    private Date createTime;
}
