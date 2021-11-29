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
public class SignDetail implements Serializable {
    private static final long serialVersionUID = 279829347532542078L;

    /*   主键 */
    private String markId;

    /*  签到id */
    private String signId;

    /*  规则id */
    private String ruleId;

    /*  创建时间 */
    private Date createTime;

    /* 日 */
    private String day;
}
