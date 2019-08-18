package com.szhengzhu.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.PurchaseHistory;
import com.szhengzhu.bean.goods.PurchaseInfo;
import com.szhengzhu.bean.vo.FoodCount;
import com.szhengzhu.bean.vo.PurchaseFood;
import com.szhengzhu.bean.vo.PurchaseHistoryVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.PurchaseHistoryMapper;
import com.szhengzhu.mapper.PurchaseInfoMapper;
import com.szhengzhu.service.PurchaseService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseInfoMapper purchaseInfoMapper;

    @Autowired
    private PurchaseHistoryMapper purchaseHistoryMapper;

    @Override
    public Result<PageGrid<PurchaseFood>> getPurchasePage(PageParam<PurchaseInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("p." + base.getSidx() + " " + base.getSort());
        List<PurchaseFood> list = purchaseInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<PurchaseFood> page = new PageInfo<>(list);
        PageGrid<PurchaseFood> grid = new PageGrid<>(page);
        return new Result<>(grid);
    }

    @Override
    public Result<PageGrid<PurchaseHistoryVo>> getHistoryPage(PageParam<PurchaseHistory> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("h."+base.getSidx() + " " + base.getSort());
        List<PurchaseHistoryVo> list = purchaseHistoryMapper.selectByExampleSelective(base.getData());
        PageInfo<PurchaseHistoryVo> page = new PageInfo<>(list);
        PageGrid<PurchaseHistoryVo> grid = new PageGrid<>(page);
        return new Result<>(grid);
    }

    @Override
    public Result<?> appoinStaff(String markId, String userId) {
        purchaseInfoMapper.updateInfoByIdAndStatus(markId, userId);
        return new Result<>();
    }

    @Override
    public Result<?> revokeStaff(String markId) {
        purchaseInfoMapper.updateStatusByIdAndStatus(markId);
        return new Result<>();
    }

    @Transactional
    public Result<?> buyFood(PurchaseInfo base) {
        // 1.确认购买更新采购单(id,购买量buyTotal，userId)
        BigDecimal buyTotal = base.getBuyTotal() == null ? new BigDecimal(0) : base.getBuyTotal();
        if (buyTotal.compareTo(new BigDecimal(0)) == 1) {
            PurchaseInfo temp = purchaseInfoMapper.selectByPrimaryKey(base.getMarkId());
            if (temp != null) {
                BigDecimal alreadyBuyTotal = temp.getBuyTotal() == null ? new BigDecimal(0) : temp.getBuyTotal();
                temp.setBuyTotal(alreadyBuyTotal.add(buyTotal));//已经的量+当前的量
                temp.setServerStatus(0);
                purchaseInfoMapper.updateBuyTotal(temp);
                // 采购记录
                PurchaseHistory history = new PurchaseHistory();
                history.setMarkId(IdGenerator.getInstance().nexId());
                history.setBuyTime(TimeUtils.today());
                history.setPurchaseVolume(base.getBuyTotal());
                history.setFoodId(temp.getFoodId());
                history.setUserId(base.getUserId());
                purchaseHistoryMapper.insertSelective(history);
            }
        }
        return new Result<>();
    }

    @Override
    public Result<?> deletePurchaseOrder(String buyTime) {
        //删除昨天时间采购单
        purchaseInfoMapper.deletePurchaseOrders(buyTime);
        return new Result<>();
    }

    @Transactional
    public Result<?> reflashPurchaseOrder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String buyTime = format.format(calendar.getTime());//采购日期
        //获取支付配货时的订单物品数量列表
        List<FoodCount> list = purchaseInfoMapper.selectTodayList();
        List<PurchaseInfo> purchaseList = new LinkedList<PurchaseInfo>();
        PurchaseInfo info = null;
        FoodCount food = null;
        IdGenerator idGenerator = IdGenerator.getInstance();
        for (int index = 0, len = list.size(); index < len; index++) {
            food = list.get(index);
            info = new PurchaseInfo();
            info.setMarkId(idGenerator.nexId());
            info.setPurchaseTotal(food.getTotalCount());
            info.setBuyTotal(new BigDecimal(0));
            info.setBuyTime(calendar.getTime());
            info.setReflashTime(calendar.getTime());
            info.setServerStatus(0);
            info.setFoodId(food.getFoodId());
            purchaseList.add(info);
        }
        if (purchaseList.size() > 0)
            purchaseInfoMapper.insertBatch(purchaseList);
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reflashTime = format.format(calendar.getTime());
        //删除还未采购的今日采购订单
        purchaseInfoMapper.deleteBatchPurchase(buyTime, reflashTime);
        //更新已经采购的，为无需再采购的（所需采购总量变为0）
        purchaseInfoMapper.updateBatchPurchase(buyTime, reflashTime);
        return new Result<>();
    }
    
    // @Transactional
    // public Result<?> createPurchaseOrder() {
    // CountMap total = new CountMap();
    // List<FoodCount> list = new ArrayList<>();
    // FoodCount food = null;
    // // 1普通订单
    // getOrderFoodCount(total, list, food);
    // // 2团购订单
    // getTeamFoodCount(total, list, food);
    // // 3秒杀订单
    // getSeckillFoodCount(total, list, food);
    // List<PurchaseInfo> purchaseList = new LinkedList<PurchaseInfo>();
    // PurchaseInfo info = null;
    // IdGenerator idGenerator = IdGenerator.getInstance();
    // for (String foodId : total.keySet()) {
    // info = new PurchaseInfo();
    // info.setMarkId(idGenerator.nexId());
    // info.setPurchaseTotal(total.get(foodId));
    // info.setBuyTotal(new BigDecimal(0));
    // info.setBuyTime(TimeUtils.today());
    // info.setReflashTime(TimeUtils.today());
    // info.setServerStatus(0);
    // info.setFoodId(foodId);
    // purchaseList.add(info);
    // }
    // if (purchaseList.size() > 0)
    // purchaseInfoMapper.insertBatch(purchaseList);
    // return new Result<>();
    // }
    //
    // private void getSeckillFoodCount(CountMap total, List<FoodCount> list,
    // FoodCount food) {
    // // 1.1 获取秒杀商品食材量
    // list = purchaseInfoMapper.selectCountInGoodsBySeckill();
    // getFoodCount(total, list, food);
    // // 1.2 获取套餐单品食材量
    // list = purchaseInfoMapper.selectCountInMealBySeckill();
    // getFoodCount(total, list, food);
    // }
    //
    // private void getTeamFoodCount(CountMap total, List<FoodCount> list, FoodCount
    // food) {
    // // 1.1 获取普通商品食材量
    // list = purchaseInfoMapper.selectCountInGoodsByTeam();
    // getFoodCount(total, list, food);
    // // 1.2 获取套餐的食材量
    // list = purchaseInfoMapper.selectCountInMealByTeam();
    // getFoodCount(total, list, food);
    // }
    //
    // private void getOrderFoodCount(CountMap total, List<FoodCount> list,
    // FoodCount food) {
    // // 1.1 获取普通商品食材量
    // list = purchaseInfoMapper.selectCountInGoods();
    // getFoodCount(total, list, food);
    // // 1.2 获取附属品数量
    // list = purchaseInfoMapper.selectCountInAccessory();
    // getFoodCount(total, list, food);
    // // 1.3获取套餐的中单品食材的数量
    // list = purchaseInfoMapper.selectCountInMeal();
    // getFoodCount(total, list, food);
    // }
    //
    // private void getFoodCount(CountMap total, List<FoodCount> list, FoodCount
    // food) {
    // if (list.size() > 0) {
    // for (int index = 0; index < list.size(); index++) {
    // food = list.get(index);
    // total.put(food.getFoodId(), food.getTotalCount());
    // }
    // }
    // }

    @Transactional
    public Result<?> createPurchaseOrder() {
        //删除前一日时间采购单
        purchaseInfoMapper.deletePurchaseOrders(TimeUtils.getOverdue(-1));
        //获取支付配货时的订单物品数量列表
        List<FoodCount> list = purchaseInfoMapper.selectTodayList();
        List<PurchaseInfo> purchaseList = new LinkedList<PurchaseInfo>();
        PurchaseInfo info = null;
        FoodCount food = null;
        IdGenerator idGenerator = IdGenerator.getInstance();
        for (int index = 0, len = list.size(); index < len; index++) {
            food = list.get(index);
            info = new PurchaseInfo();
            info.setMarkId(idGenerator.nexId());
            info.setPurchaseTotal(food.getTotalCount());
            info.setBuyTotal(new BigDecimal(0));
            info.setBuyTime(TimeUtils.today());
            info.setReflashTime(TimeUtils.today());
            info.setServerStatus(0);
            info.setFoodId(food.getFoodId());
            purchaseList.add(info);
        }
        if (purchaseList.size() > 0)
            purchaseInfoMapper.insertBatch(purchaseList);
        return new Result<>();
    }

    @Override
    public Result<List<PurchaseFood>> getListByStatus(String userId, Integer status) {
        if(StringUtils.isEmpty(userId) || status == null){
            return new Result<>(StatusCode._4008);
        }
        List<PurchaseFood> list = purchaseInfoMapper.selectListByStatus(userId,status);
        return new Result<>(list);
    }
}
