package com.szhengzhu.handler.impl;

import com.szhengzhu.annotation.OrderType;
import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.vo.ExportTemplateVo;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.handler.AbstractOrder;
import com.szhengzhu.util.ImageUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @author Jehon Zeng
 */
@Component
@OrderType(Contacts.TYPE_OF_SECKILL_ORDER)
public class SeckillHandler extends AbstractOrder {

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowActivityClient showActivityClient;

    @Override
    public Result<?> getExportOrderInfo(String orderId) {
        return showOrderClient.getExportSekillOrderInfo(orderId);
    }

    @Override
    public BufferedImage createExportImage(Object data, InputStream in) {
        return ImageUtils.createActivityOrderImage((ExportTemplateVo) data, in);
    }

    @Override
    public void addCurrentStock(String orderNo) {
        ProductInfo productInfo = getProductInfoByNo(orderNo);
        if (productInfo == null) {
            return;
        }
        showGoodsClient.addGoodsCurrentStock(productInfo);
        showActivityClient.addSeckillStock(productInfo.getActivityId());
    }

    @Override
    public void subCurrentStock(String orderNo) {
        ProductInfo productInfo = getProductInfoByNo(orderNo);
        if (productInfo == null) {
            return;
        }
        showGoodsClient.subGoodsCurrentStock(productInfo);
        showActivityClient.subSeckillStock(productInfo.getActivityId());
    }

    @Override
    public void subTotalStock(String orderNo) {
        // 此时活动里设定的库存已减，不需要再减库存
        ProductInfo productInfo = getProductInfoByNo(orderNo);
        showGoodsClient.subGoodsTotalStock(productInfo);
    }

    @Override
    public void addTotalStock(String orderNo) {
        // 此时活动设定的库在添加当前库存的时候已加，不需要再操作
        ProductInfo productInfo = getProductInfoByNo(orderNo);
        showGoodsClient.addGoodsTotalStock(productInfo);
    }

    private ProductInfo getProductInfoByNo(String orderNo) {
        ProductInfo productInfo = null;
        Result<SeckillOrder> orderResult = showOrderClient.getSeckillOrderByNo(orderNo);
        if (orderResult.isSuccess()) {
            SeckillOrder order = orderResult.getData();
            productInfo = ProductInfo.builder().productId(order.getGoodsId())
                    .specIds(order.getSpecificationIds()).storehouseId(order.getStorehouseId())
                    .quantity(order.getQuantity()).activityId(order.getSeckillId()).build();
        }
        return productInfo;
    }

}
