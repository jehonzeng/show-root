package com.szhengzhu.service;

import com.szhengzhu.bean.excel.DeliveryModel;
import com.szhengzhu.bean.excel.ProductModel;
import com.szhengzhu.bean.excel.SauceVo;
import com.szhengzhu.bean.vo.Combobox;

import java.util.List;

/**
 * @author Jehon Zeng
 */
public interface ExcelService {
    
    /**
     * 获取导出的配送信息(订单状态为备货时导单)
     * @param resultList
     * @return
     */
    List<DeliveryModel> getDownDeliveryList(List<Combobox> resultList);

    /**
     * 获取导出的不同瓶数的酱料订单数统计
     *
     * @return
     * @date 2019年9月12日
     */
    List<SauceVo> getSauceList();

    /**
     * 获取导出的备货菜品和套餐信息(运营)
     *
     * @return
     * @date 2019年9月12日
     */
    List<ProductModel> getProductList();
}
