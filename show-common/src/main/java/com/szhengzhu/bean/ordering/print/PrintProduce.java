package com.szhengzhu.bean.ordering.print;

import lombok.Data;

@Data
public class PrintProduce {

    /** 桌台 */
    private String tableCode;

    /** 用餐人数*/
    private Integer manNum;

    /** 1点菜、2退菜、3换桌、4换菜*/
    private Integer operate;
    
    /** 商品名称 */
    private String commodityName;
    
    /** 商品属性 */
    private String specs;
    
    /** 数量 */
    private Integer quantity;
    
    /** 单位*/
    private String unit;
}
