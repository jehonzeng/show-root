package com.szhengzhu.controller;

import com.szhengzhu.bean.excel.DeliveryModel;
import com.szhengzhu.bean.excel.ProductModel;
import com.szhengzhu.bean.excel.SauceVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.ExcelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * excel操作类
 *
 * @author Jehon Zeng
 */
@RestController
@RequestMapping(value = "excels")
public class ExcelController {

    @Resource
    private ExcelService excelService;

    @Resource
    private ShowBaseClient showBaseClient;

    @GetMapping(value="/delivery")
    public List<DeliveryModel> getDownDeliveryList(){
        Result<List<Combobox>> result = showBaseClient.listComboboxByType("DT");
        return excelService.getDownDeliveryList(result.getData());
    }

    @GetMapping(value = "/sauce")
    public List<SauceVo> getDownSauceList() {
        return excelService.getSauceList();
    }

    @GetMapping(value = "/operator/product")
    public List<ProductModel> getProductList() {
        return excelService.getProductList();
    }
}
