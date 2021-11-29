package com.szhengzhu.handler.impl;

import com.szhengzhu.annotation.OrderType;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.vo.OrderExportVo;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.context.ProductContext;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.handler.AbstractOrder;
import com.szhengzhu.handler.AbstractProduct;
import com.szhengzhu.util.ImageUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Component
@OrderType(Contacts.TYPE_OF_ORDER)
public class OrderHandler extends AbstractOrder {

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ProductContext productContext;

    @Override
    public Result<?> getExportOrderInfo(String orderId) {
        return showOrderClient.getExportOrderInfo(orderId);
    }

    @Override
    public BufferedImage createExportImage(Object data, InputStream in) {
        return ImageUtils.createOrderImage((OrderExportVo) data, in);
    }

    @Override
    public void addCurrentStock(String orderNo) {
        List<OrderItem> items = getItems(orderNo);
        for (OrderItem item : items) {
            // 一般IO比较多的线程 数量是CPU核数的20~30倍比较合适
            new Thread(() -> {
                AbstractProduct handler = productContext.getInstance(item.getProductType().toString());
                ProductInfo productInfo = getProductInfo(item);
                handler.addCurrentStock(productInfo);
            }).start();
        }
    }

    @Override
    public void subCurrentStock(String orderNo) {
        List<OrderItem> items = getItems(orderNo);
        for (OrderItem item : items) {
            // 一般IO比较多的线程 数量是CPU核数的20~30倍比较合适
            new Thread(() -> {
                AbstractProduct handler = productContext.getInstance(item.getProductType().toString());
                ProductInfo productInfo = getProductInfo(item);
                handler.subCurrentStock(productInfo);
            }).start();
        }
    }

    @Override
    public void subTotalStock(String orderNo) {
        List<OrderItem> items = getItems(orderNo);
        for (OrderItem item : items) {
            new Thread(() -> {
                // 一般IO比较多的线程 数量是CPU核数的20~30倍比较合适
                AbstractProduct handler = productContext.getInstance(item.getProductType().toString());
                ProductInfo productInfo = getProductInfo(item);
                handler.subTotalStock(productInfo);
            }).start();
        }
    }

    @Override
    public void addTotalStock(String orderNo) {
        List<OrderItem> items = getItems(orderNo);
        for (OrderItem item : items) {
            new Thread(() -> {
                // 一般IO比较多的线程 数量是CPU核数的20~30倍比较合适
                AbstractProduct handler = productContext.getInstance(item.getProductType().toString());
                ProductInfo productInfo = getProductInfo(item);
                handler.addTotalStock(productInfo);
            }).start();
        }
    }

    private List<OrderItem> getItems(String orderNo) {
        Result<OrderInfo> orderResult = showOrderClient.getOrderByNo(orderNo);
        List<OrderItem> items = new ArrayList<>();
        if (orderResult.isSuccess()) {
            items = orderResult.getData().getItems();
        }
        return items;
    }

    private ProductInfo getProductInfo(OrderItem item) {
        return ProductInfo.builder().productId(item.getProductId())
                .specIds(item.getSpecificationIds()).storehouseId(item.getStorehouseId())
                .quantity(item.getQuantity()).build();
    }

}
