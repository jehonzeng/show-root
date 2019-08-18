package com.szhengzhu.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szhengzhu.bean.goods.AccessoryInfo;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.goods.MealItem;
import com.szhengzhu.bean.goods.ShoppingCart;
import com.szhengzhu.bean.goods.SpecialInfo;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.order.UserVoucher;
import com.szhengzhu.bean.vo.CalcData;
import com.szhengzhu.bean.wechat.vo.AccessoryModel;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.IncreaseBase;
import com.szhengzhu.bean.wechat.vo.IncreaseModel;
import com.szhengzhu.bean.wechat.vo.OrderItemModel;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.bean.wechat.vo.VoucherModel;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.AccessoryInfoMapper;
import com.szhengzhu.mapper.GoodsInfoMapper;
import com.szhengzhu.mapper.GoodsStockMapper;
import com.szhengzhu.mapper.GoodsVoucherMapper;
import com.szhengzhu.mapper.MealInfoMapper;
import com.szhengzhu.mapper.MealItemMapper;
import com.szhengzhu.mapper.ShoppingCartMapper;
import com.szhengzhu.mapper.SpecialInfoMapper;
import com.szhengzhu.mapper.SpecialItemMapper;
import com.szhengzhu.mapper.TypeSpecMapper;
import com.szhengzhu.service.ShoppingCartService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("shoppingCartService")
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private TypeSpecMapper typeSpecMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private GoodsVoucherMapper goodsVoucherMapper;

    @Resource
    private MealInfoMapper mealInfoMapper;

    @Resource
    private MealItemMapper mealItemMapper;

    @Resource
    private AccessoryInfoMapper accessoryInfoMapper;

    @Resource
    private SpecialInfoMapper specialInfoMapper;

    @Resource
    private SpecialItemMapper specialItemMapper;

    @Resource
    private GoodsStockMapper goodsStockMapper;

    @Override
    public Result<?> addCart(ShoppingCart cart) {
        GoodsBase product = null;
        if (cart.getProductType().equals(0)) { // goods
            String specIds = cart.getSpecificationIds();
            if (StringUtils.isEmpty(specIds)) {
                Map<String, String> defaultSpecMap = typeSpecMapper.selectDefaultByGoods(cart.getProductId());
                if (defaultSpecMap != null)
                    specIds = defaultSpecMap.containsKey("specIds") ? defaultSpecMap.get("specIds") : null;
                if (specIds == null)
                    return new Result<>(StatusCode._5010);
            }
            product = goodsInfoMapper.selectCartGoodsInfo(cart.getProductId(), specIds);
            cart.setSpecificationIds(specIds); // select default specifications, order by specification_id asc
        } else if (cart.getProductType().equals(1)) { // voucher
            product = goodsVoucherMapper.selectCartVoucher(cart.getProductId());
        } else if (cart.getProductType().equals(2)) { // meal
            product = mealInfoMapper.selectCartMeal(cart.getProductId());
        }
        if (product == null)
            return new Result<>(StatusCode._4004);
        ShoppingCart shoppingCart = shoppingCartMapper.selectSingleCart(cart.getUserId(), cart.getProductId(),
                cart.getSpecificationIds());
        if (shoppingCart != null) {
            shoppingCart.setQuantity(shoppingCart.getQuantity() + cart.getQuantity());
            shoppingCart.setUpdateTime(TimeUtils.today());
            shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
        } else {
            IdGenerator generator = IdGenerator.getInstance();
            cart.setMarkId(generator.nexId());
            cart.setProductName(product.getGoodsName());
            cart.setBasePrice(product.getBasePrice());
            cart.setAddPrice(product.getSalePrice());
            cart.setAddTime(TimeUtils.today());
            shoppingCartMapper.insertSelective(cart);
        }
        if (shoppingCart == null)
            shoppingCart = shoppingCartMapper.selectSingleCart(cart.getUserId(), cart.getProductId(),
                    cart.getSpecificationIds());
        if (shoppingCart.getCurrentStock().intValue() < 0)
            shoppingCart.setStatus(2);
        return new Result<>(shoppingCart);
    }

    @Override
    public Result<?> modifyCart(ShoppingCart cart) {
        ShoppingCart oldCart = shoppingCartMapper.selectByPrimaryKey(cart.getMarkId());
        if (oldCart == null)
            return new Result<>(StatusCode._4004);
        if (cart.getQuantity().intValue() == 0) {
            shoppingCartMapper.deleteByPrimaryKey(cart.getMarkId());
            return new Result<>();
        }
        boolean isExist = cart.getProductType().equals(0)
                && !oldCart.getSpecificationIds().equals(cart.getSpecificationIds());
        if (isExist) { // goods and different specifications
            ShoppingCart shoppingCart = shoppingCartMapper.selectSingleCart(cart.getUserId(), cart.getProductId(),
                    cart.getSpecificationIds());
            if (shoppingCart != null) { // exist new specification
                shoppingCart.setQuantity(shoppingCart.getQuantity() + cart.getQuantity());
                shoppingCart.setUpdateTime(TimeUtils.today());
                shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
                shoppingCartMapper.deleteByPrimaryKey(cart.getMarkId());
            } else {
                GoodsBase goodsBase = goodsInfoMapper.selectCartGoodsInfo(cart.getProductId(),
                        cart.getSpecificationIds());
                cart.setSalePrice(goodsBase.getBasePrice());
                cart.setAddPrice(goodsBase.getSalePrice());
            }
        }
        cart.setUpdateTime(TimeUtils.today());
        shoppingCartMapper.updateByPrimaryKeySelective(cart);
        return new Result<>();
    }

    @Override
    public Result<List<ShoppingCart>> listCart(String userId) {
        List<ShoppingCart> carts = shoppingCartMapper.selectByUser(userId);
        for (int index = 0, size = carts.size(); index < size; index++) {
            if (carts.get(index).getCurrentStock().intValue() < 1)
                carts.get(index).setStatus(2);
        }
        return new Result<>(carts);
    }

    @Override
    public Result<Map<String, Object>> getCartAddition() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> accessoryList = accessoryInfoMapper.selectCartList();
        List<IncreaseBase> increaseList = specialInfoMapper.selectCartIncrease();
        List<GoodsBase> recommendList = goodsInfoMapper.selectRecommend();
        result.put("accessory", accessoryList);
        result.put("increase", increaseList);
        result.put("recommend", recommendList);
        return new Result<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<?> calcTotal(OrderModel orderModel, Map<String, UserVoucher> voucherMap, UserCoupon coupon,
            UserAddress address) {
        List<OrderItemModel> itemList = orderModel.getItem();
        List<AccessoryModel> accessoryList = orderModel.getAccessory();
        List<VoucherModel> voucherList = orderModel.getVoucher();
        List<IncreaseModel> increaseList = orderModel.getIncrease();
        BigDecimal total = new BigDecimal("0.00");
        BigDecimal discount = new BigDecimal("0.00");
        // 结算商品（包括商品、商品券、套餐）
        Map<String, Object> productResult = calcProduct(itemList, voucherList, voucherMap, total, discount,
                orderModel.getAddressId());
        itemList = (List<OrderItemModel>) productResult.get("itemList");
        voucherList = (List<VoucherModel>) productResult.get("voucherList");
        total = (BigDecimal) productResult.get("total");
        discount = (BigDecimal) productResult.get("discount");
        orderModel.setVoucher(voucherList);
        orderModel.setItem(itemList);
        // 结算附属品
        Map<String, Object> accessoryResult = calcAccessory(accessoryList, total);
        accessoryList = (List<AccessoryModel>) accessoryResult.get("accessoryList");
        total = (BigDecimal) accessoryResult.get("total");
        orderModel.setAccessory(accessoryList);
        // 结算加价购商品
        Map<String, Object> increaseResult = calcIncrease(increaseList, total, discount, orderModel.getAddressId());
        increaseList = (List<IncreaseModel>) increaseResult.get("increaseList");
        total = (BigDecimal) increaseResult.get("total");
        discount = (BigDecimal) increaseResult.get("discount");
        orderModel.setIncrease(increaseList);
        CalcData calcData = new CalcData();
        calcData.setFirstTotal(total.add(discount));
        BigDecimal couponDiscount = new BigDecimal(0.00);
        if (coupon != null) {
            // 使用优惠券
            Map<String, Object> resutMap = calcCoupon(coupon, total, orderModel);
            total = (BigDecimal) resutMap.get("total");
            couponDiscount = (BigDecimal) resutMap.get("couponDiscount");
            orderModel = (OrderModel) resutMap.get("orderModel");
        }
        calcData.setDeliveryAmount(new BigDecimal(10));
        calcData.setDeliveryLimit(new BigDecimal(400));
        if (address != null) {
            // 获取配送费

        }
        if (total.compareTo(calcData.getDeliveryLimit()) > 0) // 满额免邮
            calcData.setDeliveryAmount(new BigDecimal(0));
        else
            total = total.add(calcData.getDeliveryAmount());
        calcData.setTotal(total);
        calcData.setDiscount(discount);
        calcData.setCouponDiscount(couponDiscount);
        calcData.setOrderModel(orderModel);
        return new Result<>(calcData);
    }

    /**
     * 结算商品（包括菜品、商品券、套餐）
     * 
     * @date 2019年7月5日 上午11:41:32
     * @param itemList
     * @param voucherList
     * @param voucherMap
     * @param total
     * @param discount
     * @param addressId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> calcProduct(List<OrderItemModel> itemList, List<VoucherModel> voucherList,
            Map<String, UserVoucher> voucherMap, BigDecimal total, BigDecimal discount, String addressId) {
        item: for (int index = 0; index < itemList.size(); index++) {
            itemList.get(index).setUseVoucherCount(0);
            itemList.get(index).setInvalidCount(0); // 以防前端提交过来 默认值0才行
            int productType = itemList.get(index).getProductType();
            String productId = itemList.get(index).getProductId();
            int quantity = itemList.get(index).getQuantity();
            BigDecimal salePrice = new BigDecimal("0.00"); // 当前销售价格
            BigDecimal basePrice = new BigDecimal("0.00"); // 商品原价 并非数据库中的成本价
            int count = 0; // 使用菜品券数量
            if (productType == 0) {
                String specIds = itemList.get(index).getSpecificationIds();
                Map<String, Integer> stockMap = deliveryOrStock(addressId, productId, specIds);
                int status = stockMap.get("status");
                int stock = stockMap.get("stock");
                itemList.get(index).setStatus(status);
                if (status > 0)
                    continue;
                if (stock == 0)
                    itemList.remove(index);
                if (quantity > stock) { // 需要购买的数量超出库存数量
                    itemList.get(index).setInvalidCount(quantity - stock);
                    quantity = stock;
                }
                GoodsInfo goodsInfo = goodsInfoMapper.selectInfoById(productId, specIds);
                if (goodsInfo == null || !goodsInfo.getServerStatus().equals("ZT02")) {
                    itemList.get(index).setStatus(3);
                    continue;
                }
                if (voucherList != null) {
                    for (int i = 0; i < voucherList.size(); i++) {
                        if ((voucherList.get(i).getStatus().intValue() & 1) == 1) // 已使用或失效
                            continue;
                        UserVoucher voucher = voucherMap.containsKey(voucherList.get(i).getVoucherId())
                                ? voucherMap.get(voucherList.get(i).getVoucherId())
                                : null;
                        if (voucher != null) {
                            voucherList.get(i).setVoucherName(voucher.getVoucherName());
                            voucherList.get(i).setSpecs(voucher.getSpecs());
                        }
                        boolean isThisItem = voucher != null && voucher.getUseTime() == null
                                && voucher.getProductId().equals(productId)
                                && voucher.getSpecificationIds().equals(specIds);
                        if (isThisItem && stock == count) { // 该商品对应的菜品券数量超出库存数量
                            voucherList.get(i).setStatus(-1);
                            continue;
                        }
                        if (isThisItem) {
                            voucherList.get(i).setStatus(1);
                            count += 1;
                            if (quantity < count)
                                quantity = count; // 该商品对应的菜品券数量超过用户所选商品数量（该商品规格对应菜品券规格）
                        }
                        voucherList.get(i).setVoucherName(voucher.getVoucherName());
                        voucherList.get(i).setSpecs(voucher.getSpecs());
                    }
                }
                itemList.get(index).setUseVoucherCount(count);
                salePrice = goodsInfo.getSalePrice();
                basePrice = goodsInfo.getBasePrice();
                itemList.get(index).setProductName(goodsInfo.getGoodsName());
            } else if (productType == 1) {
                GoodsVoucher voucher = goodsVoucherMapper.selectInfoById(productId);
                if (voucher == null || !voucher.getServerStatus()) {
                    itemList.get(index).setStatus(3);
                    continue;
                }
                if (voucher.getCurrentStock().intValue() < 1) { // 没有库存
                    itemList.get(index).setInvalidCount(quantity);
                    itemList.get(index).setStatus(2);
                    continue;
                }
                if (quantity > voucher.getCurrentStock()) { // 需要购买的数量超出库存数量
                    itemList.get(index).setInvalidCount(quantity - voucher.getCurrentStock());
                    quantity = voucher.getCurrentStock();
                }
                salePrice = voucher.getPrice();
                basePrice = voucher.getBasePrice();
                itemList.get(index).setSalePrice(voucher.getPrice());
                itemList.get(index).setProductName(voucher.getVoucherName());
            } else if (productType == 2) {
                MealInfo meal = mealInfoMapper.selectByPrimaryKey(productId);
                if (!meal.getServerStatus()) {
                    itemList.get(index).setStatus(3);
                    continue;
                }
                if (meal.getStockSize().intValue() < 1) { // 没有库存
                    itemList.get(index).setInvalidCount(quantity);
                    itemList.get(index).setStatus(2);
                    continue;
                }
                if (quantity > meal.getStockSize()) { // 需要购买的数量超出库存数量
                    itemList.get(index).setInvalidCount(quantity - meal.getStockSize());
                    quantity = meal.getStockSize();
                }
                salePrice = meal.getSalePrice();
                basePrice = meal.getSalePrice();
                itemList.get(index).setProductName(meal.getTheme());
                quantity = quantity < meal.getStockSize().intValue() ? quantity : meal.getStockSize();
                List<MealItem> mealItemList = mealItemMapper.selectItemByMeal(productId);
                if (mealItemList.size() == 0) {
                    itemList.get(index).setStatus(3);
                    continue;
                }
                for (int i = 0, j = mealItemList.size(); i < j; i++) {
                    MealItem mealItem = mealItemList.get(i);
                    Map<String, Integer> stockMap = deliveryOrStock(addressId, mealItem.getGoodsId(),
                            mealItem.getSpecificationIds());
                    int status = stockMap.get("status");
                    itemList.get(index).setStatus(status);
                    if (status > 0)
                        continue item;
                }
            } else {
                itemList.get(index).setStatus(1);
                continue;
            }
            itemList.get(index).setSalePrice(salePrice);
            itemList.get(index).setBasePrice(basePrice);
            itemList.get(index).setQuantity(quantity + itemList.get(index).getInvalidCount());
            total = total.add(salePrice.multiply(new BigDecimal(quantity - count)));
            discount = discount.add(basePrice.subtract(salePrice).multiply(new BigDecimal(quantity - count)));
            itemList.get(index).setStatus(0);
        }
        if (voucherList != null) {
            Map<String, Object> voucherResult = addVoucherGoods(voucherList, voucherMap, addressId);
            voucherList = (List<VoucherModel>) voucherResult.get("voucherList");
            itemList.addAll((List<OrderItemModel>) voucherResult.get("voucherGoodsList"));
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("itemList", itemList);
        resultMap.put("voucherList", voucherList);
        resultMap.put("total", total);
        resultMap.put("discount", discount);
        return resultMap;
    }

    /**
     * 将多选出来的菜品券以兑换商品的形式添加到用户结算商品列表中
     * 
     * @date 2019年7月22日 上午10:21:04
     * @param voucherList
     * @param voucherMap
     * @param addressId
     * @return
     */
    private Map<String, Object> addVoucherGoods(List<VoucherModel> voucherList, Map<String, UserVoucher> voucherMap,
            String addressId) {
        Map<String, Object> result = new HashMap<>();
        List<OrderItemModel> voucherGoodsList = new ArrayList<>();
        Map<String, OrderItemModel> orderItemMap = new HashMap<>();
        Map<String, Integer> stockMap = new HashMap<>();
        OrderItemModel orderItem = null;
        for (int i = 0; i < voucherList.size(); i++) {
            if ((voucherList.get(i).getStatus().intValue() & 1) == 1) // 已使用或失效
                continue;
            UserVoucher voucher = voucherMap.containsKey(voucherList.get(i).getVoucherId())? voucherMap.get(voucherList.get(i).getVoucherId()) : null;
            if (voucher != null) {
                voucherList.get(i).setVoucherName(voucher.getVoucherName());
                voucherList.get(i).setSpecs(voucher.getSpecs());
            }
            if (voucher != null && voucher.getUseTime() == null) {
                String goodsId = voucher.getProductId();
                String specIds = voucher.getSpecificationIds();
                if (orderItemMap.containsKey(goodsId + specIds)) {
                    orderItem = orderItemMap.get(goodsId + specIds);
                    int quantity = orderItem.getQuantity();
                    int stock = stockMap.get(goodsId + specIds);
                    if (quantity > stock - 1) {
                        voucherList.get(i).setStatus(-1);
                        continue;
                    } else {
                        voucherList.get(i).setStatus(1);
                    }
                    orderItem.setQuantity(quantity + 1);
                    orderItem.setUseVoucherCount(quantity + 1);
                    orderItemMap.put(goodsId + specIds, orderItem);
                } else {
                    if (stockMap.containsKey(goodsId + specIds)) {
                        int stock = stockMap.get(goodsId + specIds);
                        if (stock < 0) {
                            voucherList.get(i).setStatus(-1);
                            continue;
                        }
                    } else {
                        Map<String, Integer> goodsStockMap = deliveryOrStock(addressId, goodsId, specIds);
                        int status = goodsStockMap.get("status");
                        int stock = goodsStockMap.get("stock");
                        if (status > 0 || stock < 1 || (status == 0 && stock < 1)) {
                            stock = 0;
                            voucherList.get(i).setStatus(-1);
                            stockMap.put(goodsId + specIds, stock);
                            continue;
                        }
                        if (stock > 0) {
                            voucherList.get(i).setStatus(1);
                            stockMap.put(goodsId + specIds, stock);
                        }
                    }
                    GoodsInfo goodsInfo = goodsInfoMapper.selectInfoById(goodsId, specIds);
                    orderItem = new OrderItemModel();
                    orderItem.setProductId(goodsId);
                    orderItem.setProductType(0);
                    orderItem.setProductName(goodsInfo.getGoodsName());
                    orderItem.setSpecificationIds(specIds);
                    orderItem.setQuantity(1);
                    orderItem.setBasePrice(goodsInfo.getBasePrice());
                    orderItem.setSalePrice(goodsInfo.getSalePrice());
                    orderItem.setUseVoucherCount(1);
                    orderItemMap.put(goodsId + specIds, orderItem);
                }
            } else {
                voucherList.get(i).setStatus(-1);
            }
        }
        if (!orderItemMap.isEmpty()) {
            Iterator<Map.Entry<String, OrderItemModel>> iterator = orderItemMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, OrderItemModel> entry = iterator.next();
                voucherGoodsList.add(entry.getValue());
            }
        }
        result.put("voucherList", voucherList);
        result.put("voucherGoodsList", voucherGoodsList);
        return result;
    }

    /**
     * 结算附属商品
     * 
     * @date 2019年7月5日 上午11:41:00
     * @param accessoryList
     * @param total
     * @return
     */
    private Map<String, Object> calcAccessory(List<AccessoryModel> accessoryList, BigDecimal total) {
        AccessoryInfo accessoryInfo = null;
        BigDecimal salePrice = null;
        int quantity = 0;
        for (int index = 0, size = accessoryList.size(); index < size; index++) {
            String accessoryId = accessoryList.get(index).getAccessoryId();
            quantity = accessoryList.get(index).getQuantity();
            accessoryInfo = accessoryInfoMapper.selectByPrimaryKey(accessoryId);
            if (!accessoryInfo.getServerStatus()) {
                accessoryList.get(index).setStatus(3);
                continue;
            }
            if (accessoryInfo.getStockSize().intValue() < 1) {
                accessoryList.get(index).setStatus(2);
                continue;
            }
            accessoryList.get(index).setName(accessoryInfo.getTheme());
            salePrice = accessoryInfo.getSalePrice();
            accessoryList.get(index).setSalePrice(salePrice);
            total = total.add(salePrice.multiply(new BigDecimal(quantity)));
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("accessoryList", accessoryList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 结算加价购商品
     * 
     * @date 2019年7月5日 上午11:40:37
     * @param increaseList
     * @param total
     * @param discount
     * @param addressId
     * @return
     */
    private Map<String, Object> calcIncrease(List<IncreaseModel> increaseList, BigDecimal total, BigDecimal discount,
            String addressId) {
        BigDecimal increaseTotal = new BigDecimal("0.00");
        SpecialInfo specialInfo = null;
        BigDecimal price = null;
        for (int index = 0, size = increaseList.size(); index < size; index++) {
            String specialId = increaseList.get(index).getIncreaseId();
            String goodsId = increaseList.get(index).getGoodsId();
            specialInfo = specialInfoMapper.selectByPrimaryKey(specialId);
            if (specialInfo == null) {
                increaseList.get(index).setStatus(3);
                continue;
            }
            price = specialInfo.getPrice();
            Map<String, Integer> stockMap = deliveryOrStock(addressId, goodsId, null);
            int status = stockMap.get("status");
            increaseList.get(index).setStatus(status);
            if (status > 0)
                continue;
            if (total.compareTo(specialInfo.getLimitPrice()) > 0) {
                increaseTotal = increaseTotal.add(specialInfo.getPrice());
                BigDecimal salePrice = specialItemMapper.selectGoodsSalePrice(increaseList.get(index).getGoodsId(),
                        specialId);
                discount = discount.add(salePrice.subtract(price));
                increaseList.get(index).setStatus(0);
                increaseList.get(index).setBasePrice(salePrice); // 该商品在非加价购中的售卖价格
                increaseList.get(index).setSalePrice(price);
                String goodsName = goodsInfoMapper.selectNameById(goodsId);
                increaseList.get(index).setGoodsName(goodsName);
            } else {
                increaseList.get(index).setStatus(4);
            }
        }
        total = total.add(increaseTotal);
        Map<String, Object> result = new HashMap<>();
        result.put("increaseList", increaseList);
        result.put("total", total);
        result.put("discount", discount);
        return result;
    }

    /**
     * 检测规格商品是否有可配送以及库存
     * 
     * @date 2019年7月5日 上午11:46:28
     * @param addressId
     * @param goodsId
     * @param specIds
     * @return
     */
    private Map<String, Integer> deliveryOrStock(String addressId, String goodsId, String specIds) {
        Map<String, Integer> result = new HashMap<>();
        if (StringUtils.isEmpty(addressId)) {
            result.put("status", 0);
            result.put("stock", 100);
            return result;
        }
        if (StringUtils.isEmpty(specIds)) {
            Map<String, String> defaultSpecMap = typeSpecMapper.selectDefaultByGoods(goodsId);
            if (defaultSpecMap != null)
                specIds = defaultSpecMap.containsKey("specIds") ? defaultSpecMap.get("specIds") : null;
        }
        if (StringUtils.isEmpty(specIds)) {// 默认库存不足
            result.put("status", 2);
            result.put("stock", 0);
            return result;
        }
        Map<String, Integer> deliveryStock = goodsStockMapper.selectDeliveryAndStock(addressId, goodsId, specIds);
        boolean isDelivery = false;
        int currentStock = 100;
        if (deliveryStock != null) {
            isDelivery = deliveryStock.containsKey("isDelivery")
                    ? ((Number) deliveryStock.get("isDelivery")).intValue() == 1
                    : false;
            currentStock = deliveryStock.containsKey("currentStock")
                    ? ((Number) deliveryStock.get("currentStock")).intValue()
                    : 1;
        }
        result.put("status", (isDelivery ? (currentStock < 1 ? 2 : 0) : 1));
        result.put("stock", (isDelivery ? currentStock : 1000));
        return result;
    }

    /**
     * 计算使用优惠券总额
     * 
     * @date 2019年7月5日 上午11:44:24
     * @param coupon
     * @param total
     * @param discount
     * @param orderModel
     * @return
     */
    private Map<String, Object> calcCoupon(UserCoupon coupon, BigDecimal total, OrderModel orderModel) {
        Map<String, Object> resultMap = new HashMap<>();
        long currentTime = System.currentTimeMillis();
        BigDecimal couponAmount = new BigDecimal(0.00);
        if (coupon.getServerStatus().intValue() == 0 && currentTime > coupon.getStartTime().getTime()
                && currentTime < coupon.getStopTime().getTime() && coupon.getUseTime() == null) {
            BigDecimal couponDiscount = coupon.getCouponDiscount();
            BigDecimal couponPrice = coupon.getCouponPrice();
            BigDecimal limitPrice = coupon.getLimitPrice();
            boolean flag = true;
            if (limitPrice != null && total.compareTo(limitPrice) < 0) {
                flag = false;
                orderModel.setCouponId(null);
            }
            if (flag) {
                if (couponDiscount != null) {
                    BigDecimal temp = total.multiply(couponDiscount);
                    couponAmount = total.subtract(temp);
                    total = temp;
                } else if (couponPrice != null) {
                    couponAmount = couponPrice;
                    total = total.subtract(couponPrice);
                }
            }
        } else {
            orderModel.setCouponId(null);
        }
        resultMap.put("total", total);
        resultMap.put("couponDiscount", couponAmount);
        resultMap.put("orderModel", orderModel);
        return resultMap;
    }
}
