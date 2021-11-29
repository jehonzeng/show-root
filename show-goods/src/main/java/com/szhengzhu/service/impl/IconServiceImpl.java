package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.IconInfo;
import com.szhengzhu.bean.goods.IconItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.IconGoodsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.IconInfoMapper;
import com.szhengzhu.mapper.IconItemMapper;
import com.szhengzhu.service.IconService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
@Service("iconService")
public class IconServiceImpl implements IconService {

    @Resource
    private IconInfoMapper iconInfoMapper;

    @Resource
    private IconItemMapper iconItemMapper;

    @Override
    public IconInfo addIcon(IconInfo base) {
        int count = iconInfoMapper.repeatRecoreds(base.getTheme(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        iconInfoMapper.insertSelective(base);
        return base;
    }

    @CheckGoodsChange
    @Override
    public IconInfo modifyIcon(IconInfo base) {
        String theme = StrUtil.isEmpty(base.getTheme()) ? "" : base.getTheme();
        int count = iconInfoMapper.repeatRecoreds(theme, base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        iconInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<IconInfo> getPage(PageParam<IconInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<IconInfo> page = new PageInfo<>(
                iconInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public List<Combobox> getIconByGoods(String goodsId) {
        return iconInfoMapper.selectComboboxByGoods(goodsId);
    }

    @Override
    public IconInfo getIconById(String markId) {
        return iconInfoMapper.selectByPrimaryKey(markId);
    }

    @Override
    public PageGrid<IconGoodsVo> getItemPage(PageParam<IconItem> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<IconGoodsVo> list = iconItemMapper.selectByExampleSelective(base.getData());
        PageInfo<IconGoodsVo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @CheckGoodsChange
    @Override
    public void deleteItem(IconItem base) {
        iconItemMapper.deleteItem(base.getIconId(), base.getGoodsId());
    }

    @CheckGoodsChange
    @Override
    public void addItem(IconItem base) {
        if (iconItemMapper.hasItem(base.getGoodsId()) > 0) {
            iconItemMapper.updateByGoods(base.getGoodsId(), base.getIconId());
        } else {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            base.setMarkId(snowflake.nextIdStr());
            iconItemMapper.insertSelective(base);
        }
    }

    @CheckGoodsChange
    @Override
    public void addBatchGoods(BatchVo base) {
        List<IconItem> list = new LinkedList<>();
        IconItem item;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (String goodsId : base.getIds()) {
            item = IconItem.builder().markId(snowflake.nextIdStr()).iconId(base.getCommonId()).goodsId(goodsId).build();
            list.add(item);
        }
        if (!list.isEmpty()) { iconItemMapper.insertBatch(list); }
    }
}
