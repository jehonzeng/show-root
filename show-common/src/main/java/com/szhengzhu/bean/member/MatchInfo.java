package com.szhengzhu.bean.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MatchInfo implements Serializable {

    private static final long serialVersionUID = -6084010974551986059L;

    private String markId;

    @NotBlank
    private String matchName;

    private String creator;

    @NotNull
    private Date startTime;

    @NotNull
    private Date endTime;

    private String imgId;

    private String descriptionId;

    @NotNull
    private Integer ticketType;

    @NotBlank
    private String ticketName;

    private BigDecimal amount;

    private Integer winValue;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private Boolean status;

    /* 0：消费赠送机会  1：自动赠送一次机会 */
    private Integer giveChance;

    /* 消费金额 */
    private Integer consumeAmount;

    private String imgPath;

    private String descriptionPath;
}
