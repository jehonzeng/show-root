package com.szhengzhu.rabbitmq;

import com.rabbitmq.client.Channel;
import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.UserCouponService;
import com.szhengzhu.service.UserVoucherService;
import com.szhengzhu.util.CouponUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jehon Zeng
 */
@Slf4j
@Component
public class Receiver {

    @Resource
    private UserCouponService userCouponService;

    @Resource
    private UserVoucherService userVoucherService;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.CLEAR_COUPON, containerFactory = "containerFactory")
    public void clearCoupon(String text, Message msg, Channel channel) throws IOException {
        log.info("coupon-clear:{}", text);
        try {
            userCouponService.overdue();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.SEND_COUPON, containerFactory = "containerFactory")
    public void sendCoupons(String coupon, Message msg, Channel channel) throws IOException {
        log.info("coupon-send: {}", coupon);
        try {
            String[] data = coupon.split("&&");
            Result<CouponTemplate> result = showBaseClient.getCouponTemplate(data[0]);
            List<UserCoupon> list = new ArrayList<>();
            if (result.getData() != null) {
                list = CouponUtils.createCoupon(result.getData(), data[1]);
            }
            userCouponService.addUserCoupon(list);
            log.info("发放优惠券");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.SEND_VOUCHER, containerFactory = "containerFactory")
    public void voucherSend(String text, Message msg, Channel channel) throws IOException {
        log.info("voucher-send: {}", text);
        try {
            String[] voucher = text.split("&&");
            Result<GoodsVoucher> voucherResult = showGoodsClient.getGoodsVoucherInfo(voucher[0]);
            if (!voucherResult.isSuccess()) {
                log.info("发放商品券:{}", StatusCode._5009);
            }
            userVoucherService.addGoodsVoucher(voucher[1], voucherResult.getData());
            log.info("发放商品券");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }

    private void onMessage(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }
}
