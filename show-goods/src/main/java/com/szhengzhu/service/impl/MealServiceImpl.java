package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.goods.MealItem;
import com.szhengzhu.bean.goods.MealServer;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.MealVo;
import com.szhengzhu.bean.wechat.vo.GoodsDetail;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.MealService;
import com.szhengzhu.util.ShowUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 */
@Service("mealService")
public class MealServiceImpl implements MealService {

    @Resource
    private MealInfoMapper mealInfoMapper;

    @Resource
    private MealItemMapper mealItemMapper;

    @Resource
    private MealImageMapper mealImageMapper;

    @Resource
    private MealJudgeMapper mealJudgeMapper;

    @Resource
    private MealServerMapper mealServerMapper;

    @Resource
    private Redis redis;

    private final ConcurrentHashMap<String, Boolean> lockIdMap = new ConcurrentHashMap<>();

    @Override
    public MealInfo addMeal(MealInfo base) {
        int count = mealInfoMapper.repeatRecords(base.getTheme(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        base.setMarkId(markId);
        base.setMealNo(ShowUtils.createGoodsNo(2, base.getMarkId()));
        base.setServerStatus(false);
        base.setCreateTime(DateUtil.date());
        mealInfoMapper.insertSelective(base);
        return base;
    }

    private void mealChange(String mealId) {
        redis.del("goods:meal:detail:" + mealId);
    }

    @Override
    public MealItem addItem(MealItem base) {
        int count = mealItemMapper.repeatRecords(base.getMealId(), base.getGoodsId(), base.getSpecificationIds(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        mealItemMapper.insertSelective(base);
        return base;
    }

    @Override
    public PageGrid<MealInfo> getPage(PageParam<MealInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("m.create_time desc,m." + base.getSidx() + " " + base.getSort());
        List<MealInfo> list = mealInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<MealInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public PageGrid<MealVo> getItemPage(PageParam<MealItem> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<MealVo> list = mealItemMapper.selectByExampleSelective(base.getData());
        PageInfo<MealVo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public MealInfo editMeal(MealInfo base) {
        int count = mealInfoMapper.repeatRecords(base.getTheme(), base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        mealInfoMapper.updateByPrimaryKeySelective(base);
        mealChange(base.getMarkId());
        return base;
    }

    @Override
    public List<Combobox> listCombobox() {
        return mealInfoMapper.selectCombobox();
    }

    @Override
    public MealItem editMealItem(MealItem base) {
        int count = mealItemMapper.repeatRecords(base.getMealId(), base.getGoodsId(), base.getSpecificationIds(),
                base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        mealItemMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public MealInfo getMealById(String markId) {
        return mealInfoMapper.selectByPrimaryKey(markId);
    }

    @Override
    public MealItem getMealItemById(String markId) {
        return mealItemMapper.selectByPrimaryKey(markId);
    }

    @Override
    public List<Combobox> getMealList() {
        return mealInfoMapper.selectComboboxList();
    }

    @Override
    public PageGrid<MealInfo> getPageByColumn(PageParam<MealInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("m." + base.getSidx() + " " + base.getSort());
        List<MealInfo> list = mealInfoMapper.selectByExampleSelectiveNotColumn(base.getData());
        PageInfo<MealInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public PageGrid<MealInfo> getPageByLabel(PageParam<MealInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("m." + base.getSidx() + " " + base.getSort());
        List<MealInfo> list = mealInfoMapper.selectByExampleSelectiveNotLabel(base.getData());
        PageInfo<MealInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public PageGrid<MealInfo> getPageBySpecial(PageParam<MealInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("m." + base.getSidx() + " " + base.getSort());
        List<MealInfo> list = mealInfoMapper.selectByExampleSelectiveNotSpecial(base.getData());
        PageInfo<MealInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public PageGrid<MealInfo> getPageByIcon(PageParam<MealInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("m." + base.getSidx() + " " + base.getSort());
        List<MealInfo> list = mealInfoMapper.selectByExampleSelectiveNotIcon(base.getData());
        PageInfo<MealInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public GoodsDetail getMealDetail(String mealId, String userId) {
        GoodsDetail goods;
        try {
            Object goodsObj = redis.get("goods:meal:detail:" + mealId);
            if (ObjectUtil.isNotNull(goodsObj)) {
                goods = JSON.parseObject(JSON.toJSONString(goodsObj), GoodsDetail.class);
                return goods;
            }
            // 防止数据库因请求量过大雪崩而做的拦截
            boolean lock = lockIdMap.put(mealId, true) == null;
            ShowAssert.checkTrue(!lock, StatusCode._5010);
            goodsObj = redis.get("goods:meal:detail:" + mealId);
            if (ObjectUtil.isNotNull(goodsObj)) {
                goods = JSON.parseObject(JSON.toJSONString(goodsObj), GoodsDetail.class);
                return goods;
            }
            // meal info
            GoodsDetail mealInfo = mealInfoMapper.selectById(mealId);
            // meal images
            List<String> mealImgs = mealImageMapper.selectBigByMealId(mealId);
            mealInfo.setImagePaths(mealImgs);
            // meal judge
            List<JudgeBase> judges = mealJudgeMapper.selectJudge(userId, mealId, 3);
            mealInfo.setJudges(judges);
            redis.set("goods:meal:detail:" + mealId, mealInfo);
            return mealInfo;
        } finally {
            lockIdMap.remove(mealId);
        }
    }

    @Override
    public List<String> getServerListInMeal(String mealId) {
        return mealServerMapper.selectServerListByMeal(mealId);
    }

    @Override
    public void addBatchMealServe(BatchVo base) {
        List<MealServer> list = new LinkedList<>();
        MealServer mealServer;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (String mealId : base.getIds()) {
            mealServer = MealServer.builder().markId(snowflake.nextIdStr()).mealId(mealId).serverId(base.getCommonId()).build();
            list.add(mealServer);
        }
        if (!list.isEmpty()) { mealServerMapper.insertBatch(list); }
    }

    @Override
    public void deleteBatchMealServe(BatchVo base) {
        mealServerMapper.deletetBatch(base);
    }
}
