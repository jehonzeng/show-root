package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.StoreHouseInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.StoreHouseInfoMapper;
import com.szhengzhu.service.StoreHouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("storeHouseService")
public class StoreHouseServiceImpl implements StoreHouseService {

    @Resource
    private StoreHouseInfoMapper storeHouseInfoMapper;

    @Override
    public StoreHouseInfo addStoreHouse(StoreHouseInfo info) {
        int count = storeHouseInfoMapper.repeatRecords(info.getName(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        info.setMarkId(snowflake.nextIdStr());
        info.setServerStatus(false);
        storeHouseInfoMapper.insertSelective(info);
        return info;
    }

    @Override
    public StoreHouseInfo modifyStoreHouse(StoreHouseInfo info) {
        String name = StrUtil.isEmpty(info.getName()) ? "" : info.getName();
        int count = storeHouseInfoMapper.repeatRecords(name, info.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        storeHouseInfoMapper.updateByPrimaryKeySelective(info);
        return info;
    }

    @Override
    public PageGrid<StoreHouseInfo> getPage(PageParam<StoreHouseInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<StoreHouseInfo> page = new PageInfo<>(
                storeHouseInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public List<Combobox> listCombobox() {
        return storeHouseInfoMapper.selectCombobox();
    }

    @Override
    public StoreHouseInfo getHouseInfo(String markId) {
        return storeHouseInfoMapper.selectByPrimaryKey(markId);
    }
}
