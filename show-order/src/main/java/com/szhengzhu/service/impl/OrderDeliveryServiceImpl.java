package com.szhengzhu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.excel.LogisticsModel;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.service.OrderDeliveryService;
import com.szhengzhu.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Service("orderDeliveryService")
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

    @Resource
    private OrderDeliveryMapper orderDeliveryMapper;

    @Resource
    private HolidayInfoMapper holidayInfoMapper;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private TeambuyOrderMapper teamItemMapper;

    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    @Override
    public PageGrid<OrderDelivery> pageDelivery(PageParam<OrderDelivery> deliveryPage) {
        PageMethod.startPage(deliveryPage.getPageIndex(), deliveryPage.getPageSize());
        PageMethod.orderBy(deliveryPage.getSidx() + " " + deliveryPage.getSort());
        List<OrderDelivery> deliveryList = orderDeliveryMapper.selectByExampleSelective(deliveryPage.getData());
        PageInfo<OrderDelivery> pageInfo = new PageInfo<>(deliveryList);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public OrderDelivery getDeliveryByOrderId(String orderId) {
        return orderDeliveryMapper.selectByOrderId(orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyDelivery(OrderDelivery orderDelivery) {
        if (ObjectUtil.isNotNull(orderDelivery.getDeliveryDate())) {
            boolean isHoliday = holidayInfoMapper.countHoliday(orderDelivery.getDeliveryDate()) > 0;
            ShowAssert.checkTrue(isHoliday, StatusCode._4010);
            String orderType = orderDelivery.getOrderType();
            if (Contacts.TYPE_OF_ORDER.equals(orderType)) {
                orderInfoMapper.updateDeliveryDate(orderDelivery.getOrderId(), orderDelivery.getDeliveryDate());
            } else if (Contacts.TYPE_OF_TEAMBUY_ORDER.equals(orderType)) {
                teamItemMapper.updateDeliveryDate(orderDelivery.getOrderId(), orderDelivery.getDeliveryDate());
            } else if (Contacts.TYPE_OF_SECKILL_ORDER.equals(orderType)) {
                seckillOrderMapper.updateDeliveryDate(orderDelivery.getOrderId(), orderDelivery.getDeliveryDate());
            }
        }
        orderDeliveryMapper.updateByPrimaryKeySelective(orderDelivery);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> batchModifyDeliveryOrder(List<LogisticsModel> deliveryList) {
        // 获取验证的订单号
        List<String> orderNos = orderInfoMapper.selectOrderNoByStatus("OT03");
        Map<String, Object> result = new HashMap<>(2);
        List<LogisticsModel> resultList = new ArrayList<>();
        // 反馈标识（0：有 1：无）
        int status = 1;
        for (LogisticsModel logisticsModel : deliveryList) {
            // 验证订单号和物流信息
            if (orderNos.contains(logisticsModel.getOrderNo()) && !StringUtils.isEmpty(logisticsModel.getTrackNo())
                    && !StringUtils.isEmpty(logisticsModel.getCompanyNo())) {
                orderDeliveryMapper.updateDeliveryByOrderId(logisticsModel);
                orderInfoMapper.updateStatusByOrderNo(logisticsModel.getOrderNo());
            } else {
                logisticsModel.setFeedback("该订单号或物流信息有误！");
                resultList.add(logisticsModel);
            }
        }
        // 问题列表标记
        if (!resultList.isEmpty()) {
            status = 0;
        }
        result.put("list", resultList);
        result.put("status", status);
        return result;
    }
}
