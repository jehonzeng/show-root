package com.szhengzhu.bean.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员信息
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberRecord implements Serializable {

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 商店ID
     */
    private String storeId;
}
