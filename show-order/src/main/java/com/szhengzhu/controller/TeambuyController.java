package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowActivityClient;
import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.bean.goods.DeliveryArea;
import com.szhengzhu.bean.order.TeambuyGroup;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.bean.vo.ExportTemplateVo;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.bean.wechat.vo.TeambuyModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.TeambuyService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * 团购订单类
 *
 * @author Jehon Zeng
 */
@Validated
@RestController
@RequestMapping("/teambuy")
public class TeambuyController {

    @Resource
    private TeambuyService teambuyService;

    @Resource
    private ShowActivityClient showActivityClient;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @PostMapping(value = "/group/page")
    public PageGrid<TeambuyGroup> pageOrder(@RequestBody PageParam<TeambuyGroup> groupPage) {
        return teambuyService.pageGroup(groupPage);
    }

    @GetMapping(value = "/group/{markId}")
    public TeambuyGroup getInfo(@PathVariable("markId") @NotBlank String markId) {
        return teambuyService.getInfo(markId);
    }

    @PostMapping(value = "/page")
    public PageGrid<TeambuyOrder> pageItem(@RequestBody PageParam<TeambuyOrder> orderPage) {
        return teambuyService.pageOrder(orderPage);
    }

    @GetMapping(value = "/status")
    public TeambuyOrder modifyStatus(@RequestParam("orderId") @NotBlank String orderId,
                                     @RequestParam("orderStatus") @NotBlank String orderStatus) {
        return teambuyService.modifyStatus(orderId, orderStatus);
    }

    @GetMapping(value = "/fore/status")
    public void modifyStatusByNo(@RequestParam("orderNo") @NotBlank String orderNo,
            @RequestParam("orderStatus") @NotBlank String orderStatus, @RequestParam(value = "userId", required = false) String userId) {
        teambuyService.modifyStatusNo(orderNo, orderStatus, userId);
    }

    @PostMapping(value = "/create")
    public TeambuyOrder create(@RequestBody TeambuyModel model) {
        //此处key改为团购的库存
        Object value = redisTemplate.opsForList().rightPop("activity:teambuy:stock:" + model.getMarkId());
        // 当库存不足时返回null 利用redis，用户下单占库存
        ShowAssert.checkNull(value, StatusCode._5017);
        Result<TeambuyInfo> teambuyResult = showActivityClient.getTeambuyInfo(model.getMarkId());
        ShowAssert.checkTrue(!teambuyResult.isSuccess(), StatusCode._5018);
        TeambuyInfo teambuyInfo = teambuyResult.getData();
        ShowAssert.checkTrue(teambuyInfo.getStopTime().getTime() < System.currentTimeMillis(), StatusCode._5018);
        Result<StockBase> stockResult = showGoodsClient.getGoodsStcokInfo(teambuyInfo.getGoodsId(),
                teambuyInfo.getSpecificationIds(), model.getAddressId());
        ShowAssert.checkTrue(!stockResult.getData().getIsDelivery(), StatusCode._5016);
        ShowAssert.checkTrue(stockResult.getData().getCurrentStock() < 1, StatusCode._5017);
        ShowAssert.checkTrue(teambuyInfo.getTotalStock() < 1, StatusCode._5017);
        BigDecimal deliveryPrice = new BigDecimal("0.00");
        if (!teambuyInfo.getFree()) {
            Result<DeliveryArea> deliveryResult = showGoodsClient.getDeliveryPrice(model.getAddressId());
            if (teambuyInfo.getPrice().compareTo(deliveryResult.getData().getLimitPrice()) < 0) {
                deliveryPrice = deliveryResult.getData().getDeliveryPrice();
            }
        }
        return teambuyService.create(model, teambuyInfo, deliveryPrice, stockResult.getData().getStorehouseId());
    }

    @GetMapping(value = "/info")
    public TeambuyOrder getOrderByNo(@RequestParam("orderNo") @NotBlank String orderNo) {
        return teambuyService.getOrderByNo(orderNo);
    }

    @GetMapping(value = "/{orderId}")
    public TeambuyOrder getOrderById(@RequestParam("orderId") @NotBlank String orderId) {
        return teambuyService.getOrderById(orderId);
    }

    @GetMapping(value = "/print/{markId}")
    public ExportTemplateVo getExportCollageOrderInfo(@PathVariable("markId") @NotBlank String markId) {
        return teambuyService.getPrintOrderInfo(markId);
    }

    @GetMapping(value = "/fore/detial")
    public OrderDetail orderDetail(@RequestParam("orderNo") @NotBlank String orderNo, @RequestParam(value = "userId", required = false) String userId) {
        return teambuyService.getOrderDetail(orderNo, userId);
    }

    @GetMapping(value = "/item/fore/judge")
    public List<Judge> listItemJudge(@RequestParam("orderNo") @NotBlank String orderNo, @RequestParam("userId") @NotBlank String userId) {
        return teambuyService.listItemJudge(orderNo, userId);
    }
}
