package com.szhengzhu.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.GoodsSpecification;
import com.szhengzhu.bean.goods.GoodsStock;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.vo.GoodsBaseVo;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.GoodsSpecificationMapper;
import com.szhengzhu.mapper.GoodsStockMapper;
import com.szhengzhu.service.GoodsStockService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("goodsStockService")
public class GoodsStockServiceImpl implements GoodsStockService {

    @Autowired
    private GoodsStockMapper goodsStockMapper;

    @Autowired
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @Override
    public Result<?> addGoodsStock(GoodsStock base) {
        if (base == null || StringUtils.isEmpty(base.getStorehouseId())
                || StringUtils.isEmpty(base.getGoodsId()))
            return new Result<>(StatusCode._4004);
        IdGenerator generator = IdGenerator.getInstance();
        List<GoodsStock> stocks = new LinkedList<>();
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper
                .selectByGoods(base.getGoodsId());
        List<String> goodsStockSpec = goodsStockMapper.selectGoodsStockSpec(base.getGoodsId(),
                base.getStorehouseId());
        for (int index = 0; index < goodsSpecifications.size(); index++) {
            if (goodsStockSpec.size() == 0)
                break;
            for (int i = 0; i < goodsStockSpec.size(); i++) {
                if (goodsSpecifications.get(index).getSpecificationIds()
                        .equals(goodsStockSpec.get(i)))
                    goodsSpecifications.remove(index);
            }
        }
        for (int index = 0; index < goodsSpecifications.size(); index++) {
            String markId = generator.nexId();
            GoodsStock stock = create(markId, base,
                    goodsSpecifications.get(index).getSpecificationIds());
            stocks.add(stock);
        }
        if (stocks.size() > 0)
            goodsStockMapper.insertBatch(stocks);
        return new Result<>(base);
    }

    private GoodsStock create(String markId, GoodsStock base, String specificationIds) {
        GoodsStock stock = new GoodsStock();
        stock.setMarkId(markId);
        stock.setStorehouseId(base.getStorehouseId());
        stock.setGoodsId(base.getGoodsId());
        stock.setTotalStock(base.getTotalStock());
        stock.setCurrentStock(base.getCurrentStock());
        stock.setSpecificationIds(specificationIds);
        stock.setServerStatus(false);
        return stock;
    }

    @Override
    public Result<?> editGoodsStock(GoodsStock base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4004);
        goodsStockMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<StockVo>> getPage(PageParam<GoodsStock> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<StockVo> page = new PageInfo<>(
                goodsStockMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> getDetailedInfos(String markId) {
        GoodsBaseVo base = goodsStockMapper.selectInfos(markId);
        return new Result<>(base);
    }

    @Override
    public Result<?> getGoodsStockInfo(String markId) {
        return new Result<>(goodsStockMapper.selectByPrimaryKey(markId));
    }

    @Override
    public Result<List<StockVo>> listGoodsStock(List<String> markIds) {
        List<StockVo> stocks = goodsStockMapper.selectGoodsStocks(markIds);
        return new Result<>(stocks);
    }

    @Override
    public void reduceStock(OrderItem item, String addressId) {
        
    }
    
    @Override
    public Result<StockBase> getStockInfo(String goodsId, String specIds, String addressId) {
        StockBase specGoodsBase = goodsSpecificationMapper.selectSpecGoodsInfo(goodsId, specIds);
        if (specGoodsBase == null)
            return new Result<>(StatusCode._4004);
        boolean isDelivery = true;
        Integer currentStock = null;
        if (StringUtils.isEmpty(addressId)) {
            currentStock = 1000;
        } else {
            Map<String, Integer> deliveryStock = goodsStockMapper.selectDeliveryAndStock(addressId, goodsId, specIds);
            if (deliveryStock != null) {
                isDelivery = deliveryStock.containsKey("isDelivery")? ((Number) deliveryStock.get("isDelivery")).intValue() == 1 : false;
                currentStock = deliveryStock.containsKey("currentStock")? ((Number) deliveryStock.get("currentStock")).intValue() : 1;
            }
        }
        specGoodsBase.setIsDelivery(isDelivery);
        specGoodsBase.setCurrentStock(currentStock);
        return new Result<>(specGoodsBase);
    }

}
