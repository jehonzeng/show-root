package com.szhengzhu.bean.excel;

import java.io.Serializable;

import com.szhengzhu.annotation.Excel;

import lombok.Data;

/**
 * 
 * 备货商品统计模板
 * 
 * @author Administrator
 * @date 2019年9月12日
 */
@Data
public class ProductModel implements Serializable{
     
    private static final long serialVersionUID = 5895567794746273916L;
    
    @Excel(name="商品ID",skip=true)
    private String productId;
    
    @Excel(name="商品名称",sort=1)
    private String ProductName;
    
    @Excel(name="商品数量",sort=2)
    private Integer productCount;
    
}
