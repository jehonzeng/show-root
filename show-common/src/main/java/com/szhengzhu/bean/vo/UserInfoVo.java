package com.szhengzhu.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jehon
 */
@Data
public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = 5093755458822801385L;

    private String userId;

    private String nickName;

    private String headerImg;

    private String accountNo;

    private BigDecimal accountTotal = BigDecimal.ZERO;

    private String gradeName;

    private Integer gradeSort;

    /* 会员消费成长值 */
    private Integer consumeAmount;

    private Integer integral = 0;
}
