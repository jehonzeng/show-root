package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * (ReceiveTicket)实体类
 *
 * @author makejava
 * @since 2021-04-19 11:12:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveTicket implements Serializable {
    private static final long serialVersionUID = 745457196519846555L;

    /**
     * 赠送id
     */
    private String giveId;

    /**
     * 券模板id
     */
    private String templateId;

    /**
     * 数量
     */
    private Integer quantity;
}
