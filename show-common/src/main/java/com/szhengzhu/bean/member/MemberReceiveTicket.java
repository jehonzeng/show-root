package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberReceiveTicket {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 券模板id
     */
    private String templateId;

    /**
     * 数量
     */
    private Integer quantity;
}
