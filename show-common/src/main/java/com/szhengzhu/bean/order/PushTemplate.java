package com.szhengzhu.bean.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushTemplate implements Serializable {
    private static final long serialVersionUID = 325206185620469253L;

    /* 主键 */
    private String markId;

    /* 推送模板id */
    private String modalId;

    /* 标题 */
    private String title;

    /* 行业 */
    private String industry;

    /* 创建时间 */
    private Date createTime;

    /* 修改时间 */
    private Date modifyTime;

    /* 推送详细信息记录 */
    private List<PushInfo> pushInfo;

    /* 状态 */
    private Integer status;
}
