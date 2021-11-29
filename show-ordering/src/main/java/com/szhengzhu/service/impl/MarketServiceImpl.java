package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.MarketCommodity;
import com.szhengzhu.bean.ordering.MarketInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.MarketCommodityMapper;
import com.szhengzhu.mapper.MarketInfoMapper;
import com.szhengzhu.service.MarketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
@Service("marketService")
public class MarketServiceImpl implements MarketService {

    @Resource
    private MarketInfoMapper marketInfoMapper;

    @Resource
    private MarketCommodityMapper marketCommodityMapper;

    @Override
    public PageGrid<MarketInfo> page(PageParam<MarketInfo> pageParam) {
        String sidx = "mark_id".equals(pageParam.getSidx()) ? "create_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        List<MarketInfo> list = marketInfoMapper.selectByExampleSelective(pageParam.getData());
        for (MarketInfo marketInfo : list) {
            List<String> commIdList = marketCommodityMapper.selectCommIds(marketInfo.getMarkId(), 0);
            List<String> discountCommIdList = marketCommodityMapper.selectCommIds(marketInfo.getMarkId(), 1);
            marketInfo.setCommIds(commIdList);
            marketInfo.setDiscountCommIds(discountCommIdList);
        }
        PageInfo<MarketInfo> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Transactional
    @Override
    public String add(MarketInfo marketInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        marketInfo.setMarkId(snowflake.nextIdStr());
        marketInfo.setCreateTime(DateUtil.date());
        List<MarketCommodity> list = addComm(marketInfo);
        if (!list.isEmpty()) {
            Integer exist = marketCommodityMapper.selectExistComm(marketInfo.getCommIds());
            ShowAssert.checkTrue(ObjectUtil.isNotNull(exist), StatusCode._4052);
            marketCommodityMapper.insertBatch(list);
        }
        marketInfoMapper.insertSelective(marketInfo);
        return marketInfo.getMarkId();
    }

    private List<MarketCommodity> addComm(MarketInfo marketInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        List<MarketCommodity> list = new LinkedList<>();
        for (int i = 0, len = marketInfo.getCommIds().size(); i < len; i++) {
            MarketCommodity base = MarketCommodity.builder().markId(snowflake.nextIdStr()).type(0)
                    .commodityId(marketInfo.getCommIds().get(i))
                    .marketId(marketInfo.getMarkId()).sort(i).build();
            list.add(base);
        }
        return list;
    }

    @Transactional
    @Override
    public void modify(MarketInfo marketInfo) {
        marketInfo.setModifyTime(DateUtil.date());
        marketInfoMapper.updateByPrimaryKeySelective(marketInfo);
        // 删除原有的商品数据
        marketCommodityMapper.deleteComm(marketInfo.getMarkId());
        List<MarketCommodity> list = addComm(marketInfo);
        if (!list.isEmpty()) {
            marketCommodityMapper.insertBatch(list);
        }
    }

    @Override
    public void delete(String marketId) {
        marketInfoMapper.deleteByPrimaryKey(marketId);
    }

}
