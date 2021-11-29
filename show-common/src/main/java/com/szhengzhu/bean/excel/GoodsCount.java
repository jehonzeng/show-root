package com.szhengzhu.bean.excel;

import java.io.Serializable;
import java.util.HashMap;
import com.szhengzhu.bean.excel.GoodsCount.GoodsCountItem;

/**
 * 统计每种商品的数量
 * 
 * @author Administrator
 * @date 2019年9月12日
 */
public class GoodsCount extends HashMap<String, GoodsCountItem> implements Serializable{
    
    private static final long serialVersionUID = 1962045618063000871L;

    class GoodsCountItem {
        public String productName; 
        public Integer count; // 数量
        public Integer productType;//类型
    }

    public GoodsCountItem put(String key, String productName, int productType,
            int count) {
        GoodsCountItem item = super.get(key);
        if (item == null) {
            item = new GoodsCountItem();
            item.productName = productName;
            item.productType = productType;
            item.count = count;
            super.put(key, item);
        } else {
            item.count += count;
        }
        return item;
    }

    public Integer getCount(String key) {
        return super.get(key) != null ? super.get(key).count : 0;
    }

    public String getProductName(String key) {
        return super.get(key) != null ? super.get(key).productName : key;
    }

    public Integer getProductType(String key) {
        return super.get(key) != null ? super.get(key).productType : 0;
    }

}
