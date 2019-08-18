package com.szhengzhu.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.goods.MealItem;
import com.szhengzhu.bean.goods.MealServer;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.MealVo;
import com.szhengzhu.bean.wechat.vo.GoodsInfoVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.GoodsStockMapper;
import com.szhengzhu.mapper.MealImageMapper;
import com.szhengzhu.mapper.MealInfoMapper;
import com.szhengzhu.mapper.MealItemMapper;
import com.szhengzhu.mapper.MealJudgeMapper;
import com.szhengzhu.mapper.MealServerMapper;
import com.szhengzhu.service.MealService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.StringUtils;

@Service("mealService")
public class MealServiceImpl implements MealService {

    @Autowired
    private MealInfoMapper mealInfoMapper;

    @Autowired
    private MealItemMapper mealItemMapper;

    @Autowired
    private MealImageMapper mealImageMapper;

    @Autowired
    private MealJudgeMapper mealJudgeMapper;

    @Autowired
    private MealServerMapper mealServerMapper;
    
    @Resource
    private GoodsStockMapper goodsStockMapper;

    @Override
    public Result<?> addMeal(MealInfo base) {
        if (base == null || StringUtils.isEmpty(base.getTheme()))
            return new Result<>(StatusCode._4004);
        int count = mealInfoMapper.repeatRecords(base.getTheme(), "");
        if (count > 0)
            return new Result<>(StatusCode._4007);
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setMealNo(ShowUtils.createGoodsNo(2, base.getMarkId()));
        base.setServerStatus(false);
        mealInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> addItem(MealItem base) {
        if (base == null || StringUtils.isEmpty(base.getGoodsId())
                || StringUtils.isEmpty(base.getMealId())
                || StringUtils.isEmpty(base.getSpecificationIds()))
            return new Result<>(StatusCode._4004);
        int count = mealItemMapper.repeatRecords(base.getMealId(), base.getGoodsId(),
                base.getSpecificationIds(), "");
        if (count > 0)
            return new Result<>(StatusCode._4007);
        base.setMarkId(IdGenerator.getInstance().nexId());
        mealItemMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<MealInfo>> getPage(PageParam<MealInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("m." + base.getSidx() + " " + base.getSort());
        List<MealInfo> list = mealInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<MealInfo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<PageGrid<MealVo>> getItemPage(PageParam<MealItem> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        List<MealVo> list = mealItemMapper.selectByExampleSelective(base.getData());
        PageInfo<MealVo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> editMeal(MealInfo base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4008);
        int count = mealInfoMapper.repeatRecords(base.getTheme(), base.getMarkId());
        if (count > 0)
            return new Result<>(StatusCode._4007);
        mealInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<List<Combobox>> listCombobox() {
        List<Combobox> mealList = mealInfoMapper.selectCombobox();
        return new Result<>(mealList);
    }

    @Override
    public Result<?> editMealItem(MealItem base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4008);
        int count = mealItemMapper.repeatRecords(base.getMealId(), base.getGoodsId(),
                base.getSpecificationIds(), base.getMarkId());
        if (count > 0)
            return new Result<>(StatusCode._4007);
        mealItemMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> getMealById(String markId) {
        MealInfo mealInfo = mealInfoMapper.selectByPrimaryKey(markId);
        return new Result<>(mealInfo);
    }

    @Override
    public Result<?> getMealItemById(String markId) {
        return new Result<>(mealItemMapper.selectByPrimaryKey(markId));
    }

    @Override
    public Result<List<Combobox>> getMealList() {
        List<Combobox> list = mealInfoMapper.selectComboboxList();
        return new Result<>(list);
    }

    @Override
    public Result<PageGrid<MealInfo>> getPageByColumn(PageParam<MealInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("m." + base.getSidx() + " " + base.getSort());
        List<MealInfo> list = mealInfoMapper.selectByExampleSelectiveNotColumn(base.getData());
        PageInfo<MealInfo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<PageGrid<MealInfo>> getPageByLabel(PageParam<MealInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("m." + base.getSidx() + " " + base.getSort());
        List<MealInfo> list = mealInfoMapper.selectByExampleSelectiveNotLabel(base.getData());
        PageInfo<MealInfo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<PageGrid<MealInfo>> getPageBySpecial(PageParam<MealInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("m." + base.getSidx() + " " + base.getSort());
        List<MealInfo> list = mealInfoMapper.selectByExampleSelectiveNotSpecial(base.getData());
        PageInfo<MealInfo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<PageGrid<MealInfo>> getPageByIcon(PageParam<MealInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("m." + base.getSidx() + " " + base.getSort());
        List<MealInfo> list = mealInfoMapper.selectByExampleSelectiveNotIcon(base.getData());
        PageInfo<MealInfo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<GoodsInfoVo> getMealDetail(String mealId, String userId) {
        if (StringUtils.isEmpty(mealId))
            return new Result<>(StatusCode._4004);
        GoodsInfoVo mealInfo = mealInfoMapper.selectById(mealId); // meal info
        if (mealInfo == null)
            return new Result<>(StatusCode._4004);
        List<String> mealImgs = mealImageMapper.selectBigByMealId(mealId); // meal images
        mealInfo.setImagePaths(mealImgs);
        List<JudgeBase> judges = mealJudgeMapper.selectJudge(userId, mealId, 3); // meal judge
        mealInfo.setJudges(judges);
        return new Result<>(mealInfo);
    }

    @Override
    public Result<List<String>> getServerListInMeal(String mealId) {
        List<String> list = mealServerMapper.selectServerListByMeal(mealId);
        return new Result<>(list);
    }

    @Override
    public Result<?> addBatchMealServe(BatchVo base) {
        if (base == null)
            return new Result<>(StatusCode._4004);
        List<MealServer> list = new LinkedList<>();
        MealServer mealServer = null;
        IdGenerator idGenerator = IdGenerator.getInstance();
        for (String mealId : base.getIds()) {
            mealServer = new MealServer();
            mealServer.setMarkId(idGenerator.nexId());
            mealServer.setMealId(mealId);
            mealServer.setServerId(base.getCommonId());
        }
        if (list.size() > 0)
            mealServerMapper.insertBatch(list);
        return new Result<>();
    }

    @Override
    public Result<?> deleteBatchMealServe(BatchVo base) {
        if (base == null)
            return new Result<>(StatusCode._4004);
        mealServerMapper.deletetBatch(base);
        return new Result<>();
    }

    @Override
    public Result<StockBase> getStockInfo(String mealId, String addressId) {
        StockBase stockBase = new StockBase();
        MealInfo mealInfo = mealInfoMapper.selectByPrimaryKey(mealId);
        stockBase.setGoodsId(mealId);
        stockBase.setGoodsNo(mealInfo.getMealNo());
        stockBase.setBasePrice(mealInfo.getSalePrice()); // 即售卖价
        stockBase.setSalePrice(mealInfo.getSalePrice());  // 当前售卖价
        stockBase.setIsDelivery(true);
        stockBase.setCurrentStock(mealInfo.getStockSize());
        List<MealItem> mealItemList = mealItemMapper.selectItemByMeal(mealId);
        if (mealItemList.size() == 0 || !mealInfo.getServerStatus() || mealInfo.getStockSize().intValue() < 1) {
            stockBase.setIsDelivery(false);
            return new Result<>(stockBase);
        }
        if (!StringUtils.isEmpty(addressId)) {
            for (int i = 0, j = mealItemList.size(); i < j; i++) {
                MealItem mealItem = mealItemList.get(i);
                Map<String, Integer> deliveryStock = goodsStockMapper.selectDeliveryAndStock(addressId, mealItem.getGoodsId(), mealItem.getSpecificationIds());
                if (deliveryStock != null) {
                    boolean isDelivery = deliveryStock.containsKey("isDelivery")? ((Number) deliveryStock.get("isDelivery")).intValue() == 1 : false;
                    int currentStock = deliveryStock.containsKey("currentStock")? ((Number) deliveryStock.get("currentStock")).intValue() : 1;
                    if (!isDelivery) {
                        stockBase.setIsDelivery(false);
                        return new Result<>(stockBase);
                    }
                    if (currentStock < 1) {
                        stockBase.setCurrentStock(0);
                        return new Result<>(stockBase);
                    }
                } else {
                    return new Result<>(stockBase);
                }
            }
        }
        return new Result<>(stockBase) ;
    }
}
