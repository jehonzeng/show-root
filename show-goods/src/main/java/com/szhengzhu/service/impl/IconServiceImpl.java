package com.szhengzhu.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.IconInfo;
import com.szhengzhu.bean.goods.IconItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.IconGoodsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.IconInfoMapper;
import com.szhengzhu.mapper.IconItemMapper;
import com.szhengzhu.service.IconService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("iconService")
public class IconServiceImpl implements IconService {

    @Autowired
    private IconInfoMapper iconInfoMapper;
    
    @Autowired
    private IconItemMapper iconItemMapper;
    
    @Override
    public Result<?> addIcon(IconInfo base) {
        if (base == null || StringUtils.isEmpty(base.getTheme()))
            return new Result<>(StatusCode._4004);
        int count = iconInfoMapper.repeatRecoreds(base.getTheme(), "");
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        iconInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> modifyIcon(IconInfo base) {
        if (base == null || base.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        String theme = base.getTheme() == null ? "" : base.getTheme();
        int count = iconInfoMapper.repeatRecoreds(theme, base.getMarkId());
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        iconInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<IconInfo>> getPage(PageParam<IconInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<IconInfo> page = new PageInfo<>(
                iconInfoMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> getIconByGoods(String goodsId) {
        List<Combobox> list= iconInfoMapper.selectComboboxByGoods(goodsId);
        return new Result<>(list);
    }

    @Override
    public Result<?> getInconById(String markId) {
        IconInfo icon = iconInfoMapper.selectByPrimaryKey(markId);
        return new Result<>(icon);
    }

    @Override
    public Result<PageGrid<IconGoodsVo>> getItemPage(PageParam<IconItem> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        List<IconGoodsVo> list= iconItemMapper.selectByExampleSelective(base.getData());
        PageInfo<IconGoodsVo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> deleteItem(IconItem base) {
        if(base == null)
            return new Result<>(StatusCode._4008);
        iconItemMapper.deleteItem(base.getIconId(),base.getGoodsId());
        return new Result<>();
    }

    @Override
    public Result<?> addItem(IconItem base) {
        if(iconItemMapper.hasItem(base.getGoodsId())>0) {
            iconItemMapper.updateByGoods(base.getGoodsId(),base.getIconId());
        }else {
            base.setMarkId(IdGenerator.getInstance().nexId());
            iconItemMapper.insertSelective(base);
        }
        return new Result<>();
    }

    @Override
    public Result<?> addBatchGoods(BatchVo base) {
        List<IconItem> list = new LinkedList<>();
        IconItem item;
        IdGenerator generator = IdGenerator.getInstance();
        for (String goodsId : base.getIds()) {
            item = new IconItem();
            item.setMarkId(generator.nexId());
            item.setIconId(base.getCommonId());
            item.setGoodsId(goodsId);
            list.add(item);
        }
        if (list.size() > 0)
            iconItemMapper.insertBatch(list);
        return new Result<>();
    }
}
