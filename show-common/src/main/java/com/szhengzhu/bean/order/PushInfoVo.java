package com.szhengzhu.bean.order;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushInfoVo implements Serializable {
    private static final long serialVersionUID = 3993489761656915445L;

    /* 主键 */
    private String markId;

    /* 推送模板id */
    private String modalId;

    /* 标题 */
    private String title;

    /* 行业 */
    private String industry;

    /*  版本 */
    private Integer version;

    /*  内容 */
    private String pushInfo;

    /* 模板id */
    private String templateId;

    /* 类型id */
    private String typeId;

    /* 状态 */
    private Integer status;
}
