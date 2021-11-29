package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.szhengzhu.bean.ordering.CommodityPrice;
import com.szhengzhu.mapper.CommodityPriceMapper;
import com.szhengzhu.service.CommodityPriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author jehon
 */
@Service("commodityPriceService")
public class CommodityPriceServiceImpl implements CommodityPriceService {

    @Resource
    private CommodityPriceMapper commodityPriceMapper;

    @Transactional
    @Override
    public String addPrice(CommodityPrice commodityPrice) {
        checkPriceType(commodityPrice.getPriceType(), commodityPrice.getCommodityId());
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        commodityPrice.setMarkId(markId);
        commodityPrice.setCreateTime(DateUtil.date());
        commodityPriceMapper.insertSelective(commodityPrice);
        return markId;
    }

    /**
     * 积分兑换只有一个积分价格
     *
     * @param priceType
     * @param commodityId
     */
    private void checkPriceType(int priceType, String commodityId) {
        if (priceType == 1) {
            commodityPriceMapper.updateAllPriceType(commodityId);
        }
    }

    @Transactional
    @Override
    public void modifyPrice(CommodityPrice commodityPrice) {
        checkPriceType(commodityPrice.getPriceType(), commodityPrice.getCommodityId());
        commodityPrice.setModifyTime(DateUtil.date());
        if (Boolean.TRUE.equals(commodityPrice.getDefaults())) {
            commodityPriceMapper.cancelDefaultsByCommodityId(commodityPrice.getCommodityId());
        }
        commodityPriceMapper.updateByPrimaryKeySelective(commodityPrice);
    }

    @Transactional
    @Override
    public void deletePrice(String priceId) {
        CommodityPrice commodityPrice = commodityPriceMapper.selectByPrimaryKey(priceId);
        if (Boolean.TRUE.equals(commodityPrice.getDefaults())) {
            commodityPriceMapper.updateDefaults(commodityPrice.getCommodityId());
        }
        commodityPriceMapper.deleteByPrimaryKey(priceId);
    }
}
