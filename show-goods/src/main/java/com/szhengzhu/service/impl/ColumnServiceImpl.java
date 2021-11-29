package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.goods.ColumnInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.ColumnGoodsVo;
import com.szhengzhu.bean.vo.ColumnMealVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.ColumnGoodsMapper;
import com.szhengzhu.mapper.ColumnInfoMapper;
import com.szhengzhu.service.ColumnService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("columnService")
public class ColumnServiceImpl implements ColumnService {

    @Resource
    private ColumnInfoMapper columnInfoMapper;

    @Resource
    private ColumnGoodsMapper columnGoodsMapper;

    @Override
    public ColumnInfo addColumn(ColumnInfo base) {
        int count = columnInfoMapper.repeatRecords(base.getTheme(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        columnInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    public ColumnInfo modifyColumn(ColumnInfo base) {
        int count = columnInfoMapper.repeatRecords(base.getTheme(), base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        columnInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<ColumnInfo> getPage(PageParam<ColumnInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<ColumnInfo> page = new PageInfo<>(
                columnInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @CheckGoodsChange
    @Override
    public ColumnGoods modifyColumnGoods(ColumnGoods base) {
        columnGoodsMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<ColumnGoodsVo> getColumnGoodsPage(PageParam<ColumnGoods> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("c." + base.getSidx() + " " + base.getSort());
        PageInfo<ColumnGoodsVo> page = new PageInfo<>(
                columnGoodsMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @CheckGoodsChange
    @Override
    public void addBatchColumnGoods(BatchVo base) {
        List<ColumnGoods> list = new LinkedList<>();
        ColumnGoods item;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (String goodsId : base.getIds()) {
            item = ColumnGoods.builder().markId(snowflake.nextIdStr()).columnId(base.getCommonId())
                    .goodsId(goodsId).serverStatus(false).goodsType(base.getType()).build();
            list.add(item);
        }
        if (!list.isEmpty()) {
            columnGoodsMapper.insertBatch(list);
        }
    }

    @CheckGoodsChange
    @Override
    public void deleteColumnGoods(ColumnGoods base) {
        columnGoodsMapper.deleteItem(base.getColumnId(), base.getGoodsId());
    }

    @Override
    public ColumnInfo getColumnInfo(String markId) {
        return columnInfoMapper.selectByPrimaryKey(markId);
    }

    @Override
    public PageGrid<ColumnMealVo> getColumnMealPage(PageParam<ColumnGoods> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("c." + base.getSidx() + " " + base.getSort());
        PageInfo<ColumnMealVo> page = new PageInfo<>(
                columnGoodsMapper.selectMealByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }
}
