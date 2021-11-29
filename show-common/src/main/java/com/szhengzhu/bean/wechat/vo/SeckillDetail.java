package com.szhengzhu.bean.wechat.vo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.szhengzhu.bean.activity.SeckillInfo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SeckillDetail extends SeckillInfo {

    private static final long serialVersionUID = -5303996613249847025L;
    
    private BigDecimal salePrice;

    private Cooker cookerInfo;

    private List<JudgeBase> judges;

    private List<String> imagePaths;

    private List<Map<String, String>> servers;
}
