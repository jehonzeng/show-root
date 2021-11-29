package com.szhengzhu.bean.xwechat.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class CategoryModel implements Serializable {

    private static final long serialVersionUID = -8625551274432279728L;

    private String cateId;
    
    private String cateName;
    
    private String cateImg;
    
    private List<CommodityModel> commodityList;
}
