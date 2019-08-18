package com.szhengzhu.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.goods.LabelInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.LabelGoodsVo;
import com.szhengzhu.bean.vo.LabelMealVo;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.Label;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.GoodsInfoMapper;
import com.szhengzhu.mapper.LabelGoodsMapper;
import com.szhengzhu.mapper.LabelInfoMapper;
import com.szhengzhu.mapper.MealInfoMapper;
import com.szhengzhu.service.LabelService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("labelService")
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelInfoMapper labelInfoMapper;
    
    @Autowired
    private LabelGoodsMapper labelGoodsMapper;
    
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    
    @Autowired
    private MealInfoMapper mealInfoMapper;

    @Override
    public Result<?> addLabel(LabelInfo base) {
        if (base == null || StringUtils.isEmpty(base.getTheme())) {
            return new Result<>(StatusCode._4004);
        }
        int count = labelInfoMapper.repeatRecords(base.getTheme(), "");
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        IdGenerator generator = IdGenerator.getInstance();
        base.setMarkId(generator.nexId());
        base.setServerStatus(false);
        labelInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> modifyLabel(LabelInfo base) {
        if (base == null || base.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        int count = labelInfoMapper.repeatRecords(base.getTheme(), base.getMarkId());
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        labelInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<LabelInfo>> getPage(PageParam<LabelInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<LabelInfo> page = new PageInfo<>(
                labelInfoMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }
    

    @Override
    public Result<?> addBatchLabelGoods(BatchVo base) {
        List<LabelGoods> list = new LinkedList<>();
        LabelGoods labelGoods;
        IdGenerator generator = IdGenerator.getInstance();
        for(String goodsId:base.getIds()) {
             labelGoods = new LabelGoods();
             labelGoods.setMarkId(generator.nexId());
             labelGoods.setLabelId(base.getCommonId());
             labelGoods.setGoodsId(goodsId);
             labelGoods.setServerStatus(false);
             labelGoods.setGoodsType(base.getType());
             list.add(labelGoods);
        }
        if(list.size() > 0) {
            labelGoodsMapper.insertBatch(list);
        }
        return new Result<>();
    }

    @Override
    public Result<PageGrid<LabelGoodsVo>> getLabelGoodsPage(PageParam<LabelGoods> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("c."+base.getSidx() + " " + base.getSort());
        PageInfo<LabelGoodsVo> page = new PageInfo<>(
                labelGoodsMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> deleteLabelGoods(LabelGoods base) {
        if(base == null)
            return new Result<>(StatusCode._4008);
        labelGoodsMapper.deleteItem(base.getLabelId(),base.getGoodsId());
        return new Result<>();
    }

    @Override
    public Result<?> modifyLabelGoods(LabelGoods base) {
        if(base == null || base.getMarkId() == null) {
            return new Result<>(StatusCode._4008);
        }
        labelGoodsMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<LabelInfo> getLabelInfo(String markId) {
        return new Result<>(labelInfoMapper.selectByPrimaryKey(markId));
    }

    @Override
    public Result<PageGrid<LabelMealVo>> getLabelMealPage(PageParam<LabelGoods> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("c."+base.getSidx() + " " + base.getSort());
        List<LabelMealVo> list = labelGoodsMapper.selectMealByExampleSelective(base.getData());
        PageInfo<LabelMealVo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<List<Label>> listLabelGoods() {
        List<Label> labels = labelInfoMapper.selectLabel();
        for (int index = 0, size = labels.size(); index < size; index++) {
            String labelId = labels.get(index).getLabelId();
            int type = labels.get(index).getType();
            List<GoodsBase> goodsList = new ArrayList<>();
            if (type == 0) 
                goodsList = goodsInfoMapper.selectLabelGoods(labelId);
             else if (type == 2) 
                goodsList = mealInfoMapper.selectLabelMeal(labelId);
            labels.get(index).setGoodsList(goodsList);
        }
        return new Result<>(labels);
    }

}
