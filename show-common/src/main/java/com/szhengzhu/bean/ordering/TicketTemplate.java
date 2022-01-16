package com.szhengzhu.bean.ordering;

import com.szhengzhu.util.StringUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class TicketTemplate implements Serializable {

    private static final long serialVersionUID = -5347978179096774276L;

    private String markId;

    @NotBlank
    private String name;

    @NotNull
    private Integer type;

    private String description;

    private BigDecimal limitPrice;

    private Integer receivedCount;

    private BigDecimal discount;

    private Integer mode;

    private Date startTime;

    private Date stopTime;

    private Integer effectiveDays;

    private Integer overlayUse;

    private String specialDate;

    private String remark;

    private String rankIds;

    private Integer sendCount;

    private Date modifyTime;

    private Date createTime;

    private String creator;

    private String limitStore;

    private Integer status;

    private String[] commodityIds;

    private String[] vipIds;

    public void switchVipIdsArray(String rankIds) {
        if (!StringUtils.isEmpty(rankIds)) {
            this.vipIds = rankIds.split(",");
        } else {
            this.vipIds = new String[] {};
        }

    }

    public void switchRankIdsString(String[] vipIds) {
        StringBuffer sb = new StringBuffer();
        if (vipIds != null && vipIds.length > 0) {
            for (int index = 0, len = vipIds.length; index < len; index++) {
                sb.append(vipIds[index]);
                if (index != len - 1) {
                    sb.append(",");
                }
            }
            this.rankIds = sb.toString();
        }
    }
}
