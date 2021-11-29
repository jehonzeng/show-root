package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.excel.GoodsCount;
import com.szhengzhu.bean.excel.MealGoodsModel;
import com.szhengzhu.bean.goods.PurchaseHistory;
import com.szhengzhu.bean.goods.PurchaseInfo;
import com.szhengzhu.bean.vo.FoodCount;
import com.szhengzhu.bean.vo.PurchaseFood;
import com.szhengzhu.bean.vo.PurchaseHistoryVo;
import com.szhengzhu.bean.vo.TodayProductVo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.GoodsInfoMapper;
import com.szhengzhu.mapper.MealInfoMapper;
import com.szhengzhu.mapper.PurchaseHistoryMapper;
import com.szhengzhu.mapper.PurchaseInfoMapper;
import com.szhengzhu.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author terry
 */
@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {

    @Resource
    private PurchaseInfoMapper purchaseInfoMapper;

    @Resource
    private PurchaseHistoryMapper purchaseHistoryMapper;

    @Resource
    private MealInfoMapper mealInfoMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public PageGrid<PurchaseFood> getPurchasePage(PageParam<PurchaseInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("p." + base.getSidx() + " " + base.getSort());
        List<PurchaseFood> list = purchaseInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<PurchaseFood> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public PageGrid<PurchaseHistoryVo> getHistoryPage(PageParam<PurchaseHistory> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("h.buy_time desc,h." + base.getSidx() + " " + base.getSort());
        List<PurchaseHistoryVo> list = purchaseHistoryMapper
                .selectByExampleSelective(base.getData());
        PageInfo<PurchaseHistoryVo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public void appoinStaff(String markId, String userId) {
        purchaseInfoMapper.updateInfoByIdAndStatus(markId, userId);
    }

    @Override
    public void revokeStaff(String markId) {
        purchaseInfoMapper.updateStatusByIdAndStatus(markId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void buyFood(PurchaseInfo base) {
        // 1.确认购买更新采购单(id,购买量buyTotal，userId)
        BigDecimal buyTotal = ObjectUtil.isNull(base.getBuyTotal()) ? new BigDecimal(0) : base.getBuyTotal();
        if (buyTotal.compareTo(BigDecimal.ZERO) < 1) {
            return;
        }
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        PurchaseInfo temp = purchaseInfoMapper.selectByPrimaryKey(base.getMarkId());
        if (temp != null) {
            BigDecimal alreadyBuyTotal = ObjectUtil.isNull(temp.getBuyTotal()) ? new BigDecimal(0)
                    : temp.getBuyTotal();
            // 已经的量+当前的量
            temp.setBuyTotal(alreadyBuyTotal.add(buyTotal));
            temp.setServerStatus(0);
            purchaseInfoMapper.updateBuyTotal(temp);
            // 采购记录
            PurchaseHistory history = PurchaseHistory.builder().markId(snowflake.nextIdStr()).buyTime(DateUtil.date())
                    .purchaseVolume(base.getBuyTotal()).foodId(temp.getFoodId()).userId(base.getUserId()).build();
            purchaseHistoryMapper.insertSelective(history);
        }
    }

    @Override
    public void deletePurchaseOrder(String buyTime) {
        purchaseInfoMapper.deletePurchaseOrders(buyTime);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refreshPurchaseOrder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 采购日期
        String buyTime = format.format(calendar.getTime());
        // 获取支付配货时的订单物品数量列表
        List<FoodCount> list = purchaseInfoMapper.selectTodayList();
        List<PurchaseInfo> purchaseList = new LinkedList<>();
        PurchaseInfo info;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (FoodCount food : list) {
            info = PurchaseInfo.builder().markId(snowflake.nextIdStr()).purchaseTotal(food.getTotalCount()).buyTotal(new BigDecimal(0))
                    .buyTime(calendar.getTime()).reflashTime(calendar.getTime()).serverStatus(0).foodId(food.getFoodId()).build();
            purchaseList.add(info);
        }
        if (!purchaseList.isEmpty()) {
            purchaseInfoMapper.insertBatch(purchaseList);
        }
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reflashTime = format.format(calendar.getTime());
        // 删除还未采购的今日采购订单
        purchaseInfoMapper.deleteBatchPurchase(buyTime, reflashTime);
        // 更新已经采购的，为无需再采购的（所需采购总量变为0）
        purchaseInfoMapper.updateBatchPurchase(buyTime, reflashTime);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createPurchaseOrder() {
        // 获取支付、配货时的订单物品数量列表
        List<FoodCount> list = purchaseInfoMapper.selectTodayList();
        List<PurchaseInfo> purchaseList = new LinkedList<>();
        PurchaseInfo info;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        Date time = DateUtil.date();
        for (FoodCount food : list) {
            info = PurchaseInfo.builder().markId(snowflake.nextIdStr()).purchaseTotal(food.getTotalCount()).buyTotal(new BigDecimal(0))
                    .buyTime(time).reflashTime(time).serverStatus(0).foodId(food.getFoodId()).build();
            purchaseList.add(info);
        }
        if (!purchaseList.isEmpty()) {
            purchaseInfoMapper.insertBatch(purchaseList);
        }
    }

    @Override
    public List<PurchaseFood> getListByStatus(String userId, Integer status) {
        return purchaseInfoMapper.selectListByStatus(userId, status);
    }

    @Override
    public List<MealGoodsModel> getProductList(List<TodayProductVo> list) {
        GoodsCount goodsCount = new GoodsCount();
        String productId;
        int type;
        for (TodayProductVo orderItem : list) {
            productId = orderItem.getProductId();
            type = orderItem.getProductType();
            // 过滤掉单品中非菜品
            if (Boolean.FALSE.equals(isDishes(productId, type))) {
                continue;
            }
            // 统计菜品和套餐
            goodsCount.put(productId, orderItem.getProductName(), type, orderItem.getQuantity());
        }
        List<MealGoodsModel> productList = new LinkedList<>();
        MealGoodsModel item;
        for (String id : goodsCount.keySet()) {
            item = MealGoodsModel.builder().productName(goodsCount.getProductName(id)).quantity(goodsCount.getCount(id))
                    .goods(mealInfoMapper.selectGoodsByMealId(id)).productType(goodsCount.getProductType(id)).build();
            productList.add(item);
        }
        return productList;
    }

    /**
     * 检验是否是菜品
     *
     * @param id
     * @return
     * @date 2019年9月16日
     */
    private Boolean isDishes(String id, int type) {
        // 通过商品Id查询类别名称
        String name = goodsInfoMapper.selectDishesByCategoryId(id);
        return type == 0 && Contacts.NAME.equals(name);
    }

}
