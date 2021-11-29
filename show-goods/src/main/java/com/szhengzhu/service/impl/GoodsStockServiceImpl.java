package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.GoodsSpecification;
import com.szhengzhu.bean.goods.GoodsStock;
import com.szhengzhu.bean.vo.GoodsBaseVo;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.GoodsSpecificationMapper;
import com.szhengzhu.mapper.GoodsStockMapper;
import com.szhengzhu.service.GoodsStockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service("goodsStockService")
public class GoodsStockServiceImpl implements GoodsStockService {

    @Resource
    private GoodsStockMapper goodsStockMapper;

    @Resource
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @CheckGoodsChange
    @Override
    public GoodsStock addGoodsStock(GoodsStock base) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        List<GoodsStock> stocks = new LinkedList<>();
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByGoods(base.getGoodsId());
        ShowAssert.checkTrue(goodsSpecifications.isEmpty(), StatusCode._4019);
        List<String> goodsStockSpec = goodsStockMapper.selectGoodsStockSpec(base.getGoodsId(), base.getStorehouseId());
        goodsSpecifications = goodsSpecifications.stream().filter(goodsSpecification -> !goodsStockSpec.contains(goodsSpecification.getSpecificationIds())).collect(Collectors.toList());
        goodsSpecifications.forEach(goodsSpecification -> {
            String markId = snowflake.nextIdStr();
            GoodsStock stock = create(markId, base, goodsSpecification.getSpecificationIds());
            stocks.add(stock);
        });
        if (!stocks.isEmpty()) {
            goodsStockMapper.insertBatch(stocks);
        }
        return base;
    }

    private GoodsStock create(String markId, GoodsStock base, String specificationIds) {
        return GoodsStock.builder().markId(markId).storehouseId(base.getStorehouseId()).goodsId(base.getGoodsId())
                .totalStock(base.getTotalStock()).currentStock(base.getCurrentStock()).specificationIds(specificationIds).serverStatus(false).build();
    }

    @CheckGoodsChange
    @Override
    public GoodsStock modifyGoodsStock(GoodsStock base) {
        goodsStockMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<StockVo> getPage(PageParam<GoodsStock> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<StockVo> page = new PageInfo<>(goodsStockMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public GoodsBaseVo getDetailInfos(String markId) {
        return goodsStockMapper.selectInfos(markId);
    }

    @Override
    public GoodsStock getGoodsStockInfo(String markId) {
        return goodsStockMapper.selectByPrimaryKey(markId);
    }

    @Override
    public List<StockVo> listGoodsStock(List<String> markIds) {
        return goodsStockMapper.selectGoodsStockIds(markIds);
    }

    @Override
    public StockBase getStockInfo(String goodsId, String specIds, String addressId) {
        StockBase specGoodsBase = goodsSpecificationMapper.selectSpecGoodsInfo(goodsId, specIds);
        ShowAssert.checkNull(specGoodsBase, StatusCode._4004);
        GoodsStock goodsStock;
        if (!StrUtil.isEmpty(addressId)) {
            goodsStock = goodsStockMapper.selectGoodsStockByAddress(addressId, goodsId, specIds);
        } else {
            goodsStock = goodsStockMapper.selectGoodsStock(goodsId, specIds);
        }
        int currentStock = goodsStock != null ? goodsStock.getCurrentStock() : 0;
        specGoodsBase.setIsDelivery(goodsStock != null);
        specGoodsBase.setCurrentStock(currentStock);
        return specGoodsBase;
    }

    @Override
    public void subCurrentStock(ProductInfo productInfo) {
        goodsStockMapper.subCurrentStock(productInfo.getProductId(), productInfo.getSpecIds(),
                productInfo.getStorehouseId(), productInfo.getQuantity());
    }

    @Override
    public void subTotalStock(ProductInfo productInfo) {
        goodsStockMapper.subTotalStock(productInfo.getProductId(), productInfo.getSpecIds(),
                productInfo.getStorehouseId(), productInfo.getQuantity());
    }

    @Override
    public void addCurrentStock(ProductInfo productInfo) {
        goodsStockMapper.addCurrentStock(productInfo.getProductId(), productInfo.getSpecIds(),
                productInfo.getStorehouseId(), productInfo.getQuantity());
    }

    @Override
    public void addTotalStock(ProductInfo productInfo) {
        goodsStockMapper.addTotalStock(productInfo.getProductId(), productInfo.getSpecIds(),
                productInfo.getStorehouseId(), productInfo.getQuantity());
    }
}
