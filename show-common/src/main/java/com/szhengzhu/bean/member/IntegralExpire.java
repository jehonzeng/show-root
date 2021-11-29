package com.szhengzhu.bean.member;

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
public class IntegralExpire implements Serializable {
    private static final long serialVersionUID = -69932482948610933L;

    /*  主键 */
    private String markId;

    /*  过期时间（以月为单位） */
    private Integer expireTime;

    /*  提前推送的天数 */
    private Integer pushDays;

    /*  创建时间 */
    private Date createTime;

    /*  修改时间 */
    private Date modifyTime;

    /*  备注 */
    private String remark;
}
