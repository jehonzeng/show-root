package com.szhengzhu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.base.NavInfo;
import com.szhengzhu.bean.base.NavItem;
import com.szhengzhu.bean.wechat.vo.NavVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.NavInfoMapper;
import com.szhengzhu.mapper.NavItemMapper;
import com.szhengzhu.service.NavService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("navService")
public class NavServiceImpl implements NavService {

    @Autowired
    private NavInfoMapper navInfoMapper;

    @Autowired
    private NavItemMapper navItemMapper;

    @Override
    public Result<NavInfo> save(NavInfo base) {
        if (StringUtils.isEmpty(base.getNavCode()))
            return new Result<>(StatusCode._4004);
        int count = navInfoMapper.repeatRecords(base.getNavCode(), "");
        if (count > 0)
            return new Result<>(StatusCode._4007);
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setAddTime(TimeUtils.today());
        navInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<NavInfo> update(NavInfo base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4008);
        String navCode = base.getNavCode() == null ? "" : base.getNavCode();
        int count = navInfoMapper.repeatRecords(navCode, base.getMarkId());
        if (count > 0)
            return new Result<>(StatusCode._4007);
        navInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<NavInfo>> page(PageParam<NavInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<NavInfo> page = new PageInfo<>(
                navInfoMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<NavItem> saveItem(NavItem base) {
        if(base == null || base.getNavId()== null ||
                 StringUtils.isEmpty(base.getTheme()))
            return new Result<>(StatusCode._4004);
        int count = navItemMapper.repeatRecords(base.getTheme(), "");
        if (count > 0)
            return new Result<>(StatusCode._4007);
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        navItemMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<NavItem>> getItemPage(PageParam<NavItem> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<NavItem> page = new PageInfo<>(
                navItemMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<NavItem> modifyItem(NavItem base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4008);
        int count = navItemMapper.repeatRecords(base.getTheme(), base.getMarkId());
        if (count > 0)
            return new Result<>(StatusCode._4007);
        navItemMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<NavItem> getItemById(String markId) {
        NavItem item = navItemMapper.selectByPrimaryKey(markId);
        return new Result<>(item);
    }

    @Override
    public Result<List<NavVo>> listNavAndItem() {
        List<NavVo> navs = navInfoMapper.selectForeNav();
        return new Result<>(navs);
    }
    
    @Override
    public Result<?> deleteItem(String markId) {
        if(markId == null)
            return new Result<>(StatusCode._4008);
        navItemMapper.deleteByPrimaryKey(markId);
        return new Result<>();
    }
}
