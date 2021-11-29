package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckOrderCancel;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.order.*;
import com.szhengzhu.bean.rpt.IndexDisplay;
import com.szhengzhu.bean.vo.OrderBatch;
import com.szhengzhu.bean.vo.OrderExportVo;
import com.szhengzhu.bean.wechat.vo.*;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.service.OrderService;
import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Jehon Zeng
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderDeliveryMapper orderDeliveryMapper;

    @Resource
    private UserAddressMapper userAddressMapper;

    @Resource
    private HolidayInfoMapper holidayInfoMapper;

    @Resource
    private UserVoucherMapper userVoucherMapper;

    @Resource
    private UserCouponMapper userCouponMapper;

    @Resource
    private OrderRecordMapper orderRecordMapper;

    @Resource
    private Sender sender;

    @CheckOrderCancel
    @Override
    public PageGrid<OrderInfo> pageOrder(PageParam<OrderInfo> orderPage) {
        PageMethod.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageMethod.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderInfo> orderList = orderInfoMapper.selectByExampleSelective(orderPage.getData());
        searchOrderDetailAndDelivery(orderList);
        PageInfo<OrderInfo> pageInfo = new PageInfo<>(orderList);
        return new PageGrid<>(pageInfo);
    }

    private void searchOrderDetailAndDelivery(List<OrderInfo> orderList) {
        for (OrderInfo orderinfo : orderList) {
            String orderId = orderinfo.getMarkId();
            String orderNo = orderinfo.getOrderNo();
            String reason = orderRecordMapper.selectOrderRecord(orderNo);
            List<OrderItem> items = orderItemMapper.selectByOrderId(orderId);
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(orderId);
            orderinfo.setReason(reason);
            orderinfo.setItems(items);
            orderinfo.setOrderDelivery(delivery);
        }
    }

    @Override
    public PageGrid<OrderInfo> pageShareOrder(PageParam<OrderInfo> orderPage) {
        PageMethod.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageMethod.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderInfo> orderList = orderInfoMapper.selectShareOrderByExampleSelective(orderPage.getData());
        searchOrderDetailAndDelivery(orderList);
        PageInfo<OrderInfo> pageInfo = new PageInfo<>(orderList);
        return new PageGrid<>(pageInfo);
    }

    @CheckOrderCancel
    @Override
    public OrderInfo getOrderDeliveryItem(String markId) {
        OrderInfo orderItem = orderInfoMapper.selectByPrimaryKey(markId);
        if (ObjectUtil.isNotNull(orderItem)) {
            List<OrderItem> items = orderItemMapper.selectByOrderId(orderItem.getMarkId());
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(orderItem.getMarkId());
            orderItem.setItems(items);
            orderItem.setOrderDelivery(delivery);
        }
        return orderItem;
    }

    @CheckOrderCancel
    @Override
    public OrderInfo getOrderById(String orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        ShowAssert.checkNull(orderInfo, StatusCode._4014);
        return orderInfo;
    }

    @CheckOrderCancel
    @Override
    public OrderInfo getOrderByNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.selectByNo(orderNo, null);
        ShowAssert.checkNull(orderInfo, StatusCode._4014);
        if (ObjectUtil.isNotNull(orderInfo)) {
            List<OrderItem> items = orderItemMapper.selectByOrderId(orderInfo.getMarkId());
            orderInfo.setItems(items);
        }
        return orderInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addBackendOrder(CalcData calcData, OrderDelivery orderDelivery, int orderType,
                                  BigDecimal deliveryAmount) {
        BigDecimal total = calcData.getTotal();
        deliveryAmount = ObjectUtil.isNull(deliveryAmount) ? BigDecimal.ZERO : calcData.getDeliveryAmount();
        OrderModel orderModel = calcData.getOrderModel();
        orderDelivery.setDeliveryDate(orderModel.getDeliveryDate());
        OrderInfo order = createBackInfo(total, deliveryAmount, orderModel, orderType);
        createItem(orderModel.getItem(), orderModel.getVoucher(), order);
        createDelivery(order, orderDelivery);
        // 商品系统减库存
        sender.subCurrentStock(order.getOrderNo());
        // 商品系统减真实库存
        sender.subTotalStock(order.getOrderNo());
        return order.getMarkId();
    }

    private OrderInfo createBackInfo(BigDecimal total, BigDecimal deliveryAmount,
                                     OrderModel orderModel, int orderType) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        OrderInfo orderInfo = OrderInfo.builder().markId(snowflake.nextIdStr()).orderNo(ShowUtils.createOrderNo(Contacts.TYPE_OF_ORDER, 0))
                .userId(orderModel.getUserId()).orderAmount(total.subtract(deliveryAmount)).deliveryAmount(deliveryAmount).payAmount(BigDecimal.ZERO)
                .orderTime(DateUtil.date()).orderType(orderType).deliveryDate(orderModel.getDeliveryDate()).remark(orderModel.getRemark())
                .orderStatus(OrderStatus.PAID).orderSource("0").build();
        orderInfoMapper.insertSelective(orderInfo);
        return orderInfo;
    }

    /**
     * 后台添加或修改用户订单
     *
     * @param order
     * @param orderDelivery
     * @date 2019年7月31日 下午3:25:18
     */
    private void createDelivery(OrderInfo order, OrderDelivery orderDelivery) {
        if (!StrUtil.isEmpty(orderDelivery.getMarkId())) {
            orderDeliveryMapper.updateByPrimaryKey(orderDelivery);
            return;
        }
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        orderDelivery.setMarkId(snowflake.nextIdStr());
        orderDelivery.setOrderId(order.getMarkId());
        orderDelivery.setRemark(order.getRemark());
        orderDelivery.setOrderType(Contacts.TYPE_OF_ORDER);
        orderDelivery.setDeliveryDate(order.getDeliveryDate());
        orderDelivery.setAddTime(order.getOrderTime());
        orderDeliveryMapper.insert(orderDelivery);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyBackendOrder(OrderInfo order) {
        if (ObjectUtil.isNotNull(order.getDeliveryDate())) {
            boolean isHoliday = holidayInfoMapper.countHoliday(order.getDeliveryDate()) > 0;
            ShowAssert.checkTrue(isHoliday, StatusCode._4010);
        }
        OrderDelivery orderDelivery = order.getOrderDelivery();
        orderDelivery.setDeliveryDate(order.getDeliveryDate());
        orderInfoMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public List<OrderItem> listItemByOrderId(String orderId) {
        return orderItemMapper.selectByOrderId(orderId);
    }

    @CheckOrderCancel
    @Override
    public PageGrid<OrderItem> pageItem(PageParam<OrderItem> itemPage) {
        PageMethod.startPage(itemPage.getPageIndex(), itemPage.getPageSize());
        PageMethod.orderBy(itemPage.getSidx() + " " + itemPage.getSort());
        PageInfo<OrderItem> pageInfo = new PageInfo<>(orderItemMapper.selectByExampleSelective(itemPage.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public OrderInfo modifyStatus(String orderId, String orderStatus) {
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        ShowAssert.checkNull(orderInfo, StatusCode._4014);
        // 申请退款
        boolean allowFlag = OrderStatus.PAID.equals(orderInfo.getOrderStatus())
                || OrderStatus.STOCKING.equals(orderInfo.getOrderStatus())
                || OrderStatus.REFUNDED.equals(orderInfo.getOrderStatus());
        ShowAssert.checkTrue(OrderStatus.REFUNDING.equals(orderStatus) && !allowFlag, StatusCode._4015);
        // 取消订单
        ShowAssert.checkTrue(OrderStatus.CANCELLED.equals(orderStatus) && !OrderStatus.NO_PAY.equals(orderInfo.getOrderStatus()), StatusCode._4016);
        Date time = DateUtil.date();
        if (OrderStatus.ARRIVED.equals(orderStatus)) {
            orderInfo.setArriveTime(time);
        } else if (OrderStatus.CANCELLED.equals(orderStatus)) {
            // 用户手动取消订单
            orderInfo.setCancelTime(time);
            modifyStock(orderInfo.getOrderNo(), orderInfo.getMarkId());
            if (!StringUtils.isEmpty(orderInfo.getCouponId())) {
                userCouponMapper.backOrderCoupon(orderInfo.getCouponId());
            }
        } else if (OrderStatus.IN_DISTRIBUTION.equals(orderStatus)) {
            orderInfo.setSendTime(time);
        }
        orderInfo.setOrderStatus(orderStatus);
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        return getOrderDeliveryItem(orderId);
    }

    /**
     * 取消订单，修改商品库存
     *
     * @param orderNo
     * @param orderId
     * @date 2019年10月21日 上午11:22:40
     */
    private void modifyStock(String orderNo, String orderId) {
        sender.addCurrentStock(orderNo);
        List<OrderItem> itemList = orderItemMapper.selectByOrderId(orderId);
        itemList.forEach(item -> {
            if (!StringUtils.isEmpty(item.getVoucherIds())) {
                userVoucherMapper.cancelOrder(item.getVoucherIds());
            }
        });
    }

    @Override
    public void modifyStatusByNo(String orderNo, String orderStatus, String userId) {
        OrderInfo orderInfo = orderInfoMapper.selectByNo(orderNo, userId);
        ShowAssert.checkNull(orderInfo, StatusCode._4014);
        // 申请退款
        boolean allowFlag = OrderStatus.PAID.equals(orderInfo.getOrderStatus())
                || OrderStatus.STOCKING.equals(orderInfo.getOrderStatus())
                || OrderStatus.REFUNDED.equals(orderInfo.getOrderStatus());
        ShowAssert.checkTrue(OrderStatus.REFUNDING.equals(orderStatus) && !allowFlag, StatusCode._4015);
        // 取消订单
        ShowAssert.checkTrue(OrderStatus.CANCELLED.equals(orderStatus) && !OrderStatus.NO_PAY.equals(orderInfo.getOrderStatus()), StatusCode._4016);
        Date time = DateUtil.date();
        if (OrderStatus.ARRIVED.equals(orderStatus)) {
            orderInfo.setArriveTime(time);
        } else if (OrderStatus.CANCELLED.equals(orderStatus)) {
            // 用户手动取消订单
            orderInfo.setCancelTime(time);
            modifyStock(orderNo, orderInfo.getMarkId());
        }
        orderInfo.setOrderStatus(orderStatus);
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

    @CheckOrderCancel
    @Override
    public OrderDetail getOrderDetail(String orderNo, String userId) {
        OrderDetail orderDetail = orderInfoMapper.selectOrderDetail(orderNo, userId);
        ShowAssert.checkTrue(ObjectUtil.isNull(orderDetail) || StrUtil.isEmpty(orderDetail.getOrderId()), StatusCode._4014);
        orderDetail.setExpireTime(new Date(orderDetail.getOrderTime().getTime() + Contacts.ORDER_EXPIRED_TIME));
        List<OrderItemDetail> itemDetails = orderItemMapper.selectItemDetail(orderDetail.getOrderId());
        orderDetail.setItems(itemDetails);
        return orderDetail;
    }

    @Override
    public List<Judge> listItemJudge(String orderNo, String userId) {
        return orderItemMapper.selectItemJudge(orderNo, userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> create(CalcData calcData) {
        Map<String, Object> result = new HashMap<>(8);
        BigDecimal total = calcData.getTotal();
        BigDecimal discount = calcData.getDiscount();
        BigDecimal codeDiscount = ObjectUtil.isNull(calcData.getCodeDiscount()) ? BigDecimal.ZERO : calcData.getCodeDiscount();
        BigDecimal deliveryAmount = ObjectUtil.isNull(calcData.getDeliveryAmount()) ? BigDecimal.ZERO
                : calcData.getDeliveryAmount();
        OrderModel orderModel = calcData.getOrderModel();
        OrderInfo order = createInfo(total, codeDiscount, deliveryAmount, orderModel, calcData.getManagerId());
        operateItem(orderModel, order);
        if (!StringUtils.isEmpty(orderModel.getCouponId())) {
            userCouponMapper.useCoupon(orderModel.getCouponId(), order.getOrderTime());
        }
        result.put("orderNo", order.getOrderNo());
        result.put("orderModel", orderModel);
        result.put("total", total);
        result.put("discount", discount);
        result.put("codeDiscount", codeDiscount);
        result.put("deliveryAmount", deliveryAmount);
        // 商品系统减库存
        sender.subCurrentStock(order.getOrderNo());
        return result;
    }

    /**
     * 操作商品和库存
     *
     * @param orderModel
     * @param order
     * @date 2019年7月22日 下午5:57:59
     */
    private void operateItem(OrderModel orderModel, OrderInfo order) {
        createItem(orderModel.getItem(), orderModel.getVoucher(), order);
        createAccessory(orderModel.getAccessory(), order.getMarkId());
        createIncrease(orderModel.getIncrease(), order.getMarkId());
        createDelivery(order, orderModel.getAddressId());
    }

    /**
     * 添加订单信息
     *
     * @param total
     * @param deliveryAmount
     * @param orderModel
     * @return
     * @date 2019年7月22日 下午5:56:52
     */
    private OrderInfo createInfo(BigDecimal total, BigDecimal codeDiscount, BigDecimal deliveryAmount,
                                 OrderModel orderModel, String managerId) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        OrderInfo orderInfo = OrderInfo.builder()
                .markId(snowflake.nextIdStr())
                .orderNo(ShowUtils.createOrderNo(Contacts.TYPE_OF_ORDER, 1))
                .userId(orderModel.getUserId())
                .orderAmount(total.subtract(deliveryAmount).add(codeDiscount))
                .deliveryAmount(deliveryAmount).payAmount(total)
                .orderTime(DateUtil.date()).orderType(1)
                .deliveryDate(orderModel.getDeliveryDate())
                .remark(orderModel.getRemark()).orderStatus(OrderStatus.NO_PAY)
                .couponId(orderModel.getCouponId())
                .orderSource(orderModel.getOrderSource())
                .managerId(managerId).build();
        orderInfoMapper.insertSelective(orderInfo);
        return orderInfo;
    }

    /**
     * 添加商品列表
     *
     * @param itemModelList
     * @param voucherList
     * @param order
     * @date 2019年7月22日 下午5:54:44
     */
    private void createItem(List<OrderItemModel> itemModelList, List<VoucherModel> voucherList, OrderInfo order) {
        List<OrderItem> itemList = new LinkedList<>();
        Map<String, Integer> goodsVoucherMap = new HashMap<>(16);
        for (OrderItemModel itemModel : itemModelList) {
            if (itemModel.getStatus() > 0) {
                continue;
            }
            // 添加菜品券记录
            if (itemModel.getProductType() == 1) {
                goodsVoucherMap.put(itemModel.getProductId(), itemModel.getQuantity());
            }
            BigDecimal payAmount = itemModel.getSalePrice().multiply(
                    new BigDecimal(itemModel.getQuantity() - itemModel.getUseVoucherCount()));
            // 组装orderitem并添加到列表
            addOrderItem(payAmount, order, itemModel, voucherList, itemList);
        }
        if (!itemList.isEmpty()) {
            orderItemMapper.insertBatch(itemList);
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        if (!goodsVoucherMap.isEmpty()) {
            createUserVoucher(goodsVoucherMap, order);
        }
    }

    /**
     * 组装orderitem并添加到列表
     *
     * @param payAmount
     * @param order
     * @param itemModel
     * @param voucherList
     * @param itemList
     */
    private void addOrderItem(BigDecimal payAmount, OrderInfo order, OrderItemModel itemModel, List<VoucherModel> voucherList, List<OrderItem> itemList) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        OrderItem orderItem = OrderItem.builder()
                .markId(snowflake.nextIdStr()).orderId(order.getMarkId())
                .productId(itemModel.getProductId()).productType(itemModel.getProductType())
                .productName(itemModel.getProductName()).storehouseId(itemModel.getStorehouseId())
                .specificationIds(itemModel.getSpecificationIds()).quantity(itemModel.getQuantity())
                .basePrice(itemModel.getBasePrice()).salePrice(itemModel.getSalePrice())
                .payAmount(payAmount).build();
        if (itemModel.getUseVoucherCount() > 0) {
            String voucherIds = useVoucher(voucherList, itemModel, order.getOrderTime());
            if (orderItem.getQuantity() <= itemModel.getUseVoucherCount()) {
                orderItem.setVoucherIds(voucherIds);
            }
            orderItem.setQuantity(itemModel.getQuantity() - itemModel.getUseVoucherCount());
            try {
                OrderItem exchange = orderItem.clone();
                exchange.setMarkId(snowflake.nextIdStr());
                exchange.setVoucherIds(voucherIds);
                exchange.setQuantity(itemModel.getUseVoucherCount());
                exchange.setVoucherIds(voucherIds);
                exchange.setPayAmount(BigDecimal.ZERO);
                exchange.setVoucherIds(voucherIds);
                itemList.add(exchange);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        itemList.add(orderItem);
    }

    /**
     * 添加菜品券记录
     *
     * @param goodsVoucherMap
     * @param order
     * @date 2019年7月22日 下午5:54:19
     */
    private void createUserVoucher(Map<String, Integer> goodsVoucherMap, OrderInfo order) {
        List<UserVoucher> voucherList = new LinkedList<>();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        UserVoucher voucher;
        Iterator<Map.Entry<String, Integer>> iterator = goodsVoucherMap.entrySet().iterator();
        for (Iterator<Map.Entry<String, Integer>> it = iterator; it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            String voucherId = entry.getKey();
            int quantity = entry.getValue();
            GoodsVoucher goodsVoucher = userVoucherMapper.selectGoodsVoucher(voucherId);
            for (int i = 1; i <= quantity; i++) {
                voucher = UserVoucher.builder()
                        .markId(snowflake.nextIdStr()).userId(order.getUserId())
                        .voucherId(voucherId).voucherName(goodsVoucher.getVoucherName())
                        .productType(goodsVoucher.getProductType()).productId(goodsVoucher.getProductId())
                        .specificationIds(goodsVoucher.getSpecificationIds())
                        .orderType(0).orderNo(order.getOrderNo()).createTime(order.getOrderTime())
                        .quantity(1).build();
                voucherList.add(voucher);
            }
        }
        if (!voucherList.isEmpty()) {
            userVoucherMapper.insertBatch(voucherList);
        }
    }

    /**
     * 商品使用菜品券
     *
     * @param voucherList
     * @param itemModel
     * @param orderTime
     * @return
     * @date 2019年7月22日 下午5:53:59
     */
    private String useVoucher(List<VoucherModel> voucherList, OrderItemModel itemModel, Date orderTime) {
        UserVoucher voucher;
        StringBuilder voucherIds = new StringBuilder();
        for (VoucherModel voucherModel : voucherList) {
            if (voucherModel.getStatus() == -1) {
                continue;
            }
            voucher = userVoucherMapper.selectByPrimaryKey(voucherModel.getVoucherId());
            if (itemModel.getProductId().equals(voucher.getProductId())
                    && itemModel.getSpecificationIds().equals(voucher.getSpecificationIds())) {
                if (voucherIds.length() == 0) {
                    voucherIds.append(voucher.getMarkId());
                } else {
                    voucherIds.append("," + voucher.getMarkId());
                }
                voucher.setUseTime(orderTime);
                userVoucherMapper.updateByPrimaryKeySelective(voucher);
            }
        }
        return voucherIds.toString();
    }

    /**
     * 添加附属品
     *
     * @param accessoryList
     * @param orderId
     * @return
     * @date 2019年7月22日 下午5:53:40
     */
    private void createAccessory(List<AccessoryModel> accessoryList, String orderId) {
        List<OrderItem> itemList = new LinkedList<>();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        OrderItem orderItem;
        for (AccessoryModel accessory : accessoryList) {
            if (accessory.getStatus() > 0) {
                continue;
            }
            BigDecimal payAmount = accessory.getSalePrice().multiply(new BigDecimal(accessory.getQuantity()));
            orderItem = OrderItem.builder()
                    .markId(snowflake.nextIdStr()).orderId(orderId)
                    .productType(3).productId(accessory.getAccessoryId()).productName(accessory.getName()).quantity(accessory.getQuantity())
                    .basePrice(accessory.getSalePrice()).salePrice(accessory.getSalePrice())
                    .payAmount(payAmount).build();
            itemList.add(orderItem);
        }
        if (!itemList.isEmpty()) {
            orderItemMapper.insertBatch(itemList);
        }
    }

    /**
     * 添加加价购商品
     *
     * @param increaseList
     * @param orderId
     * @return
     * @date 2019年7月22日 下午5:53:06
     */
    private void createIncrease(List<IncreaseModel> increaseList, String orderId) {
        List<OrderItem> itemList = new LinkedList<>();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        OrderItem orderItem;
        String specIds;
        for (IncreaseModel increase : increaseList) {
            if (increase.getStatus() > 0) {
                continue;
            }
            specIds = orderItemMapper.selectDefaultByGoods(increase.getGoodsId());
            BigDecimal payAmount = increase.getSalePrice().multiply(new BigDecimal(1));
            orderItem = OrderItem.builder()
                    .markId(snowflake.nextIdStr())
                    .increaseId(increase.getIncreaseId()).orderId(orderId)
                    .productType(0).productId(increase.getGoodsId()).specificationIds(specIds).productName(increase.getGoodsName())
                    .storehouseId(increase.getStorehouseId()).quantity(1)
                    .basePrice(increase.getBasePrice()).salePrice(increase.getSalePrice())
                    .payAmount(payAmount).build();
            itemList.add(orderItem);
        }
        if (!itemList.isEmpty()) {
            orderItemMapper.insertBatch(itemList);
        }
    }

    /**
     * 添加配送信息
     *
     * @param order
     * @param addressId
     * @date 2019年7月22日 下午5:21:45
     */
    private void createDelivery(OrderInfo order, String addressId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        OrderDelivery orderDelivery = OrderDelivery.builder()
                .markId(snowflake.nextIdStr()).orderId(order.getMarkId())
                .contact(userAddress.getUserName()).phone(userAddress.getPhone()).deliveryDate(order.getDeliveryDate())
                .deliveryAddress(userAddress.getUserAddress()).deliveryArea(userAddress.getDisplayValue())
                .province(userAddress.getProvince()).city(userAddress.getCity()).area(userAddress.getArea())
                .remark(order.getRemark())
                .orderType(Contacts.TYPE_OF_ORDER).longitude(userAddress.getLongitude()).latitude(userAddress.getLatitude())
                .deliveryType(null)
                .addTime(order.getOrderTime()).build();
        orderDeliveryMapper.insertSelective(orderDelivery);
    }

    @CheckOrderCancel
    @Override
    public List<OrderInfo> listStatusOrder(String orderStatus) {
        List<OrderInfo> orderList = orderInfoMapper.selectStatusOrder(orderStatus);
        for (OrderInfo orderInfo : orderList) {
            String orderId = orderInfo.getMarkId();
            List<OrderItem> items = orderItemMapper.selectByOrderId(orderId);
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(orderId);
            orderInfo.setItems(items);
            orderInfo.setOrderDelivery(delivery);
        }
        return orderList;
    }

    @Override
    public OrderExportVo getExportOrdersById(String markId) {
        OrderExportVo base = orderDeliveryMapper.selectOrdersById(markId);
        List<OrderItem> orders = orderItemMapper.selectByOrderId(markId);
        base.setOrders(orders);
        return base;
    }

    @Override
    public List<IndexDisplay> getIndexStatusCount() {
        return orderInfoMapper.selectIndexStatusCount();
    }

    @Override
    public List<OrderInfo> batchUpdateStatus(OrderBatch base) {
        List<OrderInfo> list = orderInfoMapper.selectChooseOrder(base.getIds());
        list.forEach(orderInfo -> {
            if (OrderStatus.PAID.equals(orderInfo.getOrderStatus())) {
                orderInfo.setOrderStatus(base.getOrderStatus());
            }
        });
        if (!list.isEmpty()) {
            orderInfoMapper.batchUpdateStatus(list);
        }
        return list;
    }
}
