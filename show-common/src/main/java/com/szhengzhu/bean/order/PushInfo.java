package com.szhengzhu.bean.order;

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
public class PushInfo implements Serializable {
    private static final long serialVersionUID = 267860562998172410L;

    /*  主键 */
    private String markId;

    /*  模板id */
    private String templateId;

    /*  版本 */
    private Integer version;

    /*  内容 */
    private String pushInfo;

    /*  状态（0:未使用 1：使用中） */
    private Integer status;

    /*  创建时间 */
    private Date createTime;

    /* 修改时间 */
    private Date modifyTime;

    /* 类型id */
    private String typeId;

    /*  */
    private PushTemplate pushTemplate;
}
