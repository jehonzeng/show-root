package com.szhengzhu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.HolidayInfoMapper;
import com.szhengzhu.mapper.OrderDeliveryMapper;
import com.szhengzhu.mapper.OrderInfoMapper;
import com.szhengzhu.mapper.SeckillOrderMapper;
import com.szhengzhu.mapper.TeambuyOrderMapper;
import com.szhengzhu.service.OrderDeliveryService;

@Service("orderDeliveryService")
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

    @Resource
    private OrderDeliveryMapper orderDeliveryMapper;

    @Resource
    private HolidayInfoMapper holidayInfoMpper;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private TeambuyOrderMapper teamItemMapper;

    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    @Override
    public Result<PageGrid<OrderDelivery>> pageDelivery(PageParam<OrderDelivery> deliveryPage) {
        PageHelper.startPage(deliveryPage.getPageIndex(), deliveryPage.getPageSize());
        PageHelper.orderBy(deliveryPage.getSidx() + " " + deliveryPage.getSort());
        PageInfo<OrderDelivery> pageInfo = new PageInfo<>(
                orderDeliveryMapper.selectByExampleSelective(deliveryPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<OrderDelivery> getDeliveryByOrderId(String orderId) {
        OrderDelivery orderDelivery = orderDeliveryMapper.selectByOrderId(orderId);
        return new Result<>(orderDelivery);
    }

    @Override
    public Result<?> modifyDelivery(OrderDelivery orderDelivery) {
        if (orderDelivery.getDeliveryDate() != null) {
            boolean isHoliday = holidayInfoMpper.countHoliday(orderDelivery.getDeliveryDate()) > 0;
            if (isHoliday)
                return new Result<>(StatusCode._4010);
            if (orderDelivery.getOrderType().equals(0))
                orderInfoMapper.updateDeliveryDate(orderDelivery.getMarkId(), orderDelivery.getDeliveryDate());
            else if (orderDelivery.getOrderType().equals(1))
                teamItemMapper.updateDeliveryDate(orderDelivery.getMarkId(), orderDelivery.getDeliveryDate());
            else if (orderDelivery.getOrderType().equals(2))
                seckillOrderMapper.updateDeliveryDate(orderDelivery.getMarkId(), orderDelivery.getDeliveryDate());
        }
        orderDeliveryMapper.updateByPrimaryKeySelective(orderDelivery);
        return new Result<>();
    }

}
