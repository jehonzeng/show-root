package com.szhengzhu.bean.excel;

import com.szhengzhu.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MealGoodsModel implements Serializable {

    private static final long serialVersionUID = 8061331230365829672L;

    @Excel(name = "商品名称", sort = 1)
    private String productName;

    @Excel(name = "数量", sort = 2)
    private Integer quantity;

    @Excel(skip = true)
    private Integer productType;// 0表示菜品 2表示套餐

    @Excel(name = "子商品名称")
    private List<MealGoodsModel> goods;

}
