package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckStatus;
import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.goods.LabelInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.LabelGoodsVo;
import com.szhengzhu.bean.vo.LabelMealVo;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.Label;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.LabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service("labelService")
public class LabelServiceImpl implements LabelService {

    @Resource
    private LabelInfoMapper labelInfoMapper;

    @Resource
    private LabelGoodsMapper labelGoodsMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private MealInfoMapper mealInfoMapper;

    @Resource
    private Redis redis;

    private final ConcurrentHashMap<String, Boolean> lockIdMap = new ConcurrentHashMap<>();

    @Override
    public LabelInfo addLabel(LabelInfo base) {
        int count = labelInfoMapper.repeatRecords(base.getTheme(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        labelInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    public LabelInfo modifyLabel(LabelInfo base) {
        int count = labelInfoMapper.repeatRecords(base.getTheme(), base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        labelInfoMapper.updateByPrimaryKeySelective(base);
        labelChange(base.getMarkId());
        return base;
    }

    private void labelChange(String labelId) {
        redis.del("goods:label:product");
        redis.del("goods:label:product:" + labelId);
    }

    @Override
    public PageGrid<LabelInfo> getPage(PageParam<LabelInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<LabelInfo> page = new PageInfo<>(labelInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public void addBatchLabelGoods(BatchVo base) {
        List<LabelGoods> list = new LinkedList<>();
        LabelGoods labelGoods;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (String goodsId : base.getIds()) {
            labelGoods = LabelGoods.builder().markId(snowflake.nextIdStr()).labelId(base.getCommonId())
                    .goodsId(goodsId).serverStatus(false).goodsType(base.getType()).build();
            list.add(labelGoods);
            labelChange(base.getCommonId());
        }
        if (!list.isEmpty()) {
            labelGoodsMapper.insertBatch(list);
        }
    }

    @Override
    public PageGrid<LabelGoodsVo> getLabelGoodsPage(PageParam<LabelGoods> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("c." + base.getSidx() + " " + base.getSort());
        PageInfo<LabelGoodsVo> page = new PageInfo<>(labelGoodsMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public void deleteLabelGoods(LabelGoods base) {
        labelGoodsMapper.deleteItem(base.getLabelId(), base.getGoodsId());
        labelChange(base.getLabelId());
    }

    @Override
    public LabelGoods modifyLabelGoods(LabelGoods base) {
        labelGoodsMapper.updateByPrimaryKeySelective(base);
        labelChange(base.getLabelId());
        return base;
    }

    @Override
    public LabelInfo getLabelInfo(String markId) {
        return labelInfoMapper.selectByPrimaryKey(markId);
    }

    @Override
    public PageGrid<LabelMealVo> getLabelMealPage(PageParam<LabelGoods> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("c." + base.getSidx() + " " + base.getSort());
        List<LabelMealVo> list = labelGoodsMapper.selectMealByExampleSelective(base.getData());
        PageInfo<LabelMealVo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @CheckStatus
    @SuppressWarnings("unchecked")
    @Override
    public List<Label> listLabelGoods() {
        List<Label> labels = (List<Label>) redis.get("goods:label:product");
        if (labels != null) {
            return labels;
        }
        //非公平锁
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            labels = (List<Label>) redis.get("goods:label:product");
            if (labels != null) {
                return labels;
            }
            labels = labelInfoMapper.selectLabel();
            for (Label label : labels) {
                String labelId = label.getLabelId();
                String type = label.getType().toString();
                List<GoodsBase> goodsList = new ArrayList<>();
                if (Contacts.TYPE_OF_GOODS.equals(type)) {
                    goodsList = goodsInfoMapper.selectLabelGoods(labelId);
                } else if (Contacts.TYPE_OF_MEAL.equals(type)) {
                    goodsList = mealInfoMapper.selectLabelMeal(labelId);
                }
                label.setGoodsList(goodsList);
            }
            labels = labels.stream().filter(label -> !label.getGoodsList().isEmpty()).collect(Collectors.toList());
            redis.set("goods:label:product", labels, 4L * 60 * 60);
        } finally {
            lock.unlock();
        }
        return labels;
    }

    @CheckStatus
    @Override
    public List<GoodsBase> listLabelGoods(String labelId) {
        List<GoodsBase> goodsList;
        try {
            goodsList = (List<GoodsBase>) redis.get("goods:label:product:" + labelId);
            if (goodsList != null) {
                return goodsList;
            }
            boolean lock = lockIdMap.put(labelId, true) == null;
            ShowAssert.checkTrue(!lock, StatusCode._5010);
            goodsList = (List<GoodsBase>) redis.get("goods:label:product:" + labelId);
            if (goodsList != null) {
                return goodsList;
            }
            LabelInfo label = labelInfoMapper.selectByPrimaryKey(labelId);
            String type = label.getServerType().toString();
            if (Contacts.TYPE_OF_GOODS.equals(type)) {
                goodsList = goodsInfoMapper.selectLabelGoods(labelId);
            } else if (Contacts.TYPE_OF_MEAL.equals(type)) {
                goodsList = mealInfoMapper.selectLabelMeal(labelId);
            }
            redis.set("goods:label:product:" + labelId, goodsList, 8L * 60 * 60);
        } finally {
            lockIdMap.remove(labelId);
        }
        return goodsList;
    }

}
