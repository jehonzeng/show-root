package com.szhengzhu.bean.xwechat.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.szhengzhu.bean.ordering.IndentRemark;
import com.szhengzhu.bean.ordering.vo.IndentPayVo;
import com.szhengzhu.code.IndentStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndentModel implements Serializable {

    private static final long serialVersionUID = 7984153118472254286L;

    private String indentId;

    private String indentNo;

    private String storeName;

    private String tableName;

    private Date indentTime;

    private Date billTime;

    private Integer manNum;

    private String indentStatus;

    private String statusName;

    /** 订单售价总额 */
    private BigDecimal costTotal;

    private BigDecimal indentTotal;

    private BigDecimal lastTotal;

    private BigDecimal memberDiscount;

    private String commdityName;

    private String imgPath;

    private List<IndentTimeModel> timeList;

    private List<IndentPayVo> payList;

    private List<IndentRemark> remarkList;

    public void setIndentStatus(String indentStatus) {
        this.indentStatus = indentStatus;
        this.statusName = IndentStatus.getValue(indentStatus);
    }
}
