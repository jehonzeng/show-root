package com.szhengzhu.bean.ordering.vo;

import java.util.List;

import com.szhengzhu.bean.ordering.Category;

public class CategoryVo extends Category {

    private static final long serialVersionUID = -9026894986284514920L;
    
//    private List<CategoryCommodity> commodityList;
    
    private String[] commodityList;
    
    private String[] specsList;

    public String[] getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<String> commodityList) {
        this.commodityList = commodityList.toArray(new String[commodityList.size()]);
    }

    public String[] getSpecsList() {
        return specsList;
    }

    public void setSpecsList(List<String> specsList) {
        this.specsList = specsList.toArray(new String[specsList.size()]);
    }
    
    
}
