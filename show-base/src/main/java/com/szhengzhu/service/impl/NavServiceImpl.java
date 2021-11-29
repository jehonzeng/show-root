package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.base.NavInfo;
import com.szhengzhu.bean.base.NavItem;
import com.szhengzhu.bean.wechat.vo.NavBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.NavInfoMapper;
import com.szhengzhu.mapper.NavItemMapper;
import com.szhengzhu.service.NavService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("navService")
public class NavServiceImpl implements NavService {

    @Resource
    private NavInfoMapper navInfoMapper;

    @Resource
    private NavItemMapper navItemMapper;

    @Override
    public NavInfo save(NavInfo base) {
        int count = navInfoMapper.repeatRecords(base.getNavCode(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        base.setMarkId(snowflake.nextIdStr());
        base.setAddTime(DateUtil.date());
        navInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    public NavInfo update(NavInfo base) {
        String navCode = StrUtil.isEmpty(base.getNavCode()) ? "" : base.getNavCode();
        int count = navInfoMapper.repeatRecords(navCode, base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        navInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<NavInfo> page(PageParam<NavInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<NavInfo> page = new PageInfo<>(
                navInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public NavItem saveItem(NavItem base) {
        int count = navItemMapper.repeatRecords(base.getTheme(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        navItemMapper.insertSelective(base);
        return base;
    }

    @Override
    public PageGrid<NavItem> getItemPage(PageParam<NavItem> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<NavItem> page = new PageInfo<>(
                navItemMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public NavItem modifyItem(NavItem base) {
        int count = navItemMapper.repeatRecords(base.getTheme(), base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        navItemMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public NavItem getItemById(String markId) {
        return navItemMapper.selectByPrimaryKey(markId);
    }

    @Override
    public List<NavBase> listNavAndItem() {
        return navInfoMapper.selectForeNav();
    }

    @Override
    public void deleteItem(String markId) {
        navItemMapper.deleteByPrimaryKey(markId);
    }
}
