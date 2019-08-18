package com.szhengzhu.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.bean.order.TeambuyGroup;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.wechat.vo.TeambuyModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.HolidayInfoMapper;
import com.szhengzhu.mapper.OrderDeliveryMapper;
import com.szhengzhu.mapper.TeambuyOrderMapper;
import com.szhengzhu.mapper.TeambuyGroupMapper;
import com.szhengzhu.mapper.UserAddressMapper;
import com.szhengzhu.service.TeambuyOrderService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("teambuyOrderService")
public class TeambuyOrderServiceImpl implements TeambuyOrderService {

    @Resource
    private TeambuyGroupMapper teambuyGroupMapper;

    @Resource
    private TeambuyOrderMapper teambuyOrderMapper;
    
    @Resource
    private HolidayInfoMapper holidayInfoMpper;
    
    @Resource
    private UserAddressMapper userAddressMapper;
    
    @Resource
    private OrderDeliveryMapper orderDeliveryMapper;

    @Override
    public Result<PageGrid<TeambuyGroup>> pageGroup(PageParam<TeambuyGroup> orderPage) {
        PageHelper.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageHelper.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<TeambuyGroup> groups = teambuyGroupMapper.selectByExampleSelective(orderPage.getData());
        for (int index = 0, size = groups.size(); index < size; index++) {
            String groupId = groups.get(index).getMarkId();
            List<TeambuyOrder> orders = teambuyOrderMapper.selectByGroupId(groupId);
            groups.get(index).setItems(orders);
        }
        PageInfo<TeambuyGroup> pageInfo = new PageInfo<>(groups);
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<PageGrid<TeambuyOrder>> pageOrder(PageParam<TeambuyOrder> orderPage) {
        PageHelper.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageHelper.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<TeambuyOrder> items = teambuyOrderMapper.selectByExampleSelective(orderPage.getData());
        for (int index = 0, size = items.size(); index < size; index++) {
            String orderId = items.get(index).getMarkId();
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(orderId);
            items.get(index).setOrderDelivery(delivery);
        }
        PageInfo<TeambuyOrder> pageInfo = new PageInfo<>(items);
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<?> updateStatus(String markId, String orderStatus) {
        if (StringUtils.isEmpty(markId) || StringUtils.isEmpty(orderStatus)) {
            return new Result<>(StatusCode._4004);
        }
        TeambuyOrder order = new TeambuyOrder();
        order.setMarkId(markId);
        order.setOrderStatus(orderStatus);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if (orderStatus.equals("OT05"))
            order.setArriveTime(time);
        else if (orderStatus.equals("OT10"))
            order.setCancelTime(time);
        else if (orderStatus.equals("OT04"))
            order.setSendTime(time);
        teambuyOrderMapper.updateByPrimaryKeySelective(order);
        return getOrderById(markId);
    }
    @Transactional
    @Override
    public Result<TeambuyOrder> addOrder(TeambuyModel model, TeambuyInfo teambuyInfo) {
        boolean isHoliday = holidayInfoMpper.countHoliday(model.getDeliveryDate()) > 0;
        if (isHoliday)
            return new Result<>(StatusCode._4010);
        IdGenerator generator = IdGenerator.getInstance();
        TeambuyOrder order = new TeambuyOrder();
        if (StringUtils.isEmpty(model.getGroupNo())) {
            TeambuyGroup group = addOrder(generator.nexId(), teambuyInfo, model.getUserId());
            order = addItem(generator.nexId(), model, teambuyInfo, group.getMarkId(), 1);
        } else {
            TeambuyGroup group = teambuyGroupMapper.selectByNo(model.getGroupNo());
            if (group == null)
                return new Result<>(StatusCode._4004);
            if (!group.getServerStatus().equals(1))
                return new Result<>(StatusCode._5007);
            group.setCurrentCount(group.getCurrentCount() + 1);
            teambuyGroupMapper.updateByPrimaryKeySelective(group);
            order = addItem(generator.nexId(), model, teambuyInfo, group.getMarkId(), 1);
        }
        if (!teambuyInfo.getProductType().equals(1)) {
            UserAddress address = userAddressMapper.selectByPrimaryKey(model.getAddressId());
            if (address == null)
                return new Result<>(StatusCode._4004);
            addDelivery(generator.nexId(), model, address, order.getMarkId());
        }
        return new Result<>(order);
    }
    
    private TeambuyGroup addOrder(String markId, TeambuyInfo teambuyInfo, String userId) {
        TeambuyGroup group = new TeambuyGroup();
        group.setMarkId(markId);
        group.setType(teambuyInfo.getType());
        group.setTheme(teambuyInfo.getTheme());
        group.setProductId(teambuyInfo.getProductId());
        group.setProductType(teambuyInfo.getProductType());
        group.setTeambuyId(teambuyInfo.getMarkId());
        group.setCreator(userId);
        group.setCreateTime(TimeUtils.today());
        group.setReqCount(teambuyInfo.getReqCount());
        group.setCurrentCount(1);
        group.setVaildTime(teambuyInfo.getVaildTime());
        group.setServerStatus(1);
        teambuyGroupMapper.insertSelective(group);
        return group;
    }
    private TeambuyOrder addItem(String markId, TeambuyModel model, TeambuyInfo teambuyInfo, String groupId, int sort) {
        TeambuyOrder order = new TeambuyOrder();
        order.setMarkId(markId);
        order.setOrderNo(ShowUtils.createOrderNo(2, model.getUserId()));
        order.setProductId(teambuyInfo.getProductId());
        order.setProductType(teambuyInfo.getProductType());
        order.setGroupId(groupId);
        order.setUserId(model.getUserId());
        order.setProductName(teambuyInfo.getProductName());
        order.setSpecificationIds(model.getSpecIds());
        order.setQuantity(1);
        order.setOrderAmount(teambuyInfo.getPrice());
        if (!teambuyInfo.getFree()) order.setDeliveryAmount(new BigDecimal(10)); // 设置配送费
        order.setPayAmount(order.getOrderAmount().add(order.getDeliveryAmount()));
        order.setOrderTime(TimeUtils.today());
        order.setDeliveryDate(model.getDeliveryDate());
        order.setOrderSource(model.getOrderSource());
        order.setSort(sort);
        order.setOrderStatus("OT01");
        teambuyOrderMapper.insertSelective(order);
        return order;
    }
    
    private void addDelivery(String markId, TeambuyModel model, UserAddress address, String orderId) {
        OrderDelivery delivery = new OrderDelivery();
        delivery.setMarkId(markId);
        delivery.setOrderId(orderId);
        delivery.setContact(address.getUserName());
        delivery.setDeliveryDate(model.getDeliveryDate());
        delivery.setPhone(address.getPhone());
        delivery.setDeliveryAddress(address.getUserAddress());
        delivery.setDeliveryArea(address.getProvince() + address.getCity() + address.getArea());
        delivery.setLongitude(address.getLongitude());
        delivery.setLatitude(address.getLatitude());
        delivery.setRemark(model.getRemark());
        delivery.setOrderType(2);
        orderDeliveryMapper.insertSelective(delivery);
    }

    @Override
    public Result<TeambuyOrder> getOrderByNo(String orderNo) {
        TeambuyOrder order = teambuyOrderMapper.selectByNo(orderNo);
        return new Result<>(order);
    }

    @Override
    public Result<TeambuyOrder> getOrderById(String markId) {
        TeambuyOrder order = teambuyOrderMapper.selectByPrimaryKey(markId);
        if (order != null) {
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(order.getMarkId());
            order.setOrderDelivery(delivery);
        }
        return new Result<>(order);
    }
}
