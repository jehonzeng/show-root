package com.szhengzhu.handler.impl;

import com.szhengzhu.annotation.OrderType;
import com.szhengzhu.feign.ShowActivityClient;
import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.feign.ShowOrderClient;
import com.szhengzhu.bean.order.TeambuyGroup;
import com.szhengzhu.bean.order.TeambuyOrder;
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
@OrderType(Contacts.TYPE_OF_TEAMBUY_ORDER)
public class TeambuyHandler extends AbstractOrder {

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowActivityClient showActivityClient;

    @Override
    public Result<?> getExportOrderInfo(String orderId) {
        return showOrderClient.getExportTeambuyOrderInfo(orderId);
    }

    @Override
    public BufferedImage createExportImage(Object data,InputStream in) {
        return ImageUtils.createActivityOrderImage((ExportTemplateVo) data,in);
    }

    @Override
    public void addCurrentStock(String orderNo) {
        ProductInfo productInfo = getProductInfoByNo(orderNo);
        if (productInfo == null) {
            return;
        }
        showGoodsClient.addGoodsCurrentStock(productInfo);
        showActivityClient.addTeambuyStock(productInfo.getActivityId());
    }

    @Override
    public void subCurrentStock(String orderNo) {
        ProductInfo productInfo = getProductInfoByNo(orderNo);
        if (productInfo == null) {
            return;
        }
        showGoodsClient.subGoodsCurrentStock(productInfo);
        showActivityClient.subTeambuyStock(productInfo.getActivityId());
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

    /**
     * 通过订单号获取商品信息
     * 
     * @date 2019年10月18日 下午2:22:08
     * @param orderNo
     * @return
     */
    private ProductInfo getProductInfoByNo(String orderNo) {
        ProductInfo productInfo = null;
        Result<TeambuyOrder> orderResult = showOrderClient.getTeambuyOrderByNo(orderNo);
        if (orderResult.isSuccess()) {
            TeambuyOrder order = orderResult.getData();
            Result<TeambuyGroup> groupResult = showOrderClient.getTeambuyGroupInfo(order.getGroupId());
            // 活动id
            String teambuyId = null;
            if (groupResult.isSuccess()) {
                teambuyId = groupResult.getData().getTeambuyId();
            }
            productInfo = ProductInfo.builder().productId(order.getGoodsId())
                    .specIds(order.getSpecificationIds()).storehouseId(order.getStorehouseId())
                    .quantity(order.getQuantity()).activityId(teambuyId).build();
        }
        return productInfo;
    }
}
