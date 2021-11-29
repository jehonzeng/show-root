package com.szhengzhu.bean.xwechat.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class CalcVo implements Serializable {

    private BigDecimal total;

    private List<PayTypeModel> discountList;

//    private List<Map<String, Object>> discountList;
}
