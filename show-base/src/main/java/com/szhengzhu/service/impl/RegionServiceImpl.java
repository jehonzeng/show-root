package com.szhengzhu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.base.RegionInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.RegionInfoMapper;
import com.szhengzhu.service.RegionService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("regionService")
public class RegionServiceImpl implements RegionService{
    
    @Autowired
    private RegionInfoMapper regionInfoMapper;

    @Override
    public Result<?> getRegionPage(PageParam<RegionInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx()+" "+base.getSort());
        List<RegionInfo> list = regionInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<RegionInfo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> addRegion(RegionInfo base) {
        if(base ==null || StringUtils.isEmpty(base.getStoreName()))
            return new Result<>(StatusCode._4004);
        int count = regionInfoMapper.repeatRecords(base.getStoreName(),"");
        if(count > 0)
            return new Result<>(StatusCode._4007);
        base.setMarkId(IdGenerator.getInstance().nexId());
        regionInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> editRegion(RegionInfo base) {
        if(base== null || base.getMarkId() == null)
            return new Result<>(StatusCode._4004);
        int count = regionInfoMapper.repeatRecords(base.getStoreName(),base.getMarkId());
        if(count > 0)
            return new Result<>(StatusCode._4007);
        regionInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<List<Combobox>> getComboboxList() {
        List<Combobox> list = regionInfoMapper.selectComboboxList();
        return new Result<>(list);
    }

}
