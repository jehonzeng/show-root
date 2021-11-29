package com.szhengzhu.bean.wechat.vo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.szhengzhu.bean.activity.TeambuyInfo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeambuyDetail extends TeambuyInfo {

    private static final long serialVersionUID = 5267694194327949648L;

    private BigDecimal salePrice;

    private Cooker cookerInfo;

    private List<JudgeBase> judges;

    private List<String> imagePaths;

    private List<Map<String, String>> servers;
}
