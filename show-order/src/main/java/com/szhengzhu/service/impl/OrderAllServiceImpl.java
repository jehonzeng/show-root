package com.szhengzhu.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckOrderCancel;
import com.szhengzhu.bean.excel.OrderSendModel;
import com.szhengzhu.bean.excel.ProductModel;
import com.szhengzhu.bean.vo.TodayProductVo;
import com.szhengzhu.bean.wechat.vo.OrderBase;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.OrderAllMapper;
import com.szhengzhu.mapper.OrderItemMapper;
import com.szhengzhu.mapper.SeckillOrderMapper;
import com.szhengzhu.mapper.TeambuyOrderMapper;
import com.szhengzhu.service.OrderAllService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Service("orderAllService")
public class OrderAllServiceImpl implements OrderAllService {
    
    @Resource
    private OrderAllMapper orderAllMapper;
    
    @Resource
    private TeambuyOrderMapper teambuyOrderMapper;

    @Resource
    private SeckillOrderMapper seckillOrderMapper;
    
    @Resource
    private OrderItemMapper orderItemMapper;

    @CheckOrderCancel
    @Override
    public PageGrid<OrderBase> listAll(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageMethod.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageMethod.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderAllMapper.selectAll(orderPage.getData());
        setItemImg(orderList);
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderList);
        return new PageGrid<>(pageInfo);
    }

    private void setItemImg(List<OrderBase> orderList) {
        for (OrderBase orderBase : orderList) {
            String type = orderBase.getType();
            String orderId = orderBase.getOrderId();
            List<String> imgList = getItemImg(orderId, type);
            orderBase.setImagePath(imgList);
        }
    }

    private List<String> getItemImg(String orderId, String type) {
        List<String> imgList = new ArrayList<>();
        if (Contacts.TYPE_OF_ORDER.equals(type)) {
            imgList = orderItemMapper.selectItemImg(orderId);
        } else if (Contacts.TYPE_OF_TEAMBUY_ORDER.equals(type)) {
            imgList = teambuyOrderMapper.selectItemImg(orderId);
        } else if (Contacts.TYPE_OF_SECKILL_ORDER.equals(type)) {
            imgList = seckillOrderMapper.selectItemImg(orderId);
        }
        return imgList;
    }

    @CheckOrderCancel
    @Override
    public PageGrid<OrderBase> listUnpaid(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageMethod.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageMethod.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderAllMapper.selectUnpaid(orderPage.getData());
        getItemImg(orderList);
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderList);
        return new PageGrid<>(pageInfo);
    }

    private void getItemImg(List<OrderBase> orderList) {
        for (OrderBase orderBase : orderList) {
            List<String> imgList = orderItemMapper.selectItemImg(orderBase.getOrderId());
            orderBase.setImagePath(imgList);
        }
    }

    @CheckOrderCancel
    @Override
    public PageGrid<OrderBase> listUngroup(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageMethod.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageMethod.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderAllMapper.selectUngroup(orderPage.getData());
        getItemImg(orderList);
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderList);
        return new PageGrid<>(pageInfo);
    }

    @CheckOrderCancel
    @Override
    public PageGrid<OrderBase> listUndelivery(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageMethod.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageMethod.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderAllMapper.selectUndelivery(orderPage.getData());
        setItemImg(orderList);
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderList);
        return new PageGrid<>(pageInfo);
    }

    @CheckOrderCancel
    @Override
    public PageGrid<OrderBase> listUnReceive(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageMethod.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageMethod.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderAllMapper.selectUnReceive(orderPage.getData());
        setItemImg(orderList);
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderList);
        return new PageGrid<>(pageInfo);
    }

    @CheckOrderCancel
    @Override
    public PageGrid<OrderBase> listUnjudge(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageMethod.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageMethod.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderAllMapper.selectUnjudge(orderPage.getData());
        setItemImg(orderList);
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderList);
        return new PageGrid<>(pageInfo);
    }
    
    @Override
    public List<TodayProductVo> getTodayList() {
        return orderAllMapper.selectTodayItemList();
    }
    
    @Override
    public List<Map<String, Object>> getStatusCount(String userId) {
        return orderAllMapper.selectStatusCount(userId);
    }

    @Override
    public PageGrid<OrderSendModel> pageSendInfo(PageParam<String> page) {
        PageMethod.startPage(page.getPageIndex(), page.getPageSize());
        PageMethod.orderBy(page.getSidx() + " " + page.getSort());
        List<OrderSendModel> list = orderAllMapper.selectSendInfo();
        PageInfo<OrderSendModel> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public List<OrderSendModel> listSendInfo() {
        return orderAllMapper.selectSendInfo();
    }

    @Override
    public PageGrid<ProductModel> pageTodayProductQuantity(PageParam<?> pageParam) {
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy("SUM(quantity) " + pageParam.getSort());
        List<ProductModel> list = orderAllMapper.selectTodayProductQuantity();
        PageInfo<ProductModel> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }
    
    @Override
    public List<ProductModel> listTodayProductQuantity() {
        return orderAllMapper.selectTodayProductQuantity();
    }
}
