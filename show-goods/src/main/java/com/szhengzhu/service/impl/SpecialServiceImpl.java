package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.goods.SpecialInfo;
import com.szhengzhu.bean.goods.SpecialItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.SpecialBatchVo;
import com.szhengzhu.bean.vo.SpecialGoodsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.ColumnGoodsMapper;
import com.szhengzhu.mapper.LabelGoodsMapper;
import com.szhengzhu.mapper.SpecialInfoMapper;
import com.szhengzhu.mapper.SpecialItemMapper;
import com.szhengzhu.service.SpecialService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("specialService")
public class SpecialServiceImpl implements SpecialService {

    @Resource
    private SpecialInfoMapper specialInfoMapper;

    @Resource
    private SpecialItemMapper specialItemMapper;

    @Resource
    private ColumnGoodsMapper columnGoodsMapper;

    @Resource
    private LabelGoodsMapper labelGoodsMapper;

    @Override
    public SpecialInfo addSpecial(SpecialInfo base) {
        int count = specialInfoMapper.repeatRecords(base.getTheme(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        specialInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    public PageGrid<SpecialInfo> getSpecialPage(PageParam<SpecialInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<SpecialInfo> list = specialInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<SpecialInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @CheckGoodsChange
    @Override
    public SpecialInfo editSpecial(SpecialInfo base) {
        int count = specialInfoMapper.repeatRecords(base.getTheme(), base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        specialInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public SpecialInfo specialInfoById(String markId) {
        return specialInfoMapper.selectByPrimaryKey(markId);
    }

    @CheckGoodsChange
    @Override
    public void deleteItem(SpecialItem base) {
        specialItemMapper.deleteItem(base.getSpecialId(), base.getGoodsId());
    }

    @CheckGoodsChange
    @Override
    public List<SpecialItem> addItemBatchByColumn(SpecialBatchVo base) {
        List<ColumnGoods> list = columnGoodsMapper.selectByColumn(base.getId());
        SpecialItem item;
        List<SpecialItem> items = new LinkedList<>();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (ColumnGoods columnGoods : list) {
            String goodsId = columnGoods.getGoodsId();
            item = SpecialItem.builder().markId(snowflake.nextIdStr()).specialId(base.getPromotionId()).goodsId(goodsId).build();
            items.add(item);
        }
        if (!items.isEmpty()) {
            specialItemMapper.insertBatch(items);
        }
        return items;
    }

    @CheckGoodsChange
    @Override
    public List<SpecialItem> addItemBatchByLabel(SpecialBatchVo base) {
        List<LabelGoods> list = labelGoodsMapper.selectByLabel(base.getId());
        SpecialItem item;
        List<SpecialItem> items = new LinkedList<>();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (LabelGoods labelGoods : list) {
            String goodsId = labelGoods.getGoodsId();
            item = SpecialItem.builder().markId(snowflake.nextIdStr()).specialId(base.getPromotionId()).goodsId(goodsId).build();
            items.add(item);
        }
        if (!items.isEmpty()) { specialItemMapper.insertBatch(items); }
        return items;
    }

    @Override
    public PageGrid<SpecialGoodsVo> getItemPage(PageParam<SpecialItem> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("s." + base.getSidx() + " " + base.getSort());
        List<SpecialGoodsVo> list = specialItemMapper.selectByExampleSelective(base.getData());
        PageInfo<SpecialGoodsVo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public List<Combobox> listSpecialByGoods(String goodsId) {
        return specialInfoMapper.selectSpecialListByGoods(goodsId);
    }

    @CheckGoodsChange
    @Override
    public void addSpecialItem(SpecialItem base) {
        if (specialItemMapper.hasItem(base.getGoodsId()) > 0) {
            specialItemMapper.updateByGoods(base.getGoodsId(),
                    base.getSpecialId());
        } else {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            base.setMarkId(snowflake.nextIdStr());
            specialItemMapper.insertSelective(base);
        }
    }

    @CheckGoodsChange
    @Override
    public void addBatchGoods(BatchVo base) {
        List<SpecialItem> list = new LinkedList<>();
        SpecialItem item;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (String goodsId : base.getIds()) {
            item = SpecialItem.builder().markId(snowflake.nextIdStr()).specialId(base.getCommonId()).goodsId(goodsId).build();
            list.add(item);
        }
        if (!list.isEmpty()) { specialItemMapper.insertBatch(list); }
    }
}
