package com.szhengzhu.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.ColumnGoodsMapper;
import com.szhengzhu.mapper.LabelGoodsMapper;
import com.szhengzhu.mapper.SpecialInfoMapper;
import com.szhengzhu.mapper.SpecialItemMapper;
import com.szhengzhu.service.SpecialService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("specialService")
public class SpecialServiceImpl implements SpecialService {

    @Autowired
    private SpecialInfoMapper specialInfoMapper;

    @Autowired
    private SpecialItemMapper specialItemMapper;

    @Autowired
    private ColumnGoodsMapper columnGoodsMapper;

    @Autowired
    private LabelGoodsMapper labelGoodsMapper;

    @Override
    public Result<?> addSpecial(SpecialInfo base) {
        if (StringUtils.isEmpty(base.getTheme())) {
            return new Result<>(StatusCode._4004);
        }
        int count = specialInfoMapper.repeatRecords(base.getTheme(), "");
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        specialInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<SpecialInfo>> getSpecialPage(PageParam<SpecialInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        List<SpecialInfo> list = specialInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<SpecialInfo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> editSpecial(SpecialInfo base) {
        if (base == null || base.getMarkId() == null) {
            return new Result<>(StatusCode._4008);
        }
        int count = specialInfoMapper.repeatRecords(base.getTheme(), base.getMarkId());
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        specialInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }
    
    @Override
    public Result<?> sepcialInfoById(String markId) {
        SpecialInfo data = specialInfoMapper.selectByPrimaryKey(markId);
        return new Result<>(data);
    }

    @Override
    public Result<?> deleteItem(SpecialItem base) {
        if(base == null)
            return new Result<>(StatusCode._4008);
        specialItemMapper.deleteItem(base.getSpecialId(),base.getGoodsId());
        return new Result<>();
    }

    @Override
    public Result<?> addItemBatchByColumn(SpecialBatchVo base) {
        List<ColumnGoods> list = columnGoodsMapper.selectByColumn(base.getId());
        SpecialItem item;
        List<SpecialItem> items = new LinkedList<SpecialItem>();
        IdGenerator generator = IdGenerator.getInstance();
        for (int i = 0, length = list.size(); i < length; i++) {
            String goodsId = list.get(i).getGoodsId();
            item = new SpecialItem();
            item.setMarkId(generator.nexId());
            item.setSpecialId(base.getPromotionId());
            item.setGoodsId(goodsId);
            items.add(item);
        }
        if (items.size() > 0) {
            specialItemMapper.insertBatch(items);
        }
        return new Result<Object>(items);
    }

    @Override
    public Result<?> addItemBatchByLabel(SpecialBatchVo base) {
        List<LabelGoods> list = labelGoodsMapper.selectByLabel(base.getId());
        SpecialItem item;
        List<SpecialItem> items = new LinkedList<SpecialItem>();
        IdGenerator generator = IdGenerator.getInstance();
        for (int i = 0, length = list.size(); i < length; i++) {
            String goodsId = list.get(i).getGoodsId();
            item = new SpecialItem();
            item.setMarkId(generator.nexId());
            item.setSpecialId(base.getPromotionId());
            item.setGoodsId(goodsId);
            items.add(item);
        }
        if (items.size() > 0) {
            specialItemMapper.insertBatch(items);
        }
        return new Result<Object>(items);
    }

    @Override
    public Result<PageGrid<SpecialGoodsVo>> getItemPage(PageParam<SpecialItem> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("s." + base.getSidx() + " " + base.getSort());
        List<SpecialGoodsVo> list = specialItemMapper.selectByExampleSelective(base.getData());
        PageInfo<SpecialGoodsVo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> listSpecialByGoods(String goodsId) {
        List<Combobox> list = specialInfoMapper.selectSpecialListByGoods(goodsId);
        return new Result<>(list);
    }

    @Override
    public Result<?> addSpecialItem(SpecialItem base) {
        if (specialItemMapper.hasItem(base.getGoodsId()) > 0) {
            specialItemMapper.updateByGoods(base.getGoodsId(),
                    base.getSpecialId());
        } else {
            base.setMarkId(IdGenerator.getInstance().nexId());
            specialItemMapper.insertSelective(base);
        }
        return new Result<>();
    }

    @Override
    public Result<?> addBatchGoods(BatchVo base) {
        List<SpecialItem> list = new LinkedList<>();
        SpecialItem item;
        IdGenerator generator = IdGenerator.getInstance();
        for (String goodsId : base.getIds()) {
            item = new SpecialItem();
            item.setMarkId(generator.nexId());
            item.setSpecialId(base.getCommonId());
            item.setGoodsId(goodsId);
            list.add(item);
        }
        if (list.size() > 0)
            specialItemMapper.insertBatch(list);
        return new Result<>();
    }
}
