package com.szhengzhu.bean.member;

import com.szhengzhu.bean.member.vo.TicketTemplateVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 会员充值优惠
 * @author Administrator
 *
 */
@Data
public class RechargeRule implements Serializable {
    
    private static final long serialVersionUID = -772639063049239291L;

    private String markId;

    @NotBlank
    private String theme;

    @NotNull
    /** 充值类型(0 普通充值  1 订单金额倍数充值) */
    private Integer ruleType;

    /** 充值倍数 */
    private Integer times;

    private String description;

    @NotNull
    private BigDecimal limitAmount;

    private Integer type;

    private BigDecimal amount;

    private Date startTime;

    private Date endTime;

    private String creator;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
    
    private List<TicketTemplateVo> tickets;
}