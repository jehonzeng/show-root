package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckOrderCancel;
import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.vo.ExportTemplateVo;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.bean.wechat.vo.OrderItemDetail;
import com.szhengzhu.bean.wechat.vo.SeckillModel;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.service.SeckillService;
import com.szhengzhu.util.ShowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Jehon Zeng
 */
@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {

    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    @Resource
    private UserAddressMapper userAddressMapper;

    @Resource
    private HolidayInfoMapper holidayInfoMpper;

    @Resource
    private OrderDeliveryMapper orderDeliveryMapper;

    @Resource
    private OrderRecordMapper orderRecordMapper;

    @Resource
    private Sender sender;

    @CheckOrderCancel
    @Override
    public PageGrid<SeckillOrder> pageOrder(PageParam<SeckillOrder> orderPage) {
        PageMethod.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageMethod.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<SeckillOrder> orders = seckillOrderMapper.selectByExampleSelective(orderPage.getData());
        for (SeckillOrder order : orders) {
            String reason = orderRecordMapper.selectOrderRecord(order.getOrderNo());
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(order.getMarkId());
            order.setReason(reason);
            order.setOrderDelivery(delivery);
        }
        PageInfo<SeckillOrder> pageInfo = new PageInfo<>(orders);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public SeckillOrder modifyStatus(String markId, String orderStatus) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectByPrimaryKey(markId);
        ShowAssert.checkNull(seckillOrder, StatusCode._4014);
        // 申请退款
        boolean allowFlag = OrderStatus.PAID.equals(seckillOrder.getOrderStatus())
                || OrderStatus.STOCKING.equals(seckillOrder.getOrderStatus())
                || OrderStatus.REFUNDED.equals(seckillOrder.getOrderStatus());
        ShowAssert.checkTrue(OrderStatus.REFUNDING.equals(orderStatus) && !allowFlag, StatusCode._4015);
        // 取消订单
        ShowAssert.checkTrue(OrderStatus.CANCELLED.equals(orderStatus) && !OrderStatus.NO_PAY.equals(seckillOrder.getOrderStatus()), StatusCode._4016);
        Date time = DateUtil.date();
        if (OrderStatus.ARRIVED.equals(orderStatus)) {
            seckillOrder.setArriveTime(time);
        } else if (OrderStatus.CANCELLED.equals(orderStatus)) {
            seckillOrder.setCancelTime(time);
            sender.addCurrentStock(seckillOrder.getOrderNo());
        } else if (OrderStatus.IN_DISTRIBUTION.equals(orderStatus)) {
            seckillOrder.setSendTime(time);
        }
        seckillOrder.setOrderStatus(orderStatus);
        seckillOrderMapper.updateByPrimaryKeySelective(seckillOrder);
        return getOrderById(markId);
    }

    @Override
    public void modifyStatusNo(String orderNo, String orderStatus, String userId) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectByNo(orderNo, userId);
        ShowAssert.checkNull(seckillOrder, StatusCode._4014);
        // 申请退款
        boolean allowFlag = OrderStatus.PAID.equals(seckillOrder.getOrderStatus())
                || OrderStatus.STOCKING.equals(seckillOrder.getOrderStatus())
                || OrderStatus.REFUNDED.equals(seckillOrder.getOrderStatus());
        ShowAssert.checkTrue(OrderStatus.REFUNDING.equals(orderStatus) && !allowFlag, StatusCode._4015);
        // 取消订单
        ShowAssert.checkTrue(OrderStatus.CANCELLED.equals(orderStatus) && !OrderStatus.NO_PAY.equals(seckillOrder.getOrderStatus()), StatusCode._4016);
        Date time = DateUtil.date();
        if (OrderStatus.ARRIVED.equals(orderStatus)) {
            seckillOrder.setArriveTime(time);
        } else if (OrderStatus.CANCELLED.equals(orderStatus)) {
            seckillOrder.setCancelTime(time);
            sender.addCurrentStock(orderNo);
        }
        seckillOrder.setOrderStatus(orderStatus);
        seckillOrderMapper.updateByPrimaryKeySelective(seckillOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SeckillOrder create(SeckillModel model, SeckillInfo seckillInfo, BigDecimal deliverPrice, String storehouseId) {
        if (ObjectUtil.isNotNull(model.getDeliveryDate())) {
            boolean isHoliday = holidayInfoMpper.countHoliday(model.getDeliveryDate()) > 0;
            ShowAssert.checkTrue(isHoliday, StatusCode._4010);
        }
        // 活动规则限制
        if (ObjectUtil.isNotNull(seckillInfo.getLimited()) && seckillInfo.getLimited() > 0) {
            int orderCount = seckillOrderMapper.selectCountByUserId(model.getUserId());
            ShowAssert.checkTrue(seckillInfo.getLimited() > orderCount, StatusCode._5018);
        }
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        SeckillOrder order = SeckillOrder.builder()
                .markId(snowflake.nextIdStr())
                .orderNo(ShowUtils.createOrderNo(Contacts.TYPE_OF_SECKILL_ORDER, 1))
                .theme(seckillInfo.getTheme())
                .goodsId(seckillInfo.getGoodsId())
                .goodsName(seckillInfo.getGoodsName())
                .specificationIds(seckillInfo.getSpecificationIds())
                .seckillId(seckillInfo.getMarkId())
                .userId(model.getUserId())
                .quantity(1).orderAmount(seckillInfo.getPrice())
                .deliveryAmount(deliverPrice)
                .payAmount(seckillInfo.getPrice().add(deliverPrice))
                .orderTime(DateUtil.date())
                .deliveryDate(model.getDeliveryDate())
                .storehouseId(storehouseId)
                .orderSource(model.getOrderSource())
                .orderStatus(OrderStatus.NO_PAY).build();
        seckillOrderMapper.insertSelective(order);
        // 修改库存
        sender.subCurrentStock(order.getOrderNo());
        addDelivery(model, order.getMarkId());
        Map<String, Object> result = new HashMap<>(4);
        result.put("orderNo", order.getOrderNo());
        result.put("total", order.getPayAmount());
        result.put("deliveryAmount", order.getDeliveryAmount());
        return order;
    }

    private void addDelivery(SeckillModel model, String orderId) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        UserAddress address = userAddressMapper.selectByPrimaryKey(model.getAddressId());
        OrderDelivery delivery = OrderDelivery.builder()
                .markId(snowflake.nextIdStr())
                .orderId(orderId)
                .contact(address.getUserName())
                .deliveryDate(model.getDeliveryDate())
                .phone(address.getPhone())
                .deliveryAddress(address.getUserAddress())
                .deliveryArea(address.getProvince() + address.getCity() + address.getArea())
                .longitude(address.getLongitude())
                .latitude(address.getLatitude())
                .remark(model.getRemark())
                .orderType(Contacts.TYPE_OF_SECKILL_ORDER).build();
        orderDeliveryMapper.insertSelective(delivery);
    }

    @Override
    public SeckillOrder getOrderByNo(String orderNo) {
        SeckillOrder order = seckillOrderMapper.selectByNo(orderNo, null);
        ShowAssert.checkNull(order, StatusCode._4014);
        return order;
    }

    @Override
    public SeckillOrder getOrderById(String markId) {
        SeckillOrder order = seckillOrderMapper.selectByPrimaryKey(markId);
        if (order != null) {
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(order.getMarkId());
            order.setOrderDelivery(delivery);
        }
        return order;
    }

    @Override
    public ExportTemplateVo getPrintOrderInfo(String markId) {
        return seckillOrderMapper.selectOrderById(markId);
    }

    @Override
    public OrderDetail getOrderDetail(String orderNo, String userId) {
        SeckillOrder order = seckillOrderMapper.selectByNo(orderNo, userId);
        OrderDelivery orderDelivery = orderDeliveryMapper.selectByOrderId(order.getMarkId());
        if (ObjectUtil.isNull(orderDelivery)) { orderDelivery = new OrderDelivery(); }
        List<OrderItemDetail> itemList = new LinkedList<>();
        OrderDetail orderDetail = OrderDetail.builder()
                .orderId(order.getMarkId())
                .orderNo(order.getOrderNo()).orderTime(order.getOrderTime())
                .expireTime(new Date(order.getOrderTime().getTime() + Contacts.ORDER_EXPIRED_TIME))
                .deliveryAmount(order.getDeliveryAmount())
                .payAmount(order.getPayAmount())
                .deliveryDate(order.getDeliveryDate())
                .orderStatus(order.getOrderStatus())
                .statusDesc(order.getStatusDesc())
                .contact(orderDelivery.getContact())
                .phone(orderDelivery.getPhone())
                .address(orderDelivery.getDeliveryArea() + orderDelivery.getDeliveryAddress())
                .remark(orderDelivery.getRemark())
                .type(Contacts.TYPE_OF_SECKILL_ORDER).build();
        OrderItemDetail itemDetail = OrderItemDetail.builder()
                .productType(0)
                .productId(order.getGoodsId())
                .productName(order.getGoodsName())
                .specificationIds(order.getSpecificationIds())
                .specs(order.getSpecs())
                .salePrice(order.getPayAmount())
                .payAmount(order.getPayAmount())
                .quantity(order.getQuantity()).build();
        orderDetail.setExpireTime(new Date(orderDetail.getOrderTime().getTime() + 15L * 60 * 1000));
        itemList.add(itemDetail);
        orderDetail.setItems(itemList);
        return orderDetail;
    }

    @Override
    public List<Judge> listItemJudge(String orderNo, String userId) {
        List<Judge> judgeList = new LinkedList<>();
        SeckillOrder order = seckillOrderMapper.selectByNo(orderNo, userId);
        ShowAssert.checkNull(order, StatusCode._4014);
        Judge judge = Judge.builder()
                .orderNo(order.getOrderNo())
                .productId(order.getGoodsId())
                .specificationIds(order.getSpecificationIds())
                .productName(order.getGoodsName())
                .productType(0).build();
        judgeList.add(judge);
        return judgeList;
    }
}
