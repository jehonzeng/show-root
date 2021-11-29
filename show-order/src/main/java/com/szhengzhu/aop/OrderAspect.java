package com.szhengzhu.aop;

import java.util.List;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.mapper.OrderInfoMapper;
import com.szhengzhu.mapper.OrderItemMapper;
import com.szhengzhu.mapper.SeckillOrderMapper;
import com.szhengzhu.mapper.TeambuyOrderMapper;
import com.szhengzhu.mapper.UserCouponMapper;
import com.szhengzhu.mapper.UserVoucherMapper;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.util.StringUtils;

/**
 * 订单异步操作类
 * 
 * @author Jehon Zeng
 */
@Aspect
@Component
public class OrderAspect {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private UserVoucherMapper userVoucherMapper;

    @Resource
    private TeambuyOrderMapper teambuyOrderMapper;

    @Resource
    private SeckillOrderMapper seckillOrderMapper;
    
    @Resource
    private UserCouponMapper userCouponMapper;

    @Resource
    private Sender sender;
    
    @Before(value = "@annotation(com.szhengzhu.annotation.CheckOrderCancel) && within (com.szhengzhu.service.impl.OrderAllServiceImpl)")
    public void orderAllCancel() {
        List<OrderInfo> orderList = orderInfoMapper.selectExpiredOrder();
        for (OrderInfo order : orderList) {
            orderInfoMapper.cancelExpiredOrderById(order.getMarkId());
            if (!StringUtils.isEmpty(order.getCouponId())) {
                userCouponMapper.backOrderCoupon(order.getCouponId());
            }
            List<OrderItem> itemList = orderItemMapper.selectByOrderId(order.getMarkId());
            sender.addCurrentStock(order.getOrderNo());
            for (OrderItem item : itemList) {
                if (StringUtils.isEmpty(item.getVoucherIds())) {
                    userVoucherMapper.cancelOrder(item.getVoucherIds());
                }
            }
        }
        teambuyOrderCancel();
        seckillOrderCancel();
    }

    @Before(value = "@annotation(com.szhengzhu.annotation.CheckOrderCancel) && within (com.szhengzhu.service.impl.OrderServiceImpl)")
    public void orderCancel() {
        List<OrderInfo> orderList = orderInfoMapper.selectExpiredOrder();
        for (OrderInfo order : orderList) {
            orderInfoMapper.cancelExpiredOrderById(order.getMarkId());
            if (!StringUtils.isEmpty(order.getCouponId())) {
                userCouponMapper.backOrderCoupon(order.getCouponId());
            }
            List<OrderItem> itemList = orderItemMapper.selectByOrderId(order.getMarkId());
            sender.addCurrentStock(order.getOrderNo());
            for (OrderItem item : itemList) {
                if (StringUtils.isEmpty(item.getVoucherIds())) {
                    userVoucherMapper.cancelOrder(item.getVoucherIds());
                }
            }
        }
    }

    @Before(value = "@annotation(com.szhengzhu.annotation.CheckOrderCancel) && within (com.szhengzhu.service.impl.TeambuyServiceImpl)")
    public void teambuyOrderCancel() {
        List<TeambuyOrder> orderList = teambuyOrderMapper.selectExpiredOrder();
        for (TeambuyOrder order : orderList) {
            sender.addCurrentStock(order.getOrderNo());
        }
    }

    @Before("@annotation(com.szhengzhu.annotation.CheckOrderCancel) && within (com.szhengzhu.service.impl.SeckillServiceImpl)")
    public void seckillOrderCancel() {
        List<SeckillOrder> orderList = seckillOrderMapper.selectExpiredOrder();
        for (SeckillOrder order : orderList) {
            sender.addCurrentStock(order.getOrderNo());
        }
    }
}
