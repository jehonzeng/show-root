package com.szhengzhu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.StoreHouseInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.StoreHouseInfoMapper;
import com.szhengzhu.service.StoreHouseService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("storeHouseService")
public class StoreHouseServiceImpl implements StoreHouseService {

    @Autowired
    private StoreHouseInfoMapper storeHouseInfoMapper;

    @Override
    public Result<?> addStoreHouse(StoreHouseInfo info) {
        if (info == null || StringUtils.isEmpty(info.getName())) {
            return new Result<>(StatusCode._4004);
        }
        int count = storeHouseInfoMapper.repeatRecords(info.getName(), "");
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        info.setMarkId(IdGenerator.getInstance().nexId());
        info.setServerStatus(false);
        storeHouseInfoMapper.insertSelective(info);
        return new Result<>(info);
    }

    @Override
    public Result<?> modifyStoreHouse(StoreHouseInfo info) {
        if (info == null || info.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        String name = info.getName() == null ? "" : info.getName();
        int count = storeHouseInfoMapper.repeatRecords(name, info.getMarkId());
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        storeHouseInfoMapper.updateByPrimaryKeySelective(info);
        return new Result<>(info);
    }

    @Override
    public Result<PageGrid<StoreHouseInfo>> getPage(PageParam<StoreHouseInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<StoreHouseInfo> page = new PageInfo<>(
                storeHouseInfoMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<List<Combobox>> listCombobox() {
        List<Combobox> comboboxs = storeHouseInfoMapper.selectCombobox();
        return new Result<>(comboboxs);
    }

    @Override
    public Result<?> getHouseInfo(String markId) {
        return new Result<>(storeHouseInfoMapper.selectByPrimaryKey(markId));
    }

}
