package com.szhengzhu.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.goods.ColumnInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.ColumnGoodsVo;
import com.szhengzhu.bean.vo.ColumnMealVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.ColumnGoodsMapper;
import com.szhengzhu.mapper.ColumnInfoMapper;
import com.szhengzhu.service.ColumnService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("columnService")
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    private ColumnInfoMapper columnInfoMapper;

    @Autowired
    private ColumnGoodsMapper columnGoodsMapper;

    @Override
    public Result<?> addColumn(ColumnInfo base) {
        if (base == null ||StringUtils.isEmpty(base.getTheme())) 
            return new Result<>(StatusCode._4004);
        int count = columnInfoMapper.repeatRecords(base.getTheme(), "");
        if (count > 0) 
            return new Result<>(StatusCode._4007);
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        columnInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> modifyColumn(ColumnInfo base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4004);
        int count = columnInfoMapper.repeatRecords(base.getTheme(), base.getMarkId());
        if (count > 0) 
            return new Result<>(StatusCode._4007);
        columnInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<ColumnInfo>> getPage(PageParam<ColumnInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<ColumnInfo> page = new PageInfo<>(
                columnInfoMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> modifyColumnGoods(ColumnGoods base) {
        if (base == null || StringUtils.isEmpty(base.getMarkId())) 
            return new Result<>(StatusCode._4004);
        columnGoodsMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<ColumnGoodsVo>> getColumnGoodsPage(PageParam<ColumnGoods> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("c."+base.getSidx() + " " + base.getSort());
        PageInfo<ColumnGoodsVo> page = new PageInfo<>(
                columnGoodsMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> addBatchColumnGoods(BatchVo base) {
        List<ColumnGoods> list = new LinkedList<>();
        ColumnGoods item;
        IdGenerator generator = IdGenerator.getInstance();
        for (String goodsId : base.getIds()) {
            item = new ColumnGoods();
            item.setMarkId(generator.nexId());
            item.setColumnId(base.getCommonId());
            item.setGoodsId(goodsId);
            item.setServerStatus(false);
            item.setGoodsType(base.getType());
            list.add(item);
        }
        if (list.size() > 0)
            columnGoodsMapper.insertBatch(list);
        return new Result<>();
    }

    @Override
    public Result<?> deleteColumnGoods(ColumnGoods base) {
        if(base == null)
            return new Result<>(StatusCode._4008);
        columnGoodsMapper.deleteItem(base.getColumnId(),base.getGoodsId());
        return new Result<>();
    }

    @Override
    public Result<ColumnInfo> getColumnInfo(String markId) {
        return new Result<>(columnInfoMapper.selectByPrimaryKey(markId));
    }

    @Override
    public Result<PageGrid<ColumnMealVo>> getColumnMealPage(PageParam<ColumnGoods> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("c."+base.getSidx() + " " + base.getSort());
        PageInfo<ColumnMealVo> page = new PageInfo<>(
                columnGoodsMapper.selectMealByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

}
