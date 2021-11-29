package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.base.StoreInfo;
import com.szhengzhu.bean.base.StoreItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.StoreInfoMapper;
import com.szhengzhu.mapper.StoreItemMapper;
import com.szhengzhu.service.StoreService;
import com.szhengzhu.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
@Service("storeService")
public class StoreServiceImpl implements StoreService {

    @Resource
    private StoreInfoMapper storeInfoMapper;

    @Resource
    private StoreItemMapper storeItemMapper;

    @Override
    public PageGrid<StoreInfo> getStorePage(PageParam<StoreInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<StoreInfo> list = storeInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<StoreInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public StoreInfo addStore(StoreInfo base) {
        int count = storeInfoMapper.repeatRecords(base.getStoreName(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        base.setMarkId(snowflake.nextIdStr());
        storeInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    public StoreInfo editStore(StoreInfo base) {
        int count = storeInfoMapper.repeatRecords(base.getStoreName(), base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        storeInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public List<Combobox> getComboboxList() {
        return storeInfoMapper.selectComboboxList();
    }

    @Override
    public void addStoreStaff(BatchVo base) {
        List<StoreItem> list = new LinkedList<>();
        StoreItem item;
        for (String userId : base.getIds()) {
            item = StoreItem.builder().storeId(base.getCommonId()).userId(userId).build();
            list.add(item);
        }
        if (!list.isEmpty()) { storeItemMapper.insertBatch(list); }
    }

    @Override
    public void deleteStoreStaff(StoreItem base) {
        storeItemMapper.deleteItem(base.getStoreId(), base.getUserId());
    }

    @Override
    public String getStoreByStaff(String userId) {
        String storeId = storeItemMapper.selectByStaff(userId);
        ShowAssert.checkTrue(StringUtils.isEmpty(storeId), StatusCode._5020);
        return storeId;
    }
}
