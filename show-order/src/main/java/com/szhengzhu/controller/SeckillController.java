package com.szhengzhu.controller;

import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.goods.DeliveryArea;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.vo.ExportTemplateVo;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.bean.wechat.vo.SeckillModel;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.SeckillService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * 秒杀订单类
 *
 * @author Jehon Zeng
 */
@Validated
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Resource
    private SeckillService seckillService;

    @Resource
    private ShowActivityClient showActivityClient;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @PostMapping(value = "/page")
    public PageGrid<SeckillOrder> pageOrder(@RequestBody PageParam<SeckillOrder> orderPage) {
        return seckillService.pageOrder(orderPage);
    }

    @GetMapping(value = "/status")
    public SeckillOrder modifyStatus(@RequestParam("orderId") @NotBlank String orderId,
                                     @RequestParam("orderStatus") @NotBlank String orderStatus) {
        return seckillService.modifyStatus(orderId, orderStatus);
    }

    @GetMapping(value = "/fore/status")
    public void modifyStatusByNo(@RequestParam("orderNo") String orderNo,
            @RequestParam("orderStatus") String orderStatus,
            @RequestParam(value = "userId", required = false) String userId) {
        seckillService.modifyStatusNo(orderNo, orderStatus, userId);
    }

    @PostMapping(value = "/create")
    public SeckillOrder create(@RequestBody SeckillModel model) {
        // 此处key改为秒杀的库存
        Object value = redisTemplate.opsForList().rightPop("activity:seckill:stock:" + model.getMarkId());
        // 当库存不足时返回null 利用redis，用户下单占库存
        ShowAssert.checkNull(value, StatusCode._5017);
        Result<SeckillInfo> seckillResult = showActivityClient.getSeckillInfo(model.getMarkId());
        ShowAssert.checkTrue(!seckillResult.isSuccess(), StatusCode._5018);
        SeckillInfo seckillInfo = seckillResult.getData();
        ShowAssert.checkTrue(seckillInfo.getStopTime().getTime() < System.currentTimeMillis(), StatusCode._5018);
        Result<StockBase> stockResult = showGoodsClient.getGoodsStcokInfo(seckillInfo.getGoodsId(),
                seckillInfo.getSpecificationIds(), model.getAddressId());
        ShowAssert.checkTrue(!stockResult.getData().getIsDelivery(), StatusCode._5016);
        ShowAssert.checkTrue(stockResult.getData().getCurrentStock() < 1, StatusCode._5017);
        ShowAssert.checkTrue(seckillInfo.getTotalStock() < 1, StatusCode._5017);
        BigDecimal deliveryPrice = new BigDecimal("0.00");
        if (!seckillInfo.getFree()) {
            Result<DeliveryArea> deliveryResult = showGoodsClient.getDeliveryPrice(model.getAddressId());
            if (seckillInfo.getPrice().compareTo(deliveryResult.getData().getLimitPrice()) < 0) {
                deliveryPrice = deliveryResult.getData().getDeliveryPrice();
            }
        }
        return seckillService.create(model, seckillInfo, deliveryPrice, stockResult.getData().getStorehouseId());
    }

    @GetMapping(value = "/info")
    public SeckillOrder getOrderByNo(@RequestParam("orderNo") @NotBlank String orderNo) {
        return seckillService.getOrderByNo(orderNo);
    }

    @GetMapping(value = "/{markId}")
    public SeckillOrder getInfoById(@PathVariable("markId") @NotBlank String markId) {
        return seckillService.getOrderById(markId);
    }

    @GetMapping(value = "/print/{markId}")
    public ExportTemplateVo getExportSekillOrderInfo(@PathVariable("markId") @NotBlank String markId) {
        return seckillService.getPrintOrderInfo(markId);
    }

    @GetMapping(value = "/fore/detial")
    public OrderDetail orderDetail(@RequestParam("orderNo") @NotBlank String orderNo, @RequestParam(value = "userId", required = false) String userId) {
        return seckillService.getOrderDetail(orderNo, userId);
    }

    @GetMapping(value = "/item/fore/judge")
    public List<Judge> listItemJudge(@RequestParam("orderNo") @NotBlank String orderNo,
            @RequestParam("userId") @NotBlank String userId) {
        return seckillService.listItemJudge(orderNo, userId);
    }
}
