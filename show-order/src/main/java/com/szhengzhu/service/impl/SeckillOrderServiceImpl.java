package com.szhengzhu.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.wechat.vo.SeckillModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.OrderDeliveryMapper;
import com.szhengzhu.mapper.SeckillOrderMapper;
import com.szhengzhu.mapper.UserAddressMapper;
import com.szhengzhu.service.SeckillOrderService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("seckillOrderService")
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Resource
    private SeckillOrderMapper seckillOrderMapper;
    
    @Resource
    private UserAddressMapper userAddressMapper;
    
    @Resource
    private OrderDeliveryMapper orderDeliveryMapper;

    @Override
    public Result<PageGrid<SeckillOrder>> pageOrder(PageParam<SeckillOrder> orderPage) {
        PageHelper.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageHelper.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<SeckillOrder> orders = seckillOrderMapper.selectByExampleSelective(orderPage.getData());
        for (int index = 0, size = orders.size(); index < size; index++) {
            String orderId = orders.get(index).getMarkId();
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(orderId);
            orders.get(index).setOrderDelivery(delivery);
        }
        PageInfo<SeckillOrder> pageInfo = new PageInfo<>(orders);
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<?> updateStatus(String markId, String orderStatus) {
        if (StringUtils.isEmpty(markId) || StringUtils.isEmpty(orderStatus))
            return new Result<>(StatusCode._4004);
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setMarkId(markId);
        seckillOrder.setOrderStatus(orderStatus);
        Date time = TimeUtils.today();
        if (orderStatus.equals("OT05"))
            seckillOrder.setArriveTime(time);
        else if (orderStatus.equals("OT10"))
            seckillOrder.setCancelTime(time);
        else if (orderStatus.equals("OT04"))
            seckillOrder.setSendTime(time);
        seckillOrderMapper.updateByPrimaryKeySelective(seckillOrder);
        return getOrderById(markId);
    }
    @Override
    public Result<SeckillOrder> addOrder(SeckillModel model, SeckillInfo seckillInfo) {
        IdGenerator generator = IdGenerator.getInstance();
        SeckillOrder order = new SeckillOrder();
        order.setMarkId(generator.nexId());
        order.setOrderNo(ShowUtils.createOrderNo(3, model.getUserId()));
        order.setTheme(seckillInfo.getTheme());
        order.setProductId(seckillInfo.getProductId());
        order.setProductType(seckillInfo.getProductType());
        order.setProductName(seckillInfo.getProductName());
        order.setSeckillId(seckillInfo.getMarkId());
        order.setUserId(model.getUserId());
        order.setSpecificationIds(seckillInfo.getSpecificationIds());
        order.setQuantity(1);
        order.setOrderAmount(seckillInfo.getPrice());
        if (!seckillInfo.getFree()) order.setDeliveryAmount(new BigDecimal(10));
        order.setPayAmount(order.getOrderAmount().add(order.getDeliveryAmount()));
        order.setOrderTime(TimeUtils.today());
        order.setDeliveryDate(model.getDeliveryDate());
        order.setOrderSource(model.getOrderSource());
        order.setOrderStatus("OT01");
        seckillOrderMapper.insertSelective(order);
        if (!seckillInfo.getProductType().equals(1)) {
            UserAddress address = userAddressMapper.selectByPrimaryKey(model.getAddressId());
            if (address == null)
                return new Result<>(StatusCode._4004);
            addDelivery(generator.nexId(), model, address, order.getMarkId());
        }
        return null;
    }
    
    private void addDelivery(String markId, SeckillModel model, UserAddress address, String itemId) {
        OrderDelivery delivery = new OrderDelivery();
        delivery.setMarkId(markId);
        delivery.setOrderId(itemId);
        delivery.setContact(address.getUserName());
        delivery.setDeliveryDate(model.getDeliveryDate());
        delivery.setPhone(address.getPhone());
        delivery.setDeliveryAddress(address.getUserAddress());
        delivery.setDeliveryArea(address.getProvince() + address.getCity() + address.getArea());
        delivery.setLongitude(address.getLongitude());
        delivery.setLatitude(address.getLatitude());
        delivery.setRemark(model.getRemark());
        delivery.setOrderType(3);
        orderDeliveryMapper.insertSelective(delivery);
    }

    @Override
    public Result<SeckillOrder> getOrderByNo(String orderNo) {
        SeckillOrder order = seckillOrderMapper.selectByNo(orderNo);
        return new Result<>(order);
    }

    @Override
    public Result<SeckillOrder> getOrderById(String markId) {
        SeckillOrder order = seckillOrderMapper.selectByPrimaryKey(markId);
        if (order != null) {
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(order.getMarkId());
            order.setOrderDelivery(delivery);
        }
        return new Result<>(order);
    }
}
