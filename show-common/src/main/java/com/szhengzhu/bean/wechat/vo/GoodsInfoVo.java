package com.szhengzhu.bean.wechat.vo;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GoodsInfoVo extends GoodsBase {

    private static final long serialVersionUID = -2914712889092920270L;

    private String content;
    
    private Boolean follow; // 商品关注
    
    private String defSpecIds; // 默认规格集
    
    private String defSpecValues; // 默认规格集显示值
    
    private Cooker cookerInfo;
    
    private List<JudgeBase> judges;
    
    private List<String> imagePaths;
    
    private List<Map<String, String>> servers;
}
