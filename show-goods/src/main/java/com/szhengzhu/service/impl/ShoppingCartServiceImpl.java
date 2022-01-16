package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.feign.ShowOrderClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.goods.*;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.order.UserVoucher;
import com.szhengzhu.bean.user.ManagerCode;
import com.szhengzhu.bean.wechat.vo.*;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.code.GoodsStatus;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.ShoppingCartService;
import com.szhengzhu.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jehon Zeng
 */
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
    private AccessoryInfoMapper accessoryInfoMapper;

    @Resource
    private SpecialInfoMapper specialInfoMapper;

    @Resource
    private SpecialItemMapper specialItemMapper;

    @Resource
    private GoodsStockMapper goodsStockMapper;

    @Resource
    private DeliveryAreaMapper deliveryAreaMapper;

    @Resource
    private MealStockMapper mealStockMapper;

    @Resource
    private Redis redis;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ShowUserClient showUserClient;

    @Override
    public ShoppingCart addCart(ShoppingCart cart) {
        GoodsBase product = null;
        String productType = cart.getProductType().toString();
        switch (productType) {
            case Contacts.TYPE_OF_GOODS:
                String specIds = cart.getSpecificationIds();
                if (StringUtils.isEmpty(specIds)) {
                    Map<String, String> defaultSpecMap = typeSpecMapper.selectDefaultByGoods(cart.getProductId());
                    if (defaultSpecMap != null) {
                        specIds = defaultSpecMap.getOrDefault("specIds", null);
                    }
                    ShowAssert.checkTrue(StrUtil.isEmpty(specIds), StatusCode._5010);
                }
                product = goodsInfoMapper.selectCartGoodsInfo(cart.getProductId(), specIds);
                // select default specifications, order by specification_id asc
                cart.setSpecificationIds(specIds);
                break;
            case Contacts.TYPE_OF_VOUCHER:
                // voucher
                product = goodsVoucherMapper.selectCartVoucher(cart.getProductId());
                break;
            case Contacts.TYPE_OF_MEAL:
                // meal
                product = mealInfoMapper.selectCartMeal(cart.getProductId());
                break;
            default:
                break;
        }
        ShowAssert.checkNull(product, StatusCode._4004);
        ShoppingCart shoppingCart = shoppingCartMapper.selectSingleCart(cart.getUserId(), cart.getProductId(),
                cart.getSpecificationIds());
        if (ObjectUtil.isNotNull(shoppingCart)) {
            shoppingCart.setQuantity(shoppingCart.getQuantity() + cart.getQuantity());
            shoppingCart.setUpdateTime(DateUtil.date());
            shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
        } else {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            cart.setMarkId(snowflake.nextIdStr());
            cart.setProductName(product.getGoodsName());
            cart.setBasePrice(product.getBasePrice());
            cart.setAddPrice(product.getSalePrice());
            cart.setAddTime(DateUtil.date());
            shoppingCartMapper.insertSelective(cart);
        }
        if (ObjectUtil.isNull(shoppingCart)) {
            shoppingCart = shoppingCartMapper.selectSingleCart(cart.getUserId(), cart.getProductId(),
                    cart.getSpecificationIds());
        }
        if (shoppingCart.getCurrentStock() < 0) {
            shoppingCart.setStatus(2);
        }
        return shoppingCart;
    }

    @Override
    public void modifyCart(ShoppingCart cart) {
        ShoppingCart oldCart = shoppingCartMapper.selectByPrimaryKey(cart.getMarkId());
        ShowAssert.checkNull(oldCart, StatusCode._4004);
        if (cart.getQuantity() == 0) {
            shoppingCartMapper.deleteByPrimaryKey(cart.getMarkId());
            return;
        }
        boolean isExist = cart.getProductType().equals(0)
                && !oldCart.getSpecificationIds().equals(cart.getSpecificationIds());
        if (isExist) {
            // goods and different specifications
            ShoppingCart shoppingCart = shoppingCartMapper.selectSingleCart(cart.getUserId(), cart.getProductId(),
                    cart.getSpecificationIds());
            if (ObjectUtil.isNotNull(shoppingCart)) {
                // exist new specification
                shoppingCart.setQuantity(shoppingCart.getQuantity() + cart.getQuantity());
                shoppingCart.setUpdateTime(DateUtil.date());
                shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
                shoppingCartMapper.deleteByPrimaryKey(cart.getMarkId());
            } else {
                GoodsBase goodsBase = goodsInfoMapper.selectCartGoodsInfo(cart.getProductId(),
                        cart.getSpecificationIds());
                cart.setSalePrice(goodsBase.getBasePrice());
                cart.setAddPrice(goodsBase.getSalePrice());
            }
        }
        cart.setUpdateTime(DateUtil.date());
        shoppingCartMapper.updateByPrimaryKeySelective(cart);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refresh(String userId, List<ShoppingCart> cartList) {
        shoppingCartMapper.deleteUserCart(userId);
        if (!cartList.isEmpty()) {
            shoppingCartMapper.insertBatch(cartList);
        }
    }

    @Override
    public List<ShoppingCart> listCart(String userId) {
        List<ShoppingCart> carts = shoppingCartMapper.selectByUser(userId);
        carts.forEach(cart -> {
            if (cart.getCurrentStock() < 1) {
                cart.setStatus(2);
            }
        });
        return carts;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getCartAddition() {
        Map<String, Object> result = new HashMap<>(8);
        List<Map<String, Object>> accessoryList = accessoryInfoMapper.selectCartList();
        List<IncreaseBase> increaseList = specialInfoMapper.selectCartIncrease();
        String key = "cart:recommend";
        List<GoodsBase> recommendList = (List<GoodsBase>) redis.get(key);
        if (recommendList == null) {
            Lock lock = new ReentrantLock();
            lock.lock();
            try {
                recommendList = (List<GoodsBase>) redis.get(key);
                if (recommendList == null) {
                    recommendList = goodsInfoMapper.selectRecommend();
                    redis.set(key, recommendList, 5 * 60 * 60L);
                }
            } finally {
                lock.unlock();
            }
        }
        result.put("accessory", accessoryList);
        result.put("increase", increaseList);
        result.put("recommend", recommendList);
        return result;
    }

    @Override
    public CalcData calcTotal(OrderModel orderModel) {
        List<DeliveryArea> deliveryAreaList = new ArrayList<>();
        if (!StringUtils.isEmpty(orderModel.getAddressId())) {
            deliveryAreaList = deliveryAreaMapper.selectAreaByAddressId(orderModel.getAddressId());
            if (deliveryAreaList.isEmpty()) {
                return noDelivery(orderModel);
            }
        }
        // 结算商品（包括商品、商品券、套餐）
        calcProduct(orderModel.getItem(), orderModel.getVoucher());
        // 结算附属品
        BigDecimal total = calcAccessory(orderModel.getAccessory());
        // 商品库存 并返回商品配送仓列表
        List<String> deliveryList = calcStockAndDelivery(orderModel);
        // 计算商品总价与优惠价
        CalcData calcData = calcTotalAndDiscount(orderModel.getItem(), total);
        // 特价商品结算
        calcSpecial(orderModel, calcData);
        // 结算加价购商品
        List<String> increaseDeliveryList = calcIncrease(orderModel.getIncrease(), orderModel.getAddressId(), calcData);
        deliveryList.addAll(increaseDeliveryList);
        // 计算优惠券
        calcCoupon(orderModel, calcData);

        // 输入口令优惠
        codeDiscount(orderModel.getCode(), calcData);
        // 以防万一没有设置配送值问题
        BigDecimal deliveryAmount = BigDecimal.ZERO;
        BigDecimal deliveryLimit = BigDecimal.ZERO;
        for (DeliveryArea deliveryArea : deliveryAreaList) {
            if (deliveryList.contains(deliveryArea.getStorehouseId()) && deliveryArea.getLimitPrice().compareTo(deliveryLimit) > 0) {
                deliveryAmount = deliveryArea.getDeliveryPrice();
                deliveryLimit = deliveryArea.getLimitPrice();
            }
        }
        total = calcData.getTotal();
        if (total.compareTo(deliveryLimit) > 0) {
            // 满额免邮
            deliveryAmount = new BigDecimal(0);
        } else {
            total = total.add(deliveryAmount);
        }
        total = total.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : total;
        calcData.setFirstTotal(NumberUtil.add(total, calcData.getDiscount(), calcData.getCodeDiscount(), calcData.getCouponDiscount()));
        calcData.setOrderModel(orderModel);
        calcData.setDeliveryAmount(deliveryAmount);
        calcData.setDeliveryLimit(deliveryLimit);
        calcData.setTotal(total);
        return calcData;
    }

    /**
     * 输入口令优惠
     */
    private void codeDiscount(String code, CalcData calcData) {
        if (StrUtil.isEmpty(code)) {
            return;
        }
        Result<ManagerCode> codeResult = showUserClient.getManagerCodeByCode(code);
        ShowAssert.checkTrue(!codeResult.isSuccess(), StatusCode._4036);
        ManagerCode managerCode = codeResult.getData();
        if (ObjectUtil.isNull(managerCode.getDiscount())) {
            return;
        }
        calcData.setCodeDiscount(NumberUtil.sub(calcData.getTotal(), NumberUtil.mul(calcData.getTotal(), managerCode.getDiscount())));
        calcData.setTotal(NumberUtil.mul(calcData.getTotal(), managerCode.getDiscount()));
        calcData.setManagerId(managerCode.getUserId());
    }

    /**
     * 计算总金额，优惠金额
     *
     * @param itemList
     * @param total
     * @return
     */
    private CalcData calcTotalAndDiscount(List<OrderItemModel> itemList, BigDecimal total) {
        BigDecimal discount = BigDecimal.ZERO;
        for (OrderItemModel itemModel : itemList) {
            if (!itemModel.getStatus().equals(0)) {
                continue;
            }
            int quantity = itemModel.getQuantity() - itemModel.getInvalidCount() - itemModel.getUseVoucherCount();
            total = NumberUtil.add(total, NumberUtil.mul(itemModel.getSalePrice(), quantity));
            discount = NumberUtil.add(discount, NumberUtil.mul(NumberUtil.sub(itemModel.getBasePrice(), itemModel.getSalePrice()), quantity));
        }
        CalcData calcData = new CalcData();
        calcData.setTotal(total);
        calcData.setDiscount(discount);
        return calcData;
    }

    /**
     * 当所选地址不在配送范围内
     *
     * @param orderModel
     * @return
     */
    private CalcData noDelivery(OrderModel orderModel) {
        for (OrderItemModel itemModel : orderModel.getItem()) {
            itemModel.setStatus(1);
        }
        for (VoucherModel voucherModel : orderModel.getVoucher()) {
            voucherModel.setStatus(-1);
        }
        for (AccessoryModel accessoryModel : orderModel.getAccessory()) {
            accessoryModel.setStatus(3);
        }
        for (IncreaseModel increaseModel : orderModel.getIncrease()) {
            increaseModel.setStatus(1);
        }
        CalcData calcData = new CalcData();
        calcData.setDeliveryAmount(BigDecimal.ZERO);
        calcData.setDeliveryLimit(new BigDecimal(100));
        calcData.setTotal(BigDecimal.ZERO);
        calcData.setDiscount(BigDecimal.ZERO);
        return calcData;
    }

    /**
     * 计算商品库存及是否可配送
     *
     * @param orderModel
     * @return
     */
    private List<String> calcStockAndDelivery(OrderModel orderModel) {
        if (StrUtil.isEmpty(orderModel.getAddressId())) {
            return new ArrayList<>();
        }
        List<String> deliveryList = new LinkedList<>();
        for (OrderItemModel itemModel : orderModel.getItem()) {
            String delivery;
            if (itemModel.getProductType() == Integer.parseInt(Contacts.TYPE_OF_GOODS)) {
                delivery = goodsDeliveryAndStock(itemModel, orderModel);
            } else if (itemModel.getProductType() == Integer.parseInt(Contacts.TYPE_OF_MEAL)) {
                delivery = mealDeliverAndStock(itemModel, orderModel.getAddressId());
            } else {
                continue;
            }
            if (delivery != null) {
                deliveryList.add(delivery);
            }
        }
        return deliveryList;
    }

    /**
     * 判断规格商品是否有可配送以及库存
     *
     * @param itemModel
     * @param orderModel
     * @return
     * @date 2019年7月5日 上午11:46:28
     */
    private String goodsDeliveryAndStock(OrderItemModel itemModel, OrderModel orderModel) {
        GoodsStock goodsStock = goodsStockMapper.selectGoodsStockByAddress(orderModel.getAddressId(), itemModel.getProductId(), itemModel.getSpecificationIds());
        if (goodsStock == null || goodsStock.getCurrentStock() == 0) {
            itemModel.setStatus(goodsStock != null && goodsStock.getCurrentStock() == 0 ? 2 : 1);
            if (itemModel.getUseVoucherCount() > 0) {
                setVoucherStatus(itemModel.getVoucherIds(), orderModel.getVoucher());
            }
            return null;
        }
        if (itemModel.getQuantity() > goodsStock.getCurrentStock()) {
            int invalid = itemModel.getQuantity() - goodsStock.getCurrentStock();
            itemModel.setInvalidCount(itemModel.getInvalidCount() + invalid);
            if (itemModel.getUseVoucherCount() > goodsStock.getCurrentStock()) {
                itemModel.setUseVoucherCount(goodsStock.getCurrentStock());
                List<String> voucherIds = itemModel.getVoucherIds().subList(0, itemModel.getUseVoucherCount() - goodsStock.getCurrentStock());
                setVoucherStatus(voucherIds, orderModel.getVoucher());
            }
        }
        itemModel.setStorehouseId(goodsStock.getStorehouseId());
        return goodsStock.getStorehouseId();
    }

    private void setVoucherStatus( List<String> voucherIds,  List<VoucherModel> voucherModels) {
        for (String voucherId : voucherIds) {
            for (VoucherModel voucherModel : voucherModels) {
                if (voucherId.equals(voucherModel.getVoucherId())) {
                    voucherModel.setStatus(-1);
                }
            }
        }
    }

    /**
     * 判断套餐可否配送及库存
     *
     * @param itemModel
     * @param addressId
     * @return
     * @date 2019年8月8日 下午4:37:59
     */
    private String mealDeliverAndStock(OrderItemModel itemModel, String addressId) {
        MealStock stock = mealStockMapper.selectMealStockByAddress(itemModel.getProductId(), addressId);
        if (stock == null || stock.getCurrentStock() == 0) {
            itemModel.setInvalidCount(itemModel.getQuantity());
            itemModel.setStatus(stock != null && stock.getCurrentStock() == 0 ? 2 : 1);
            return null;
        }
        if (itemModel.getQuantity() > stock.getCurrentStock()) {
            itemModel.setInvalidCount(itemModel.getQuantity() - stock.getCurrentStock());
        }
        itemModel.setStorehouseId(stock.getStorehouseId());
        return stock.getStorehouseId();
    }

    /**
     * 结算商品（包括菜品、商品券、套餐）
     *
     * @param itemList
     * @param voucherList
     * @date 2019年7月5日 上午11:41:32
     */
    public void calcProduct(List<OrderItemModel> itemList, List<VoucherModel> voucherList) {
        for (OrderItemModel item : itemList) {
            // 以防前端提交过来 默认值0才行
            item.setUseVoucherCount(0);
            item.setInvalidCount(0);
            String productType = item.getProductType().toString();
            switch (productType) {
                case Contacts.TYPE_OF_GOODS:
                    calcGoods(item);
                    break;
                case Contacts.TYPE_OF_VOUCHER:
                    calcVoucher(item);
                    break;
                case Contacts.TYPE_OF_MEAL:
                    calcMeal(item);
                    break;
                default:
                    item.setStatus(1);
                    break;
            }
        }
        useGoodsVoucher(itemList, voucherList);
    }

    /**
     * 结算商品
     *
     * @param item
     */
    private void calcGoods(OrderItemModel item) {
        String goodsId = item.getProductId();
        String specIds = item.getSpecificationIds();
        if (StrUtil.isEmpty(specIds)) {
            Map<String, String> defaultSpecMap = typeSpecMapper.selectDefaultByGoods(goodsId);
            if (defaultSpecMap != null) {
                specIds = defaultSpecMap.getOrDefault("specIds", null);
            }
        }
        GoodsInfo goodsInfo = goodsInfoMapper.selectInfoById(goodsId, specIds);
        if (ObjectUtil.isNull(goodsInfo) || !GoodsStatus.ONLINE.equals(goodsInfo.getServerStatus())) {
            item.setStatus(3);
            item.setInvalidCount(item.getQuantity());
            return;
        }
        item.setProductName(goodsInfo.getGoodsName());
        // 当前销售价格
        item.setSalePrice(goodsInfo.getSalePrice());
        // 商品原价 并非数据库中的成本价
        item.setBasePrice(goodsInfo.getBasePrice());
    }

    /**
     * 结算菜品券
     *
     * @param item
     */
    private void calcVoucher(OrderItemModel item) {
        String productId = item.getProductId();
        int quantity = item.getQuantity();
        GoodsVoucher voucher = goodsVoucherMapper.selectInfoById(productId);
        if (ObjectUtil.isNull(voucher) || Boolean.FALSE.equals(voucher.getServerStatus())) {
            item.setStatus(3);
            item.setInvalidCount(quantity);
            return;
        }
        if (voucher.getCurrentStock() < 1) {
            // 没有库存
            item.setInvalidCount(quantity);
            item.setStatus(2);
            return;
        }
        if (quantity > voucher.getCurrentStock()) {
            // 需要购买的数量超出库存数量
            item.setInvalidCount(quantity - voucher.getCurrentStock());
        }
        // 当前销售价格
        BigDecimal salePrice = voucher.getPrice();
        // 商品原价 并非数据库中的成本价
        BigDecimal basePrice = voucher.getBasePrice();
        item.setSalePrice(voucher.getPrice());
        item.setProductName(voucher.getVoucherName());
        item.setSalePrice(salePrice);
        item.setBasePrice(basePrice);
    }

    /**
     * 结算套餐
     *
     * @param item
     * @date 2019年10月30日 下午5:24:02
     */
    private void calcMeal(OrderItemModel item) {
        String productId = item.getProductId();
        MealInfo meal = mealInfoMapper.selectByPrimaryKey(productId);
        if (Boolean.FALSE.equals(meal.getServerStatus())) {
            item.setInvalidCount(item.getQuantity());
            item.setStatus(3);
            return;
        }
        item.setProductName(meal.getTheme());
        // 当前销售价格
        item.setSalePrice(meal.getSalePrice());
        // 商品原价 并非数据库中的成本价
        item.setBasePrice(meal.getSalePrice());
    }

    /**
     * 操作用户所选菜品券与所选商品进行对应
     *
     * @param itemList
     * @param voucherList
     * @date 2019年10月30日 下午5:23:29
     */
    private void useGoodsVoucher(List<OrderItemModel> itemList, List<VoucherModel> voucherList) {
        if (voucherList == null || voucherList.isEmpty()) {
            return;
        }
        List<String> vouchers = new LinkedList<>();
        for (VoucherModel voucher : voucherList) {
            vouchers.add(voucher.getVoucherId());
        }
        Result<Map<String, UserVoucher>> result = showOrderClient.mapVoucherByIds(vouchers);
        Map<String, UserVoucher> voucherMap = result.isSuccess() ? result.getData() : null;
        // 将用户所选择的菜品券先存到map中，以便直接获取
        Map<String, String> goodsVoucherMap = new HashMap<>(16);
        for (VoucherModel voucherModel : voucherList) {
            String voucherId = voucherModel.getVoucherId();
            UserVoucher voucher = voucherMap.getOrDefault(voucherId, null);
            if (ObjectUtil.isNull(voucher) || ObjectUtil.isNotNull(voucher.getUseTime())) {
                voucherModel.setStatus(-1);
                continue;
            }
            voucherModel.setVoucherName(voucher.getVoucherName());
            voucherModel.setSpecs(voucher.getSpecs());
            String goodsId = voucher.getProductId();
            String specIds = voucher.getSpecificationIds();
            String voucherMapKey = goodsId + "," + specIds;
            if (goodsVoucherMap.containsKey(voucherMapKey)) {
                String voucherIds = goodsVoucherMap.get(voucherMapKey) + "," + voucherId;
                goodsVoucherMap.put(voucherMapKey, voucherIds);
            } else {
                goodsVoucherMap.put(voucherMapKey, voucherId);
            }
        }
        // 将菜品券对应菜品券关联起来进行计算
        // 已使用的菜品券id
        List<String> usedVoucherIds = new LinkedList<>();
        List<String> usedVoucherMapList = new LinkedList<>();
        for (OrderItemModel itemModel : itemList) {
            if (itemModel.getProductType() != 0 || !itemModel.getStatus().equals(0)) {
                continue;
            }
            String goodsId = itemModel.getProductId();
            String specIds = itemModel.getSpecificationIds();
            String voucherMapKey = goodsId + "," + specIds;
            if (!goodsVoucherMap.containsKey(voucherMapKey)) {
                continue;
            }
            String voucherIds = goodsVoucherMap.get(voucherMapKey);
            String[] ids = voucherIds.split(",");
            if (itemModel.getQuantity() > ids.length) {
                itemModel.setUseVoucherCount(ids.length);
            } else {
                itemModel.setQuantity(ids.length);
            }
            itemModel.setVoucherIds(Arrays.asList(ids));
            usedVoucherMapList.add(voucherMapKey);
            usedVoucherIds.addAll(Arrays.asList(ids));
        }
        // 将多选出来的菜品券以兑换商品的形式添加到用户结算商品列表中
        for (String key : goodsVoucherMap.keySet()) {
            if (usedVoucherMapList.contains(key)) {
                continue;
            }
            String goodsId = key.split(",")[0];
            String specIds = key.split(",")[1];
            String voucherIds = goodsVoucherMap.get(key);
            String[] ids = voucherIds.split(",");
            GoodsInfo goodsInfo = goodsInfoMapper.selectInfoById(goodsId, specIds);
            if (ObjectUtil.isNotNull(goodsInfo) && GoodsStatus.ONLINE.equals(goodsInfo.getServerStatus())) {
                OrderItemModel orderItem = OrderItemModel.builder().productId(goodsId).productType(0)
                        .productName(goodsInfo.getGoodsName()).specificationIds(specIds)
                        .quantity(ids.length).basePrice(goodsInfo.getBasePrice()).salePrice(goodsInfo.getSalePrice())
                        .useVoucherCount(ids.length).invalidCount(0).status(0).voucherIds(Arrays.asList(ids)).build();
                itemList.add(orderItem);
                usedVoucherIds.addAll(Arrays.asList(ids));
            }
        }
        for (VoucherModel voucherModel : voucherList) {
            voucherModel.setStatus(-1);
            if (usedVoucherIds.contains(voucherModel.getVoucherId())) {
                voucherModel.setStatus(1);
            }
        }
    }

    /**
     * 特价结算
     *
     * @param orderModel
     * @param calcData
     * @return
     */
    private void calcSpecial(OrderModel orderModel, CalcData calcData) {
        Map<String, SpecialBase> specialMap = new HashMap<>(8);
        List<SpecialBase> specialBases = specialInfoMapper.selectFullPiece();
        specialBases.forEach(specialBase ->  specialMap.put(specialBase.getGoodsId(), specialBase) );
        BigDecimal total = calcData.getTotal();
        BigDecimal discount = calcData.getDiscount();
        for (OrderItemModel orderItemModel : orderModel.getItem()) {
            String goodsId = orderItemModel.getProductId();
            String productType = orderItemModel.getProductType().toString();
            int quantity = orderItemModel.getQuantity() - orderItemModel.getInvalidCount()
                    - orderItemModel.getUseVoucherCount();
            if (!Contacts.TYPE_OF_GOODS.equals(productType) || !specialMap.containsKey(goodsId)) {
                continue;
            }
            SpecialBase specialBase = specialMap.get(goodsId);
            int limitCount = ObjectUtil.isNull(specialBase.getLimitPrice()) ? 0 : specialBase.getLimitPrice().intValue();
            if (System.currentTimeMillis() - specialBase.getStartTime().getTime() < 0 || limitCount == 0
                    || quantity < limitCount) {
                continue;
            }
            BigDecimal salePrice = orderItemModel.getSalePrice();
            int typeOfPrice = 0;
            int typeOfDiscount = 1;
            BigDecimal discountPrice = BigDecimal.ZERO;
            if (specialBase.getPromotionMode()== typeOfPrice) {
                discountPrice = salePrice.compareTo(specialBase.getPrice()) > 0 ? specialBase.getPrice() : salePrice;

            } else if (specialBase.getPromotionMode() == typeOfDiscount) {
                discountPrice = salePrice.subtract(salePrice.multiply(specialBase.getDiscount()));
            }
            total = NumberUtil.sub(total, discountPrice);
            discount = NumberUtil.add(discount, discountPrice);
        }
        SpecialInfo specialInfo = specialInfoMapper.selectByTotal(total);
        if (ObjectUtil.isNotNull(specialInfo)) {
            total = total.subtract(specialInfo.getPrice());
            calcData.setLimitPrice(specialInfo.getLimitPrice());
            calcData.setLimitDiscount(specialInfo.getPrice());
        }
        calcData.setTotal(total);
        calcData.setDiscount(discount);
    }

    /**
     * 结算附属商品
     *
     * @param accessoryList
     * @return
     * @date 2019年7月5日 上午11:41:00
     */
    private BigDecimal calcAccessory(List<AccessoryModel> accessoryList) {
        if (accessoryList == null || accessoryList.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (AccessoryModel accessoryModel : accessoryList) {
            int quantity = accessoryModel.getQuantity();
            AccessoryInfo accessoryInfo = accessoryInfoMapper.selectByPrimaryKey(accessoryModel.getAccessoryId());
            if (Boolean.FALSE.equals(accessoryInfo.getServerStatus())) {
                accessoryModel.setStatus(3);
                continue;
            }
            if (accessoryInfo.getStockSize() < 1) {
                accessoryModel.setStatus(2);
            } else {
                accessoryModel.setName(accessoryInfo.getTheme());
                BigDecimal salePrice = accessoryInfo.getSalePrice();
                accessoryModel.setSalePrice(salePrice);
                total = total.add(salePrice.multiply(new BigDecimal(quantity)));
            }
        }
        return total;
    }

    /**
     * 结算加价购商品
     *
     * @param increaseList
     * @param addressId
     * @param calcData
     * @return
     */
    private List<String> calcIncrease(List<IncreaseModel> increaseList, String addressId, CalcData calcData) {
        if (increaseList == null || increaseList.isEmpty()) {
            return new ArrayList<>();
        }
        BigDecimal total = calcData.getTotal();
        BigDecimal discount = calcData.getDiscount();
        BigDecimal increaseTotal = BigDecimal.ZERO;
        List<String> deliveryList = new LinkedList<>();
        for (IncreaseModel increaseModel : increaseList) {
            SpecialInfo specialInfo = specialInfoMapper.selectByPrimaryKey(increaseModel.getIncreaseId());
            if (ObjectUtil.isNull(specialInfo)) {
                increaseModel.setStatus(3);
                continue;
            }
            String goodsId = increaseModel.getGoodsId();
            String specIds = null;
            Map<String, String> defaultSpecMap = typeSpecMapper.selectDefaultByGoods(goodsId);
            if (defaultSpecMap != null) {
                specIds = defaultSpecMap.getOrDefault("specIds", null);
            }
            GoodsStock goodsStock = goodsStockMapper.selectGoodsStockByAddress(addressId, goodsId, specIds);
            if (goodsStock == null) {
                increaseModel.setStatus(1);
                continue;
            } else if (goodsStock.getCurrentStock() == 0) {
                increaseModel.setStatus(2);
                continue;
            }
            if (total.compareTo(specialInfo.getLimitPrice()) < 0) {
                increaseModel.setStatus(4);
                continue;
            }
            increaseTotal = increaseTotal.add(specialInfo.getPrice());
            BigDecimal salePrice = specialItemMapper.selectGoodsSalePrice(goodsId, specIds);
            discount = NumberUtil.add(discount, NumberUtil.sub(salePrice, specialInfo.getPrice()));
            increaseModel.setStorehouseId(goodsStock.getStorehouseId());
            increaseModel.setStatus(0);
            // 该商品在非加价购中的售卖价格
            increaseModel.setBasePrice(salePrice);
            increaseModel.setSalePrice(specialInfo.getPrice());
            String goodsName = goodsInfoMapper.selectNameById(goodsId);
            increaseModel.setGoodsName(goodsName);
            deliveryList.add(goodsStock.getStorehouseId());
        }
        total = NumberUtil.add(total, increaseTotal);
        calcData.setTotal(total);
        calcData.setDiscount(discount);
        return deliveryList;
    }

    /**
     * 计算使用优惠券总额
     *
     * @param orderModel
     * @param calcData
     * @date 2019年7月5日 上午11:44:24
     */
    private void calcCoupon(OrderModel orderModel, CalcData calcData) {
        if (StrUtil.isBlank(orderModel.getCouponId())) {
            return;
        }
        Result<UserCoupon> result = showOrderClient.getCouponInfo(orderModel.getCouponId());
        UserCoupon coupon = result.isSuccess() ? result.getData() : null;
        if (coupon == null || coupon.getServerStatus() != 0 || ObjectUtil.isNotNull(coupon.getUseTime())) {
            orderModel.setCouponId(null);
            return;
        }
        BigDecimal total = calcData.getTotal();
        BigDecimal limitPrice = coupon.getLimitPrice();
        if (ObjectUtil.isNotNull(limitPrice) && total.compareTo(limitPrice) < 0) {
            orderModel.setCouponId(null);
            return;
        }
        BigDecimal couponDiscount = coupon.getCouponDiscount();
        BigDecimal couponPrice = coupon.getCouponPrice();
        if (ObjectUtil.isNotNull(couponDiscount)) {
            BigDecimal temp = NumberUtil.mul(total, couponDiscount);
            calcData.setCouponDiscount(NumberUtil.sub(total, temp));
            calcData.setTotal(temp);
        } else if (ObjectUtil.isNotNull(couponPrice)) {
            calcData.setCouponDiscount(couponPrice);
            calcData.setTotal(NumberUtil.sub(total, couponPrice));
        }
    }
}
