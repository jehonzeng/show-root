package com.szhengzhu.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.GoodsSales;
import com.szhengzhu.bean.ordering.param.GoodSaleParam;
import com.szhengzhu.bean.ordering.param.GoodSaleRankParam;
import com.szhengzhu.bean.ordering.param.GoodTypeSaleParam;
import com.szhengzhu.bean.ordering.param.IndexParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.IndexMapper;
import com.szhengzhu.service.IndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service("indexService")
public class IndexServiceImpl implements IndexService {

    @Resource
    private IndexMapper indexMapper;

    @Override
    public Map<String, Object> todayTurnover(String storeId) {
        Map<String, Object> map = indexMapper.todayTurnover(storeId);
        map.put("todayNetReceipts", indexMapper.todayNetReceipts(storeId));
        return map;
    }

    @Override
    public List<Map<String, Object>> weekTurnover(String storeId) {
        return indexMapper.weekTurnover(storeId);
    }

    @Override
    public PageGrid<GoodSaleRankParam> goodsSalesRank(PageParam<?> pageParam, String storeId) {
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageInfo<GoodSaleRankParam> pageInfo = new PageInfo<>(indexMapper.goodsSalesRank(storeId));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public PageGrid<GoodSaleParam> goodsSaleCompare(PageParam<?> pageParam, String storeId) {
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageInfo<GoodSaleParam> pageInfo = new PageInfo<>(indexMapper.goodsSaleCompare(storeId));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public List<GoodTypeSaleParam> goodsTypeSale(String storeId) {
        return indexMapper.goodsTypeSale(storeId);
    }

    @Override
    public IndexParam netReceipts(String storeId) {
        IndexParam indexParam = new IndexParam();
        indexParam.setTodayNetReceipts(indexMapper.todayNetReceipts(storeId));
        indexParam.setYesterdayNetReceipts(indexMapper.yesterdayNetReceipts(storeId));
        indexParam.setTodayTimeSlotIncome(indexMapper.todayTimeSlotIncome(storeId));
        indexParam.setYesterdayTimeSlotIncome(indexMapper.yesterdayTimeSlotIncome(storeId));
        return indexParam;
    }

    @Override
    public Map<String, Object> info(String storeId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> todayTurnover = indexMapper.todayTurnover(storeId);
        Map<String, Object> yesterdaySituation = indexMapper.yesterdaySituation(storeId);
        map.put("todayTurnover", todayTurnover);
        map.put("yesterdaySituation", yesterdaySituation);
        return map;
    }

    @Override
    public List<GoodSaleRankParam> goodsSales(GoodsSales goodsSales) {
        return indexMapper.goodsSales(goodsSales);
    }

    @Override
    public Map<String,Object> weekNetReceipts(String storeId) {
        return indexMapper.weekNetReceipts(storeId);
    }
}
