package com.szhengzhu.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.order.OrderError;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.order.UserVoucher;
import com.szhengzhu.bean.vo.CalcData;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.bean.wechat.vo.AccessoryModel;
import com.szhengzhu.bean.wechat.vo.IncreaseModel;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderBase;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.bean.wechat.vo.OrderItemDetail;
import com.szhengzhu.bean.wechat.vo.OrderItemModel;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.bean.wechat.vo.VoucherModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.HolidayInfoMapper;
import com.szhengzhu.mapper.OrderDeliveryMapper;
import com.szhengzhu.mapper.OrderErrorMapper;
import com.szhengzhu.mapper.OrderInfoMapper;
import com.szhengzhu.mapper.OrderItemMapper;
import com.szhengzhu.mapper.SeckillOrderMapper;
import com.szhengzhu.mapper.TeambuyOrderMapper;
import com.szhengzhu.mapper.UserAddressMapper;
import com.szhengzhu.mapper.UserCouponMapper;
import com.szhengzhu.mapper.UserVoucherMapper;
import com.szhengzhu.service.OrderService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

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
    private HolidayInfoMapper holidayInfoMpper;

    @Resource
    private UserVoucherMapper userVoucherMapper;

    @Resource
    private UserCouponMapper userCouponMapper;
    
    @Resource
    private TeambuyOrderMapper teambuyOrderMapper;
    
    @Resource
    private SeckillOrderMapper seckillOrderMapper;
    
    @Resource
    private OrderErrorMapper orderErrorMapper;

    @Override
    public Result<PageGrid<OrderInfo>> pageOrder(PageParam<OrderInfo> orderPage) {
        PageHelper.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageHelper.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderInfo> orderList = orderInfoMapper.selectByExampleSelective(orderPage.getData());
        for (int index = 0, size = orderList.size(); index < size; index++) {
            String orderId = orderList.get(index).getMarkId();
            List<OrderItem> items = orderItemMapper.selectByOrderId(orderId);
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(orderId);
            orderList.get(index).setItems(items);
            orderList.get(index).setOrderDelivery(delivery);
        }
        PageInfo<OrderInfo> pageInfo = new PageInfo<>(orderList);
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<OrderInfo> getOrderInfo(String markId) {
        OrderInfo orderItem = orderInfoMapper.selectByPrimaryKey(markId);
        if (orderItem != null) {
            List<OrderItem> items = orderItemMapper.selectByOrderId(orderItem.getMarkId());
            OrderDelivery delivery = orderDeliveryMapper.selectByOrderId(orderItem.getMarkId());
            orderItem.setItems(items);
            orderItem.setOrderDelivery(delivery);
        }
        return new Result<>(orderItem);
    }

    @Override
    public Result<OrderInfo> getOrderByNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.selectByNo(orderNo);
        return new Result<>(orderInfo);
    }

    @Transactional
    @Override
    public Result<?> addBackendOrder(OrderInfo order, List<StockVo> stocks) {
        if (order.getDeliveryDate() == null)
            return new Result<>(StatusCode._4004);
        boolean isHoliday = holidayInfoMpper.countHoliday(order.getDeliveryDate()) > 0;
        if (isHoliday)
            return new Result<>(StatusCode._4010);
        IdGenerator generator = IdGenerator.getInstance();
        order.setMarkId(generator.nexId());
        order.setOrderTime(TimeUtils.today());
        order.setOrderStatus("OT01");

        OrderDelivery orderDelivery = createDelivery(order, order.getOrderDelivery(), null);
        if (orderDelivery == null) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        orderDelivery.setMarkId(generator.nexId());

        BigDecimal orderAmountTotal = new BigDecimal(0);
//        BigDecimal payAmountTotal = new BigDecimal(0);
        List<OrderItem> items = new ArrayList<>();
        OrderItem orderItem = null;
        A: for (int index = 0, size = order.getItems().size(); index < size; index++) {
            orderItem = order.getItems().get(index);
            orderItem.setMarkId(generator.nexId());
            orderItem.setOrderId(order.getMarkId());
            orderItem.setProductType(0);
            for (StockVo stock : stocks) {
                if (stock.getMarkId().equals(orderItem.getProductId())) {
                    BigDecimal basePrice = stock.getBasePrice();
                    BigDecimal salePrice = stock.getSalePrice();
                    BigDecimal payAmount = stock.getSalePrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                    orderAmountTotal = orderAmountTotal.add(salePrice);
//                    payAmountTotal = payAmountTotal.add(payAmount);
                    orderItem.setBasePrice(basePrice);
                    orderItem.setSalePrice(salePrice);
                    orderItem.setPayAmount(payAmount);
                    orderItem.setProductName(stock.getGoodsName());
                    orderItem.setSpecificationIds(stock.getSpecificationIds());
                    items.add(orderItem);
                    continue A;
                }
            }
        }
        order.setOrderAmount(orderAmountTotal);
        order.setPayAmount(BigDecimal.valueOf(0));
        orderInfoMapper.insertSelective(order);
        orderDeliveryMapper.insertSelective(orderDelivery);
        orderItemMapper.insertBatch(items);
        // 修改库存

        return new Result<>();
    }

    private OrderInfo createOrderInfo(OrderInfo orderVo) {
        OrderInfo order = new OrderInfo();
        order.setMarkId(orderVo.getMarkId());
        order.setOrderNo(orderVo.getOrderNo());
        order.setDeliveryAmount(orderVo.getDeliveryAmount());
        order.setOrderTime(TimeUtils.today());
        order.setUserId(orderVo.getUserId());
        order.setDeliveryDate(orderVo.getDeliveryDate());
        order.setRemark(orderVo.getRemark());
        order.setOrderType(orderVo.getOrderType());
        order.setOrderStatus("OT01");
        order.setCouponId(orderVo.getCouponId());
        return order;
    }

    private OrderDelivery createDelivery(OrderInfo order, OrderDelivery orderDelivery, String addressId) {
        if (orderDelivery == null && StringUtils.isEmpty(addressId)) {
            return null;
        }
        if (!StringUtils.isEmpty(addressId)) {
            orderDelivery = new OrderDelivery();
            UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
            orderDelivery.setContact(userAddress.getUserName());
            orderDelivery.setPhone(userAddress.getPhone());
            orderDelivery.setDeliveryArea(userAddress.getProvince() + userAddress.getCity() + userAddress.getArea());
            orderDelivery.setDeliveryAddress(userAddress.getUserAddress());
            orderDelivery.setLongitude(userAddress.getLongitude());
            orderDelivery.setLatitude(userAddress.getLatitude());
        }
        orderDelivery.setOrderId(order.getMarkId());
        orderDelivery.setRemark(order.getRemark());
        orderDelivery.setOrderType(0);
        orderDelivery.setDeliveryDate(order.getDeliveryDate());
        orderDelivery.setOrderNo(order.getOrderNo());
        return orderDelivery;
    }

    @Transactional
    @Override
    public Result<?> modifyBackendOrder(OrderInfo order, List<StockVo> stocks) {
        if (order.getDeliveryDate() != null) {
            boolean isHoliday = holidayInfoMpper.countHoliday(order.getDeliveryDate()) > 0;
            if (isHoliday)
                return new Result<>(StatusCode._4010);
        }
        OrderInfo orderInfo = createOrderInfo(order);

        OrderDelivery orderDelivery = createDelivery(orderInfo, order.getOrderDelivery(), null);
        if (orderDelivery == null)
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        IdGenerator generator = IdGenerator.getInstance();

        BigDecimal orderAmountTotal = new BigDecimal(0);
//        BigDecimal payAmountTotal = new BigDecimal(0);
        List<OrderItem> items = new ArrayList<>();
        OrderItem orderItem = null;
        List<String> markIds = new ArrayList<>();
        A: for (int index = 0, size = order.getItems().size(); index < size; index++) {
            orderItem = order.getItems().get(index);
            markIds.add(orderItem.getMarkId());
            orderItem.setMarkId(generator.nexId());
            orderItem.setOrderId(order.getMarkId());
            orderItem.setProductType(0);
            for (StockVo stock : stocks) {
                if (stock.getMarkId().equals(orderItem.getProductId())) {
                    BigDecimal basePrice = stock.getBasePrice();
                    BigDecimal salePrice = stock.getSalePrice();
                    BigDecimal payAmount = stock.getSalePrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                    orderAmountTotal = orderAmountTotal.add(salePrice);
//                    payAmountTotal = payAmountTotal.add(payAmount);
                    orderItem.setBasePrice(basePrice);
                    orderItem.setSalePrice(salePrice);
                    orderItem.setPayAmount(payAmount);
                    items.add(orderItem);
                    continue A;
                }
            }
        }
        orderInfo.setOrderAmount(orderAmountTotal);
        orderInfo.setPayAmount(BigDecimal.valueOf(0));
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        orderDeliveryMapper.updateByPrimaryKeySelective(orderDelivery);
        orderItemMapper.deleteBatch(markIds);
        orderItemMapper.insertBatch(items);
        // 修改库存

        return new Result<>();
    }

    @Override
    public Result<List<OrderItem>> listItemByOrderId(String orderId) {
        List<OrderItem> items = orderItemMapper.selectByOrderId(orderId);
        return new Result<>(items);
    }

    @Override
    public Result<PageGrid<OrderItem>> pageItem(PageParam<OrderItem> itemPage) {
        PageHelper.startPage(itemPage.getPageIndex(), itemPage.getPageSize());
        PageHelper.orderBy(itemPage.getSidx() + " " + itemPage.getSort());
        PageInfo<OrderItem> pageInfo = new PageInfo<>(orderItemMapper.selectByExampleSelective(itemPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<?> updateStatus(String markId, String orderStatus) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setMarkId(markId);
        orderInfo.setOrderStatus(orderStatus);
        Date time = TimeUtils.today();
        if (orderStatus.equals("OT05")) {
            orderInfo.setArriveTime(time);
        } else if (orderStatus.equals("OT10")) {
            orderInfo.setCancelTime(time);
        } else if (orderStatus.equals("OT04")) {
            orderInfo.setSendTime(time);
        }
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        return getOrderInfo(markId);
    }

    @Override
    public Result<PageGrid<OrderBase>> listAll(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageHelper.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageHelper.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderInfoMapper.selectAll(orderPage.getData());
        for (int index = 0, size = orderList.size(); index < size; index++) {
            int type = orderList.get(index).getType();
            String orderId = orderList.get(index).getOrderId();
            List<String> imgList = new ArrayList<>();
            if (type == 1) 
                imgList = orderItemMapper.selectItemImg(orderId);
            else if (type == 2)
                imgList = teambuyOrderMapper.selectItemImg(orderId);
            else if (type == 3)
                imgList = seckillOrderMapper.selectItemImg(orderId);
            orderList.get(index).setImagePath(imgList);
        }
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderList);
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<PageGrid<OrderBase>> listUnpaid(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageHelper.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageHelper.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderInfoMapper.selectUnpaid(orderPage.getData());
        for (int index = 0, size = orderList.size(); index < size; index++) {
            List<String> imgList = orderItemMapper.selectItemImg(orderList.get(index).getOrderId());
            orderList.get(index).setImagePath(imgList);
        }
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderList);
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<PageGrid<OrderBase>> listUngroup(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageHelper.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageHelper.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderInfoMapper.selectUngroup(orderPage.getData());
        for (int index = 0, size = orderList.size(); index < size; index++) {
            List<String> imgList = teambuyOrderMapper.selectItemImg(orderList.get(index).getOrderId());
            orderList.get(index).setImagePath(imgList);
        }
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderInfoMapper.selectUngroup(orderPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<PageGrid<OrderBase>> listUndelivery(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageHelper.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageHelper.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderInfoMapper.selectUndelivery(orderPage.getData());
        for (int index = 0, size = orderList.size(); index < size; index++) {
            int type = orderList.get(index).getType();
            String orderId = orderList.get(index).getOrderId();
            List<String> imgList = new ArrayList<>();
            if (type == 1) 
                imgList = orderItemMapper.selectItemImg(orderId);
            else if (type == 2)
                imgList = teambuyOrderMapper.selectItemImg(orderId);
            else if (type == 3)
                imgList = seckillOrderMapper.selectItemImg(orderId);
            orderList.get(index).setImagePath(imgList);
        }
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderList);
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<PageGrid<OrderBase>> listUnReceive(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageHelper.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageHelper.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderInfoMapper.selectUnReceive(orderPage.getData());
        for (int index = 0, size = orderList.size(); index < size; index++) {
            int type = orderList.get(index).getType();
            String orderId = orderList.get(index).getOrderId();
            List<String> imgList = new ArrayList<>();
            if (type == 1) 
                imgList = orderItemMapper.selectItemImg(orderId);
            else if (type == 2)
                imgList = teambuyOrderMapper.selectItemImg(orderId);
            else if (type == 3)
                imgList = seckillOrderMapper.selectItemImg(orderId);
            orderList.get(index).setImagePath(imgList);
        }
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderList);
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<PageGrid<OrderBase>> listUnjudge(PageParam<String> orderPage) {
        orderPage.setSidx("o.order_time");
        PageHelper.startPage(orderPage.getPageIndex(), orderPage.getPageSize());
        PageHelper.orderBy(orderPage.getSidx() + " " + orderPage.getSort());
        List<OrderBase> orderList = orderInfoMapper.selectUnjudge(orderPage.getData());
        for (int index = 0, size = orderList.size(); index < size; index++) {
            int type = orderList.get(index).getType();
            String orderId = orderList.get(index).getOrderId();
            List<String> imgList = new ArrayList<>();
            if (type == 1) 
                imgList = orderItemMapper.selectItemImg(orderId);
            else if (type == 2)
                imgList = teambuyOrderMapper.selectItemImg(orderId);
            else if (type == 3)
                imgList = seckillOrderMapper.selectItemImg(orderId);
            orderList.get(index).setImagePath(imgList);
        }
        PageInfo<OrderBase> pageInfo = new PageInfo<>(orderList);
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<OrderDetail> getOrderDetail(String orderNo) {
        OrderDetail orderDetail = orderInfoMapper.selectOrderDetail(orderNo);
        if (orderDetail == null)
            return new Result<>(StatusCode._4004);
        orderDetail.setExpireTime(new Date(orderDetail.getOrderTime().getTime() + 15 * 60 * 1000));
        List<OrderItemDetail> itemDetails = orderItemMapper.selectItemDetail(orderDetail.getOrderId());
        orderDetail.setItems(itemDetails);
        return new Result<>(orderDetail);
    }

    @Override
    public Result<List<Judge>> listItemJudge(String orderId) {
        List<Judge> itemJudge = orderItemMapper.selectItemJudge(orderId);
        return new Result<>(itemJudge);
    }

    @Transactional
    @Override
    public Result<?> createOrder(CalcData calcData) throws CloneNotSupportedException {
        Map<String, Object> result = new HashMap<>();
        BigDecimal total = calcData.getTotal();
        BigDecimal discount = calcData.getDiscount();
        BigDecimal deliveryAmount = calcData.getDeliveryAmount() == null ? new BigDecimal("0.00")
                : calcData.getDeliveryAmount();
        OrderModel orderModel = calcData.getOrderModel();
        OrderInfo order = createInfo(total, discount, deliveryAmount, orderModel);
        operateItem(orderModel, order);
        if (!StringUtils.isEmpty(orderModel.getCouponId()))
            userCouponMapper.useCoupon(orderModel.getCouponId(), order.getOrderTime());
        result.put("orderNo", order.getOrderNo());
        result.put("orderModel", orderModel);
        result.put("total", total);
        result.put("discount", discount);
        result.put("deliveryAmount", deliveryAmount);
        return new Result<>(result);
    }

    /**
     * 操作商品和库存
     * 
     * @date 2019年7月22日 下午5:57:59
     * @param orderModel
     * @param order
     * @throws CloneNotSupportedException
     */
    private void operateItem(OrderModel orderModel, OrderInfo order) throws CloneNotSupportedException {
        List<OrderItem> itemAccessoryList = new ArrayList<>();
        List<OrderItem> itemIncreaseList = new ArrayList<>();
        List<OrderItem> itemList = createItem(orderModel.getItem(), orderModel.getVoucher(), order);
        if (orderModel.getAccessory().size() > 0)
            itemAccessoryList = createAccessory(orderModel.getAccessory(), order.getMarkId());
        if (orderModel.getIncrease().size() > 0)
            itemIncreaseList = createIncrease(orderModel.getIncrease(), order.getMarkId());
        createDelivery(order, orderModel.getAddressId());
        // 修改库存
        Map<String, Object> mqMap = new HashMap<>();
        mqMap.put("addressId", orderModel.getAddressId());
        itemList.addAll(itemAccessoryList);
        itemList.addAll(itemIncreaseList);
        mqMap.put("itemList", itemList);
    }

    /**
     * 添加订单信息
     * 
     * @date 2019年7月22日 下午5:56:52
     * @param total
     * @param discount
     * @param deliveryAmount
     * @param orderModel
     * @return
     */
    private OrderInfo createInfo(BigDecimal total, BigDecimal discount, BigDecimal deliveryAmount,
            OrderModel orderModel) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setMarkId(IdGenerator.getInstance().nexId());
        orderInfo.setOrderNo(ShowUtils.createOrderNo(1, orderModel.getUserId()));
        orderInfo.setUserId(orderModel.getUserId());
        orderInfo.setOrderAmount(total.subtract(deliveryAmount));
        orderInfo.setDeliveryAmount(deliveryAmount);
        orderInfo.setPayAmount(total);
        orderInfo.setOrderTime(TimeUtils.today());
        orderInfo.setOrderType(1);
        orderInfo.setDeliveryDate(orderModel.getDelvieryDate());
        orderInfo.setRemark(orderModel.getRemark());
        orderInfo.setOrderStatus("OT01");
        orderInfo.setCouponId(orderModel.getCouponId());
        orderInfo.setOrderSource(orderModel.getOrderSource());
        orderInfoMapper.insertSelective(orderInfo);
        return orderInfo;
    }

    /**
     * 添加商品列表
     * 
     * @date 2019年7月22日 下午5:54:44
     * @param itemModelList
     * @param voucherList
     * @param order
     * @return
     * @throws CloneNotSupportedException
     */
    private List<OrderItem> createItem(List<OrderItemModel> itemModelList, List<VoucherModel> voucherList,
            OrderInfo order) throws CloneNotSupportedException {
        List<OrderItem> itemList = new LinkedList<>();
        IdGenerator generator = IdGenerator.getInstance();
        Map<String, Integer> goodsVoucherMap = new HashMap<>();
        OrderItemModel itemModel = null;
        OrderItem orderItem = null;
        for (int index = 0, size = itemModelList.size(); index < size; index++) {
            itemModel = itemModelList.get(index);
            if (itemModel.getStatus() > 0)
                continue;
            if (itemModel.getProductType().intValue() == 1) // 添加菜品券记录
                goodsVoucherMap.put(itemModel.getProductId(), itemModel.getQuantity());
            orderItem = new OrderItem();
            orderItem.setMarkId(generator.nexId());
            orderItem.setOrderId(order.getMarkId());
            orderItem.setProductId(itemModel.getProductId());
            orderItem.setProductType(itemModel.getProductType());
            orderItem.setProductName(itemModel.getProductName());
            orderItem.setSpecificationIds(itemModel.getSpecificationIds());
            orderItem.setQuantity(itemModel.getQuantity() - itemModel.getUseVoucherCount());
            orderItem.setBasePrice(itemModel.getBasePrice());
            orderItem.setSalePrice(itemModel.getSalePrice());
            BigDecimal payAmount = itemModel.getSalePrice().multiply(
                    new BigDecimal(itemModel.getQuantity().intValue() - itemModel.getUseVoucherCount().intValue()));
            orderItem.setPayAmount(payAmount);
            itemList.add(orderItem);
            if (itemModel.getUseVoucherCount().intValue() > 0) {
                String voucherIds = useVoucher(voucherList, itemModel, order.getOrderTime());
                OrderItem exchange = orderItem.clone();
                exchange.setMarkId(generator.nexId());
                exchange.setVoucherIds(voucherIds);
                exchange.setQuantity(itemModel.getUseVoucherCount());
                exchange.setVoucherIds(voucherIds);
                exchange.setPayAmount(new BigDecimal(0.00));
                itemList.add(exchange);
            }
        }
        orderItemMapper.insertBatch(itemList);
        if (!goodsVoucherMap.isEmpty())
            addUserVoucher(goodsVoucherMap, order);
        return itemList;
    }

    /**
     * 添加菜品券记录
     * 
     * @date 2019年7月22日 下午5:54:19
     * @param goodsVoucherMap
     * @param order
     */
    private void addUserVoucher(Map<String, Integer> goodsVoucherMap, OrderInfo order) {
        List<UserVoucher> voucherList = new LinkedList<>();
        IdGenerator generator = IdGenerator.getInstance();
        UserVoucher voucher = null;
        Iterator<Map.Entry<String, Integer>> iterator = goodsVoucherMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String voucherId = entry.getKey();
            int quantity = entry.getValue();
            GoodsVoucher goodsVoucher = userVoucherMapper.selectGoodsVoucher(voucherId);
            for (int i = 1; i <= quantity; i++) {
                voucher = new UserVoucher();
                voucher.setMarkId(generator.nexId());
                voucher.setUserId(order.getUserId());
                voucher.setVoucherId(voucherId);
                voucher.setVoucherName(goodsVoucher.getVoucherName());
                voucher.setProductId(goodsVoucher.getProductId());
                voucher.setSpecificationIds(goodsVoucher.getSpecificationIds());
                voucher.setProductType(goodsVoucher.getProductType());
                voucher.setOrderType(0);
                voucher.setOrderNo(order.getOrderNo());
                voucher.setCreateTime(order.getOrderTime());
                voucher.setQuantity(1);
                voucherList.add(voucher);
            }
        }
        if (voucherList.size() > 0)
            userVoucherMapper.insertBatch(voucherList);
    }

    /**
     * 商品使用菜品券
     * 
     * @date 2019年7月22日 下午5:53:59
     * @param voucherList
     * @param itemModel
     * @param orderTime
     * @return
     */
    private String useVoucher(List<VoucherModel> voucherList, OrderItemModel itemModel, Date orderTime) {
        UserVoucher voucher = null;
        StringBuffer voucherIds = new StringBuffer();
        for (int index = 0, size = voucherList.size(); index < size; index++) {
            if (voucherList.get(index).getStatus().intValue() == -1)
                continue;
            voucher = userVoucherMapper.selectByPrimaryKey(voucherList.get(index).getVoucherId());
            if (itemModel.getProductId().equals(voucher.getProductId())
                    && itemModel.getSpecificationIds().equals(voucher.getSpecificationIds())) {
                if (StringUtils.isEmpty(voucherIds.toString()))
                    voucherIds.append(voucher.getMarkId());
                else
                    voucherIds.append("," + voucher.getMarkId());
                voucher.setUseTime(orderTime);
                userVoucherMapper.updateByPrimaryKeySelective(voucher);
            }
        }
        return voucherIds.toString();
    }

    /**
     * 添加附属品
     * 
     * @date 2019年7月22日 下午5:53:40
     * @param accessoryList
     * @param orderId
     * @return
     */
    private List<OrderItem> createAccessory(List<AccessoryModel> accessoryList, String orderId) {
        List<OrderItem> itemList = new LinkedList<>();
        IdGenerator generator = IdGenerator.getInstance();
        OrderItem orderItem = null;
        AccessoryModel accessory = null;
        for (int index = 0, size = accessoryList.size(); index < size; index++) {
            accessory = accessoryList.get(index);
            if (accessory.getStatus().intValue() > 0)
                continue;
            orderItem = new OrderItem();
            orderItem.setMarkId(generator.nexId());
            orderItem.setOrderId(orderId);
            orderItem.setProductId(accessory.getAccessoryId());
            orderItem.setProductType(3);
            orderItem.setProductName(accessory.getName());
            orderItem.setQuantity(accessory.getQuantity());
            orderItem.setBasePrice(accessory.getSalePrice());
            orderItem.setSalePrice(accessory.getSalePrice());
            BigDecimal payAmount = accessory.getSalePrice().multiply(new BigDecimal(accessory.getQuantity()));
            orderItem.setPayAmount(payAmount);
            itemList.add(orderItem);
        }
        if (itemList.size() > 0)
            orderItemMapper.insertBatch(itemList);
        return itemList;
    }

    /**
     * 添加加价购商品
     * 
     * @date 2019年7月22日 下午5:53:06
     * @param increaseList
     * @param orderId
     * @return
     */
    private List<OrderItem> createIncrease(List<IncreaseModel> increaseList, String orderId) {
        List<OrderItem> itemList = new LinkedList<>();
        IdGenerator generator = IdGenerator.getInstance();
        OrderItem orderItem = null;
        IncreaseModel increase = null;
        String specIds = null;
        for (int index = 0, size = increaseList.size(); index < size; index++) {
            increase = increaseList.get(index);
            if (increase.getStatus().intValue() > 0)
                continue;
            orderItem = new OrderItem();
            orderItem.setMarkId(generator.nexId());
            orderItem.setIncreaseId(increase.getIncreaseId());
            orderItem.setOrderId(orderId);
            orderItem.setProductId(increase.getGoodsId());
            specIds = orderItemMapper.selectDefaultByGoods(increase.getGoodsId());
            orderItem.setSpecificationIds(specIds);
            orderItem.setProductType(0);
            orderItem.setProductName(increase.getGoodsName());
            orderItem.setQuantity(1);
            orderItem.setBasePrice(increase.getBasePrice());
            orderItem.setSalePrice(increase.getSalePrice());
            BigDecimal payAmount = increase.getSalePrice().multiply(new BigDecimal(1));
            orderItem.setPayAmount(payAmount);
            itemList.add(orderItem);
        }
        if (itemList.size() > 0)
            orderItemMapper.insertBatch(itemList);
        return itemList;
    }

    /**
     * 添加配送信息
     * 
     * @date 2019年7月22日 下午5:21:45
     * @param order
     * @param addressId
     */
    private void createDelivery(OrderInfo order, String addressId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
        OrderDelivery orderDelivery = new OrderDelivery();
        orderDelivery.setMarkId(IdGenerator.getInstance().nexId());
        orderDelivery.setOrderId(order.getMarkId());
        orderDelivery.setContact(userAddress.getUserName());
        orderDelivery.setDeliveryDate(order.getDeliveryDate());
        orderDelivery.setPhone(userAddress.getPhone());
        orderDelivery.setDeliveryAddress(userAddress.getUserAddress());
        orderDelivery.setDeliveryArea(userAddress.getDisplayValue());
        orderDelivery.setProvince(userAddress.getProvince());
        orderDelivery.setCity(userAddress.getCity());
        orderDelivery.setArea(userAddress.getArea());
        orderDelivery.setRemark(order.getRemark());
        orderDelivery.setOrderType(0);
        orderDelivery.setLongitude(userAddress.getLongitude());
        orderDelivery.setLatitude(userAddress.getLatitude());
        orderDelivery.setDeliveryType("1");
        if (userAddress.getSendToday())
            orderDelivery.setDeliveryType("2");
        orderDeliveryMapper.insertSelective(orderDelivery);
    }
    
    @Override
    public void orderErrorBack(String orderNo, String userId) {
        int type = Character.getNumericValue(orderNo.charAt(0));
        OrderError orderError = new OrderError();
        orderError.setMarkId(IdGenerator.getInstance().nexId());
        orderError.setOrderNo(orderNo);
        orderError.setErrorInfo("支付回调验证失败！");
        orderError.setErrorType(0);
        orderError.setAddTime(TimeUtils.today());
        orderError.setUserMark(userId);
        orderError.setType(type);
        orderErrorMapper.insertSelective(orderError);
    }

    @Override
    public List<OrderItem> orderInfoBack(String orderNo) {
        orderInfoMapper.updateOrderByNo(orderNo, "OT02");
        OrderInfo orderInfo = orderInfoMapper.selectByNo(orderNo);
        List<OrderItem> items = orderItemMapper.selectByOrderId(orderInfo.getMarkId());
        return items;
    }
}
