package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.AccessoryInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.AccessoryInfoMapper;
import com.szhengzhu.service.AccessoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("accessoryService")
public class AccessoryServiceImpl implements AccessoryService {

    @Resource
    private AccessoryInfoMapper accessoryInfoMapper;

    @Override
    public AccessoryInfo addAccessory(AccessoryInfo base) {
        int count = accessoryInfoMapper.repeatRecords(base.getTheme(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        base.setCreateTime(DateUtil.date());
        accessoryInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    public PageGrid<AccessoryInfo> getAccessoryPage(PageParam<AccessoryInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("create_time desc,"+base.getSidx() + " " + base.getSort());
        List<AccessoryInfo> list = accessoryInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<AccessoryInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public AccessoryInfo editAccessory(AccessoryInfo base) {
        int count = accessoryInfoMapper.repeatRecords(base.getTheme(), base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        accessoryInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public AccessoryInfo selectAccessoryById(String markId) {
        return accessoryInfoMapper.selectByPrimaryKey(markId);
    }
}
