package com.szhengzhu.controller;

import cn.hutool.core.util.StrUtil;
import com.szhengzhu.bean.goods.DeliveryArea;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.DeliveryAreaService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "/delivery")
public class DeliveryAreaController {

    @Resource
    private DeliveryAreaService deliveryAreaService;

    @Resource
    private ShowBaseClient showBaseClient;

    @PostMapping(value = "/add")
    public DeliveryArea addDeliveryAreaInfo(@RequestBody @Validated DeliveryArea base) {
        return deliveryAreaService.addInfo(base);
    }

    @PatchMapping(value = "/modify")
    public DeliveryArea editDeliveryAreaInfo(@RequestBody @Validated DeliveryArea base) {
        return deliveryAreaService.editInfo(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<DeliveryArea> getDeliveryAreaPage(
            @RequestBody PageParam<DeliveryArea> base) {
        return deliveryAreaService.getPage(base);
    }

    @GetMapping(value = "/{markId}")
    public DeliveryArea deliveryAreaInfo(@PathVariable("markId") @NotBlank String markId) {
        return deliveryAreaService.getDeliveryInfo(markId);
    }

    @DeleteMapping(value = "/{markId}")
    public void deleteDelivery(@PathVariable("markId") @NotBlank String markId) {
        deliveryAreaService.deleteDelivery(markId);
    }

    @PostMapping(value = "/batch")
    public void addBatchByProvince(@RequestBody DeliveryArea base) {
        ShowAssert.checkTrue(StrUtil.isEmpty(base.getProvince()), StatusCode._4004);
        Result<List<AreaVo>> result = showBaseClient.listCityAndArea(base.getProvince());
        ShowAssert.checkTrue(!result.isSuccess(), StatusCode._5000);
        deliveryAreaService.addBatchByProvince(base, result.getData());
    }

    @GetMapping(value = "/price/{addressId}")
    public DeliveryArea getDeliveryPrice(@PathVariable("addressId") @NotBlank String addressId) {
        return deliveryAreaService.getDeliveryPrice(addressId);
    }

    @PostMapping(value = "/area/enabled")
    public void enabledDeliveryArea(@RequestBody DeliveryArea base) {
        deliveryAreaService.updateBatchStatus(base);
    }
}
