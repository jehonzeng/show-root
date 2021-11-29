package com.szhengzhu.bean.xwechat.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class SpecsModel implements Serializable {

    private static final long serialVersionUID = -8982162993331574029L;

    private String specsName;
    
    private Integer minValue;
    
    private Integer maxValue;
    
    private List<ItemModel> itemList;
}
