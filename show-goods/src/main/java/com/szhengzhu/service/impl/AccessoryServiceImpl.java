package com.szhengzhu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.AccessoryInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.AccessoryInfoMapper;
import com.szhengzhu.service.AccessoryService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("accessoryService")
public class AccessoryServiceImpl implements AccessoryService {

    @Autowired
    private AccessoryInfoMapper accessoryInfoMapper;

    @Override
    public Result<?> addAccessory(AccessoryInfo base) {
        if (base == null || StringUtils.isEmpty(base.getTheme()))
            return new Result<>(StatusCode._4004);

        int count = accessoryInfoMapper.repeatRecords(base.getTheme(), "");
        if (count > 0)
            return new Result<>(StatusCode._4007);
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        accessoryInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<AccessoryInfo>> getAccessoryPage(PageParam<AccessoryInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        List<AccessoryInfo> list = accessoryInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<AccessoryInfo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> editAccessory(AccessoryInfo base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4004);
        int count = accessoryInfoMapper.repeatRecords(base.getTheme(), base.getMarkId());
        if (count > 0)
            return new Result<>(StatusCode._4007);
        accessoryInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> selectAccessoryById(String markId) {
        AccessoryInfo data = accessoryInfoMapper.selectByPrimaryKey(markId);
        return new Result<>(data);
    }

}
