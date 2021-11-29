package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberActivity implements Serializable {
    private static final long serialVersionUID = 403132312714614946L;
    /* 主键 */
    private String markId;
    /* 活动名称 */
    @NotBlank
    private String name;
    /* 活动编号 */
    private String code;
    /* 活动类型 */
    private String type;
    /* 门店id */
    private String storeId;
    /* 活动备注 */
    private String remark;
    /* 开始时间 */
    private Date beginTime;
    /* 结束时间 */
    private Date endTime;
    /* 创建时间 */
    private Date createTime;
    /* 修改时间 */
    private Date modifyTime;
    /* 创建人 */
    private String creator;
    /* 修改人  */
    private String modifier;
    /* 活动状态（0：失效 1：有效） */
    private Integer status;
    /* 领取券列表 */
    private List<ReceiveTicket> tickets;
    /* 天数 */
    private Integer days;
}
