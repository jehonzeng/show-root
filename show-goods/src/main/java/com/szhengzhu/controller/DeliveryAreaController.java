package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.DeliveryArea;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.client.ShowBaseCilent;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.DeliveryAreaService;
import com.szhengzhu.util.StringUtils;

@RestController
@RequestMapping(value = "delivery")
public class DeliveryAreaController {

    @Resource
    private DeliveryAreaService deliveryAreaService;
    
    @Resource
    private ShowBaseCilent showBaseCilent;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addDeliveryAreaInfo(@RequestBody DeliveryArea base) {
        return deliveryAreaService.addInfo(base);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> editDeliveryAreaInfo(@RequestBody DeliveryArea base) {
        return deliveryAreaService.editInfo(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<DeliveryArea>> getDeliveryAreaPage(
            @RequestBody PageParam<DeliveryArea> base) {
        return deliveryAreaService.getPage(base);
    }
    
    @RequestMapping(value = "/{markId}",method = RequestMethod.GET)
    public Result<?> deliveryAreaInfo(@PathVariable("markId") String markId){
        return deliveryAreaService.getDeliveryInfo(markId);
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.DELETE)
    public Result<?> deleteDelivery(@PathVariable() String markId) {
        return deliveryAreaService.deleteDelivery(markId);
    }

    @RequestMapping(value ="/batch",method=RequestMethod.POST)
    public Result<?> addBatchByProvince(@RequestBody DeliveryArea base) {
        if(base == null || StringUtils.isEmpty(base.getProvince()))
            return new Result<>(StatusCode._4004);
        Result<List<AreaVo>> result = showBaseCilent.listCityAndArea(base.getProvince());
        if(!result.isSuccess())
            return new Result<>(StatusCode._5000);
        return deliveryAreaService.addBatchByProvince(base,result.getData());
    }
}
