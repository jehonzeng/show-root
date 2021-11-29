package com.szhengzhu.service.impl;

import com.szhengzhu.bean.excel.DeliveryModel;
import com.szhengzhu.bean.excel.ProductModel;
import com.szhengzhu.bean.excel.SauceVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.mapper.OrderDeliveryMapper;
import com.szhengzhu.mapper.OrderInfoMapper;
import com.szhengzhu.service.ExcelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zihang
 */
@Service("excelService")
public class ExcelServiceImpl implements ExcelService {

    @Resource
    private OrderDeliveryMapper orderDeliveryMapper;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<DeliveryModel> getDownDeliveryList(List<Combobox> resultList) {
        // 解析成
        List<String> companys = new LinkedList<>();
        for (Combobox temp : resultList) {
            String company = temp.getName();
            companys.add(company);
        }
        List<DeliveryModel> deliveryList = orderDeliveryMapper.selectTodayDeliverys();
        for (DeliveryModel deliveryModel : deliveryList) {
            deliveryModel.setCompany(companys);
        }
        return deliveryList;
    }

    @Override
    public List<SauceVo> getSauceList() {
        return orderInfoMapper.selectSauceOrderCount();
    }

    @Override
    public List<ProductModel> getProductList() {
        return orderInfoMapper.selectProductCount();
    }
}
