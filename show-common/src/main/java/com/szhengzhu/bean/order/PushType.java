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
public class PushType implements Serializable {
    private static final long serialVersionUID = -14257687364364589L;

    /*  主键 */
    private String markId;

    /*  类型名称 */
    private String name;

    /*  创建时间 */
    private Date createTime;
}
