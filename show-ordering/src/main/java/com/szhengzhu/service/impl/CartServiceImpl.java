package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.bean.member.IntegralDetail;
import com.szhengzhu.bean.ordering.*;
import com.szhengzhu.bean.xwechat.param.CartParam;
import com.szhengzhu.bean.xwechat.vo.DetailModel;
import com.szhengzhu.code.IntegralCode;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.code.TableStatus;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service("cartService")
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private TableMapper tableMapper;

    @Resource
    private CommodityMapper commodityMapper;

    @Resource
    private MarketInfoMapper marketInfoMapper;

    @Resource
    private MarketCommodityMapper marketCommodityMapper;

    @Resource
    private CommodityItemMapper commodityItemMapper;

    @Resource
    private CommodityPriceMapper commodityPriceMapper;

    @Resource
    private ShowMemberClient showMemberClient;

    @Transactional
    @Override
    public String add(CartParam cartParam) {
        Cart newCart = createCart(cartParam);
        checkMemberIntegral(cartParam.getUserId(), 0, newCart.getIntegralPrice(), newCart.getQuantity());
        Cart cart = cartMapper.selectByTableComm(cartParam.getTableId(), cartParam.getCommodityId(), cartParam.getPriceId(),
                cartParam.getSpecsItems(), cartParam.getUserId());
        String markId;
        if (ObjectUtil.isNotNull(cart)) {
            markId = cart.getMarkId();
            cart.setQuantity(cart.getQuantity() + cartParam.getQuantity());
            cart.setModifyTime(DateUtil.date());
            cartMapper.deleteByPrimaryKey(cart.getMarkId());
        } else {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            markId = snowflake.nextIdStr();
            cart = newCart;
            cart.setMarkId(markId);
            cart.setCreateTime(DateUtil.date());
        }
        cartMapper.insertSelective(cart);
        consumeMemberIntegral(newCart.getPriceType(), newCart.getUserId(), newCart.getIntegralPrice(), newCart.getQuantity());
        return markId;
    }

    /**
     * 退还积分
     *
     * @param userId
     * @param integralPrice
     * @param quantity
     */
    private void addMemberIntegral(int priceType, String userId, int integralPrice, int quantity) {
        if (priceType != 1) {
            return;
        }
        IntegralDetail detail = IntegralDetail.builder().userId(userId)
                .integralLimit(integralPrice * quantity).status(1).build();
        detail.setIntegralType(IntegralCode.EXCHANGE_WITHDRAW);
        ShowAssert.checkSuccess(showMemberClient.addIntegral(detail));
    }

    /**
     * 消费积分
     *
     * @param userId
     * @param integralPrice
     * @param quantity
     */
    private void consumeMemberIntegral(int priceType, String userId, int integralPrice, int quantity) {
        if (priceType != 1) {
            return;
        }
        IntegralDetail detail = IntegralDetail.builder().userId(userId)
                .integralLimit(integralPrice * quantity).status(1).build();
        detail.setIntegralType(IntegralCode.EXCHANGE);
        ShowAssert.checkSuccess(showMemberClient.integralConsume(detail));
    }

    /**
     * 判断积分是否够
     *
     * @param userId
     * @param newCommodityPrice
     * @param quantity
     */
    private void checkMemberIntegral(String userId, int oldPriceTotal, int newCommodityPrice, int quantity) {
        Result<Integer> result = showMemberClient.getIntegralTotalByUserId(userId);
        int total = result.isSuccess() ? result.getData() : 0;
        ShowAssert.checkTrue(total + oldPriceTotal < newCommodityPrice * quantity, StatusCode._4057);
    }

    /**
     * 判断桌台状态，生成购物车商品
     *
     * @param cartParam
     * @return
     */
    private Cart createCart(CartParam cartParam) {
        checkTableStatus(cartParam.getTableId());
        Commodity commodity = commodityMapper.selectByPrimaryKey(cartParam.getCommodityId());
        ShowAssert.checkTrue(commodity.getQuantity() != null && commodity.getQuantity() <= 0, StatusCode._5003);
        CommodityPrice commodityPrice = commodityPriceMapper.selectByIdOrDefault(cartParam.getPriceId());
        int priceType = commodityPrice.getPriceType();
        BigDecimal markupPrice = commodityItemMapper.sumItemPrice(commodity.getMarkId(), cartParam.getSpecsItems());
        BigDecimal price = commodityPrice.getSalePrice().add(markupPrice);
        return Cart.builder().tableId(cartParam.getTableId()).storeId(commodity.getStoreId()).userId(cartParam.getUserId())
                .commodityType(commodity.getType()).commodityId(commodity.getMarkId()).commodityName(commodity.getName())
                .priceId(cartParam.getPriceId()).specsItems(cartParam.getSpecsItems()).costPrice(price).priceType(priceType)
                .salePrice(priceType == 0 ? price : BigDecimal.ZERO)
                .integralPrice(priceType == 1 ? commodityPrice.getIntegralPrice() : 0)
                .quantity(cartParam.getQuantity())
                .build();
    }

    /**
     * 未下单、准备买单、已结账都不可以添加购物车(添加/修改购物车)
     *
     * @param tableId
     */
    private void checkTableStatus(String tableId) {
        Table table = tableMapper.selectByPrimaryKey(tableId);
        ShowAssert.checkTrue(TableStatus.FREEING.code.equals(table.getTableStatus()), StatusCode._5025);
        ShowAssert.checkTrue(TableStatus.PAID.code.equals(table.getTableStatus()), StatusCode._4053);
        ShowAssert.checkTrue(TableStatus.BILL.code.equals(table.getTableStatus()), StatusCode._4053);
    }

    @Transactional
    @Override
    public void modify(CartParam cartParam) {
        Cart cart = cartMapper.selectByPrimaryKey(cartParam.getCartId());
        if (cart == null) {
            return;
        }
        ShowAssert.checkTrue(cart.getPriceType().equals(1) && !cart.getUserId().equals(cartParam.getUserId()), StatusCode._4058);
        Cart newCart = createCart(cartParam);
        checkMemberIntegral(cartParam.getUserId(), cart.getIntegralPrice() * cart.getQuantity(),
                newCart.getIntegralPrice(), newCart.getQuantity());
        cartMapper.deleteByPrimaryKey(cart.getMarkId());
        addMemberIntegral(cart.getPriceType(), cartParam.getUserId(), cart.getIntegralPrice(), cart.getQuantity());
        if (cartParam.getQuantity() == 0) {
            return;
        }
        cart.setPriceId(cartParam.getPriceId());
        cart.setSpecsItems(cartParam.getSpecsItems());
        cart.setCostPrice(newCart.getCostPrice());
        cart.setPriceType(newCart.getPriceType());
        cart.setSalePrice(newCart.getSalePrice());
        cart.setIntegralPrice(newCart.getIntegralPrice());
        cart.setQuantity(cartParam.getQuantity());
        cartMapper.insertSelective(cart);
        consumeMemberIntegral(cart.getPriceType(), cart.getUserId(), cart.getIntegralPrice(), cartParam.getQuantity());
    }

    @Override
    public void clearCart(String tableId) {
        List<Cart> carts = cartMapper.selectByTable(tableId);
        for (Cart cart : carts) {
            addMemberIntegral(cart.getPriceType(), cart.getUserId(), cart.getIntegralPrice(), cart.getQuantity());
        }
        cartMapper.clearCartByTable(tableId);
    }

    @Override
    public Map<String, Object> list(String tableId) {
        Map<String, Object> resultMap = new HashMap<>(8);
        List<DetailModel> detailList = cartMapper.selectVoByTable(tableId);
        BigDecimal saleTotalPrice = BigDecimal.ZERO;
        for (DetailModel detailModel : detailList) {
            saleTotalPrice = saleTotalPrice
                    .add(detailModel.getSalePrice().multiply(new BigDecimal(detailModel.getQuantity())));
        }
        BigDecimal discount = calcDiscount(tableId, detailList);
        resultMap.put("detailList", detailList);
        resultMap.put("discount", discount);
        resultMap.put("saleTotal", NumberUtil.sub(saleTotalPrice, discount));
        return resultMap;
    }

    /**
     * 类似第二杯半价
     *
     * @param tableId
     * @param detailList
     * @return
     */
    private BigDecimal calcDiscount(String tableId, List<DetailModel> detailList) {
        BigDecimal discount = BigDecimal.ZERO;
        List<MarketInfo> marketList = marketInfoMapper.selectCartMarket(tableId);
        for (MarketInfo marketInfo : marketList) {
            int discountNum = (marketInfo.getQuantity() / marketInfo.getBuyQuantity()) * marketInfo.getDiscountQuantity();
            List<String> commIds = marketCommodityMapper.selectCommIds(marketInfo.getMarkId(), 0);
            // 不包含在优惠里的商品
            List<DetailModel> modelList = detailList.stream().filter(detail -> commIds.contains(detail.getCommodityId()) || detail.getPriceType() != 1).collect(Collectors.toList());
            for (DetailModel detailModel : modelList) {
                BigDecimal diffPrice = BigDecimal.ZERO;
                BigDecimal salePrice = detailModel.getSalePrice();
                if ("0".equals(String.valueOf(marketInfo.getDiscountType()))) {
                    diffPrice = NumberUtil.sub(salePrice, marketInfo.getAmount());
                } else if ("1".equals(String.valueOf(marketInfo.getDiscountType()))) {
                    BigDecimal currDiscount = NumberUtil.sub(1, NumberUtil.div(marketInfo.getAmount(), 10));
                    diffPrice = NumberUtil.mul(salePrice, currDiscount);
                }
                int quantity;
                if (detailModel.getQuantity() > discountNum) {
                    quantity = discountNum;
                    discountNum = 0;
                } else {
                    quantity = detailModel.getQuantity();
                    discountNum = discountNum - quantity;
                }
                discount = NumberUtil.add(discount, NumberUtil.mul(diffPrice, quantity));
                if (discountNum == 0) {
                    break;
                }
            }
        }
        return NumberUtil.round(discount, 2, RoundingMode.DOWN);
    }
}
