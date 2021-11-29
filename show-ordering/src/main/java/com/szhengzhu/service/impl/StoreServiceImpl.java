package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.Store;
import com.szhengzhu.bean.ordering.param.StoreParam;
import com.szhengzhu.bean.ordering.vo.StoreMapVo;
import com.szhengzhu.bean.xwechat.vo.StoreModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.StoreMapper;
import com.szhengzhu.service.StoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("storeService")
public class StoreServiceImpl implements StoreService {

    @Resource
    private StoreMapper storeMapper;

    @Override
    public PageGrid<Store> page(PageParam<StoreParam> pageParam) {
        String sidx = "mark_id".equals(pageParam.getSidx())? "create_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<Store> pageInfo = new PageInfo<>(storeMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public Store getInfo(String storeId) {
        return storeMapper.selectByPrimaryKey(storeId);
    }

    @Override
    public String add(Store store) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        store.setMarkId(markId);
        store.setCreateTime(DateUtil.date());
        storeMapper.insertSelective(store);
        return markId;
    }

    @Override
    public void modify(Store store) {
        store.setModifyTime(DateUtil.date());
        storeMapper.updateByPrimaryKeySelective(store);
    }

    @Override
    public void delete(String storeId) {
        storeMapper.updateStatus(storeId, -1);
    }

    @Override
    public List<StoreMapVo> listByEmployee(String employeeId) {
        return storeMapper.selectMapByEmployee(employeeId);
    }

    @Override
    public List<StoreModel> listLjsStore(String city) {
        return storeMapper.selectLjsStoreByCity(city);
    }
}
