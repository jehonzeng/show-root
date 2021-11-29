package com.szhengzhu.bean.ordering;

import com.szhengzhu.util.StringUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class DiscountInfo implements Serializable {
    /** */
    private static final long serialVersionUID = 5044466411326651262L;

    private String markId;

    @NotBlank
    private String theme;

    @NotNull
    private Integer type;

    private BigDecimal discount;

    private Date startTime;

    private Date stopTime;

    private String employIds;

    private String remark;

    private Date createTime;

    private Date modifyTime;

    private String creator;

    private String storeId;

    private Integer status;

    private String[] employs;

    private Integer sort;

    public void switchEmployIds(String[] employs) {
        StringBuffer sb = new StringBuffer();
        if (employs != null && employs.length > 0) {
            for (int i = 0, len = employs.length; i < len; i++) {
                sb.append(employs[i]);
                if (i != len - 1) {
                    sb.append(",");
                }
            }
            this.employIds = sb.toString();
        }

    }

    public void switchEmploysArray(String employIds) {
        if (!StringUtils.isEmpty(employIds)) {
            this.employs = employIds.split(",");
        }else {
            this.employs = new String[] {};
        }

    }
}
