package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckOrderCancel;
import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.order.TeambuyGroup;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.vo.ExportTemplateVo;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.bean.wechat.vo.OrderItemDetail;
import com.szhengzhu.bean.wechat.vo.TeambuyModel;
import com.szhengzhu.code.GroupStatus;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.service.TeambuyService;
import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Jehon Zeng
 */
@Service("teambuyService")
public class TeambuyServiceImpl implements TeambuyService {

    @Resource
    private TeambuyGroupMapper teambuyGroupMapper;

    @Resource
    private TeambuyOrderMapper teambuyOrderMapper;

    @Resource
    private HolidayInfoMapper holidayInfoMapper;

    @Resource
    private UserAddressMapper userAddressMapper;

    @Resource
    private OrderDeliveryMapper orderDeliveryMapper;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderRecordMapper orderRecordMapper;

    @Resource
    private Sender sender;

    @CheckOrderCancel
    @Override
    public PageGrid<TeambuyGroup> pageGroup(PageParam<TeambuyGroup> orderPage) {
        PageMethod.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageMethod.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<TeambuyGroup> groups = teambuyGroupMapper.selectByExampleSelective(orderPage.getData());
        for (TeambuyGroup group : groups) {
            List<TeambuyOrder> orders = teambuyOrderMapper.selectByGroupId(group.getMarkId());
            group.setItems(orders);
        }
        PageInfo<TeambuyGroup> pageInfo = new PageInfo<>(groups);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public TeambuyGroup getInfo(String markId) {
        return teambuyGroupMapper.selectByPrimaryKey(markId);
    }

    @CheckOrderCancel
    @Override
    public PageGrid<TeambuyOrder> pageOrder(PageParam<TeambuyOrder> orderPage) {
        PageMethod.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageMethod.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<TeambuyOrder> items = teambuyOrderMapper.selectByExampleSelective(orderPage.getData());
        for (TeambuyOrder order : items) {
            String reason = orderRecordMapper.selectOrderRecord(order.getOrderNo());
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(order.getMarkId());
            order.setReason(reason);
            order.setOrderDelivery(delivery);
        }
        PageInfo<TeambuyOrder> pageInfo = new PageInfo<>(items);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public TeambuyOrder modifyStatus(String markId, String orderStatus) {
        TeambuyOrder order = teambuyOrderMapper.selectByPrimaryKey(markId);
        ShowAssert.checkNull(order, StatusCode._4014);
        // 申请退款
        boolean allowFlag = OrderStatus.PAID.equals(order.getOrderStatus())
                || OrderStatus.STOCKING.equals(order.getOrderStatus())
                || OrderStatus.REFUNDED.equals(order.getOrderStatus());
        ShowAssert.checkTrue(OrderStatus.REFUNDING.equals(orderStatus) && !allowFlag, StatusCode._4015);
        // 取消订单
        ShowAssert.checkTrue(OrderStatus.CANCELLED.equals(orderStatus) && !OrderStatus.NO_PAY.equals(order.getOrderStatus()), StatusCode._4016);
        Date time = DateUtil.date();
        if (OrderStatus.ARRIVED.equals(orderStatus)) {
            order.setArriveTime(time);
        } else if (OrderStatus.CANCELLED.equals(orderStatus)) {
            order.setCancelTime(time);
            sender.addCurrentStock(order.getOrderNo());
        } else if (OrderStatus.IN_DISTRIBUTION.equals(orderStatus)) {
            order.setSendTime(time);
        }
        order.setOrderStatus(orderStatus);
        teambuyOrderMapper.updateByPrimaryKeySelective(order);
        return getOrderById(markId);
    }

    @Override
    public void modifyStatusNo(String orderNo, String orderStatus, String userId) {
        TeambuyOrder order = teambuyOrderMapper.selectByNo(orderNo, userId);
        ShowAssert.checkNull(order, StatusCode._4014);
        // 申请退款
        boolean allowFlag = OrderStatus.PAID.equals(order.getOrderStatus())
                || OrderStatus.STOCKING.equals(order.getOrderStatus())
                || OrderStatus.REFUNDED.equals(order.getOrderStatus());
        ShowAssert.checkTrue(OrderStatus.REFUNDING.equals(orderStatus) && !allowFlag, StatusCode._4015);
        // 取消订单
        ShowAssert.checkTrue(OrderStatus.CANCELLED.equals(orderStatus) && !OrderStatus.NO_PAY.equals(order.getOrderStatus()), StatusCode._4016);
        Date time = DateUtil.date();
        if (OrderStatus.ARRIVED.equals(orderStatus)) {
            order.setArriveTime(time);
        } else if (OrderStatus.CANCELLED.equals(orderStatus)) {
            order.setCancelTime(time);
            sender.addCurrentStock(orderNo);
        }
        order.setOrderStatus(orderStatus);
        teambuyOrderMapper.updateByPrimaryKeySelective(order);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TeambuyOrder create(TeambuyModel model, TeambuyInfo teambuyInfo, BigDecimal deliveryPrice,
                            String storehouseId) {
        if (ObjectUtil.isNotNull(model.getDeliveryDate())) {
            boolean isHoliday = holidayInfoMapper.countHoliday(model.getDeliveryDate()) > 0;
            ShowAssert.checkTrue(isHoliday, StatusCode._4010);
        }
        // 活动限制
        if (teambuyInfo.getLimited() != null && teambuyInfo.getLimited() > 0) {
            int orderCount = teambuyOrderMapper.selectCountByUserId(model.getUserId());
            ShowAssert.checkTrue(orderCount > teambuyInfo.getLimited(), StatusCode._5018);
        }
        // 老带新不能参团
        if (teambuyInfo.getType() == 1) {
            int orderCount = orderInfoMapper.selectCountByUserId(model.getUserId());
            if (orderCount > 0 && !StringUtils.isEmpty(model.getGroupId())) {
                int oldOrderCount = orderInfoMapper.selectCountByUserId(model.getUserId());
                ShowAssert.checkTrue(oldOrderCount > 0, StatusCode._5018);
            }
        }
        TeambuyGroup group;
        if (StringUtils.isEmpty(model.getGroupId())) {
            group = createGroup(teambuyInfo, model.getUserId());
        } else {
            group = teambuyGroupMapper.selectByPrimaryKey(model.getGroupId());
            group.setCurrentCount(group.getCurrentCount() + 1);
            if (group.getReqCount().equals(group.getCurrentCount())) {
                group.setGroupStatus(GroupStatus.SUCCEED);
            }
            teambuyGroupMapper.updateByPrimaryKeySelective(group);
        }
        TeambuyOrder order = createOrder(model, teambuyInfo, group.getMarkId(), group.getCurrentCount(), deliveryPrice,
                storehouseId);
        addDelivery(model, order.getMarkId());
        sender.subCurrentStock(order.getOrderNo());
//        Map<String, Object> result = new HashMap<>(8);
//        result.put("orderNo", order.getOrderNo());
//        result.put("total", order.getPayAmount());
//        result.put("deliveryAmount", order.getDeliveryAmount());
        return order;
    }

    /**
     * 开团
     *
     * @param teambuyInfo
     * @param userId
     * @return
     * @date 2019年10月15日 下午12:15:25
     */
    private TeambuyGroup createGroup(TeambuyInfo teambuyInfo, String userId) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        TeambuyGroup group = TeambuyGroup.builder()
                .markId(snowflake.nextIdStr())
                .type(teambuyInfo.getType()).theme(teambuyInfo.getTheme())
                .goodsId(teambuyInfo.getGoodsId())
                .teambuyId(teambuyInfo.getMarkId()).creator(userId)
                .createTime(DateUtil.date())
                .reqCount(teambuyInfo.getReqCount()).currentCount(1)
                .vaildTime(teambuyInfo.getVaildTime()).groupStatus(GroupStatus.UNGROUP)
                .build();
        teambuyGroupMapper.insertSelective(group);
        return group;
    }

    /**
     * 添加团购订单
     *
     * @param model
     * @param teambuyInfo
     * @param groupId
     * @param sort
     * @param deliveryPrice
     * @return
     * @date 2019年10月15日 下午12:16:03
     */
    private TeambuyOrder createOrder(TeambuyModel model, TeambuyInfo teambuyInfo, String groupId, int sort,
                                     BigDecimal deliveryPrice, String storehouseId) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        TeambuyOrder order = TeambuyOrder.builder()
                .markId(snowflake.nextIdStr())
                .orderNo(ShowUtils.createOrderNo(Contacts.TYPE_OF_TEAMBUY_ORDER, 1))
                .groupId(groupId)
                .goodsId(teambuyInfo.getGoodsId())
                .goodsName(teambuyInfo.getGoodsName())
                .specificationIds(teambuyInfo.getSpecificationIds())
                .userId(model.getUserId()).quantity(1)
                .orderAmount(teambuyInfo.getPrice())
                .deliveryAmount(deliveryPrice)
                .payAmount(teambuyInfo.getPrice().add(deliveryPrice))
                .orderTime(DateUtil.date())
                .deliveryDate(model.getDeliveryDate())
                .storehouseId(storehouseId)
                .orderSource(model.getOrderSource()).sort(sort)
                .orderStatus(OrderStatus.NO_PAY).build();
        teambuyOrderMapper.insertSelective(order);
        return order;
    }

    /**
     * 添加团单配送地址
     *
     * @param model
     * @param orderId
     * @date 2019年10月15日 下午12:16:19
     */
    private void addDelivery(TeambuyModel model, String orderId) {
        UserAddress address = userAddressMapper.selectByPrimaryKey(model.getAddressId());
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        OrderDelivery delivery = OrderDelivery.builder()
                .markId(snowflake.nextIdStr())
                .orderId(orderId).contact(address.getUserName())
                .deliveryDate(model.getDeliveryDate())
                .phone(address.getPhone())
                .deliveryAddress(address.getUserAddress())
                .deliveryArea(address.getProvince() + address.getCity() + address.getArea())
                .longitude(address.getLongitude())
                .latitude(address.getLatitude())
                .remark(model.getRemark())
                .orderType(Contacts.TYPE_OF_TEAMBUY_ORDER).build();
        orderDeliveryMapper.insertSelective(delivery);
    }

    @CheckOrderCancel
    @Override
    public TeambuyOrder getOrderByNo(String orderNo) {
        TeambuyOrder order = teambuyOrderMapper.selectByNo(orderNo, null);
        ShowAssert.checkNull(order, StatusCode._4014);
        return order;
    }

    @Override
    public TeambuyOrder getOrderById(String markId) {
        TeambuyOrder order = teambuyOrderMapper.selectByPrimaryKey(markId);
        if (order != null) {
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(order.getMarkId());
            order.setOrderDelivery(delivery);
        }
        return order;
    }

    @Override
    public ExportTemplateVo getPrintOrderInfo(String markId) {
        return teambuyOrderMapper.selectOrderById(markId);
    }

    @Override
    public OrderDetail getOrderDetail(String orderNo, String userId) {
        TeambuyOrder order = teambuyOrderMapper.selectByNo(orderNo, userId);
        ShowAssert.checkNull(order, StatusCode._4004);
        TeambuyGroup group = teambuyGroupMapper.selectByPrimaryKey(order.getGroupId());
        OrderDelivery orderDelivery = orderDeliveryMapper.selectByOrderId(order.getMarkId());
        if (ObjectUtil.isNull(orderDelivery)) {
            orderDelivery = new OrderDelivery();
        }
        List<OrderItemDetail> itemList = new LinkedList<>();
        String status = GroupStatus.SUCCEED.equals(group.getGroupStatus()) && OrderStatus.PAID.equals(order.getOrderStatus()) ? group.getStatusDesc()
                : order.getStatusDesc();
        OrderDetail orderDetail = OrderDetail.builder()
                .orderId(order.getMarkId())
                .orderNo(order.getOrderNo()).orderTime(order.getOrderTime())
                .expireTime(new Date(order.getOrderTime().getTime() + Contacts.ORDER_EXPIRED_TIME))
                .deliveryAmount(order.getDeliveryAmount())
                .payAmount(order.getPayAmount())
                .deliveryDate(order.getDeliveryDate())
                .orderStatus(order.getOrderStatus()).statusDesc(status)
                .contact(orderDelivery.getContact())
                .phone(orderDelivery.getPhone())
                .address(orderDelivery.getDeliveryArea() + orderDelivery.getDeliveryAddress())
                .remark(orderDelivery.getRemark()).type(Contacts.TYPE_OF_TEAMBUY_ORDER).build();
        OrderItemDetail itemDetail = OrderItemDetail.builder()
                .productId(order.getGoodsId())
                .productName(order.getGoodsName()).productType(0)
                .specificationIds(order.getSpecificationIds())
                .specs(order.getSpecs())
                .salePrice(order.getPayAmount())
                .payAmount(order.getPayAmount())
                .quantity(order.getQuantity()).build();
        itemList.add(itemDetail);
        orderDetail.setItems(itemList);
        return orderDetail;
    }

    @Override
    public List<Judge> listItemJudge(String orderNo, String userId) {
        List<Judge> judgeList = new LinkedList<>();
        TeambuyOrder order = teambuyOrderMapper.selectByNo(orderNo, userId);
        ShowAssert.checkNull(order, StatusCode._4014);
        Judge judge = Judge.builder()
                .productId(order.getGoodsId())
                .specificationIds(order.getSpecificationIds())
                .orderNo(order.getOrderNo()).productName(order.getGoodsName())
                .productType(0).build();
        judgeList.add(judge);
        return judgeList;
    }
}
