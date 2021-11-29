package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.MealStock;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.MealInfoMapper;
import com.szhengzhu.mapper.MealStockMapper;
import com.szhengzhu.service.MealStockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service("mealStockService")
public class MealStockServiceImpl implements MealStockService {

    @Resource
    private MealStockMapper mealStockMapper;

    @Resource
    private MealInfoMapper mealInfoMapper;

    @Override
    public void add(MealStock base) {
        int count = mealStockMapper.selectCount(base.getMealId(), base.getStorehouseId());
        if (count == 0) {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            base.setMarkId(snowflake.nextIdStr());
            base.setServerStatus(false);
            mealStockMapper.insertSelective(base);
        }
    }

    @Override
    public void modify(MealStock base) {
        mealStockMapper.updateByPrimaryKeySelective(base);
    }

    @Override
    public PageGrid<MealStock> pageStock(PageParam<MealStock> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<MealStock> page = new PageInfo<>(mealStockMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public StockBase getStockInfo(String mealId, String addressId) {
        StockBase stockBase = mealInfoMapper.selectMealInfo(mealId);
        ShowAssert.checkNull(stockBase, StatusCode._4004);
        MealStock mealStock;
        if (!StrUtil.isEmpty(addressId)) {
            mealStock = mealStockMapper.selectMealStockByAddress(mealId, addressId);
        } else {
            mealStock = mealStockMapper.selectMealStock(mealId);
        }
        stockBase.setIsDelivery(mealStock != null);
        stockBase.setCurrentStock(mealStock != null? mealStock.getCurrentStock() : 0);
        stockBase.setStorehouseId(mealStock != null? mealStock.getStorehouseId() : null);
        return stockBase;
    }
    

    @Override
    public void subCurrentStock(ProductInfo productInfo) {
        mealStockMapper.subCurrentStock(productInfo.getProductId(), productInfo.getStorehouseId(),
                productInfo.getQuantity());
    }

    @Override
    public void subTotalStock(ProductInfo productInfo) {
        mealStockMapper.subTotalStock(productInfo.getProductId(), productInfo.getStorehouseId(),
                productInfo.getQuantity());
    }

    @Override
    public void addCurrentStock(ProductInfo productInfo) {
        mealStockMapper.addCurrentStock(productInfo.getProductId(), productInfo.getStorehouseId(),
                productInfo.getQuantity());
    }

    @Override
    public void addTotalStock(ProductInfo productInfo) {
        mealStockMapper.addTotalStock(productInfo.getProductId(), productInfo.getStorehouseId(),
                productInfo.getQuantity());
    }

}
