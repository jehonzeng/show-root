package com.szhengzhu.rabbitmq;

import com.rabbitmq.client.Channel;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.bean.ordering.Indent;
import com.szhengzhu.bean.ordering.IndentPay;
import com.szhengzhu.bean.ordering.PayRefund;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import weixin.popular.bean.paymch.SecapiPayRefundResult;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class Receiver {

    @Resource
    private WechatConfig config;

    @Resource
    private ShowOrderingClient showOrderingClient;

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.INDENT_REFUND, containerFactory = "containerFactory")
    public void indentRefund(String indentPayId, Message msg, Channel channel) throws IOException {
        log.info("退款：{}", indentPayId);
        try {
            Result<IndentPay> payResult = showOrderingClient.getIndentPayInfo(indentPayId);
            if (!payResult.isSuccess()) {
                return;
            }
            IndentPay indentPay = payResult.getData();
            Result<Indent> indentResult = showOrderingClient.getIndentInfo(indentPay.getIndentId());
            if (!indentResult.isSuccess()) {
                return;
            }
            String refundNo = ShowUtils.createRefundNo();
            Result<String> result = showOrderingClient.getRefundNo(indentPayId);
            if (result.isSuccess()) {
                refundNo = result.getData();
            }
            SecapiPayRefundResult refundResult = null;
            try {
                int totalFee = indentPay.getPayAmount().multiply(new BigDecimal(100)).intValue();
                int refundFee = totalFee;
                refundResult = WechatUtils.indentRefund(config, indentPay.getIndentId(), refundNo, totalFee, refundFee);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (refundResult == null) {
                PayRefund refund = new PayRefund();
                refund.setPayId(indentPay.getMarkId());
                refund.setRefundNo(refundNo);
                refund.setRefundStatus(0);
                refund.setTotalFee(indentPay.getPayAmount());
                showOrderingClient.addRefundBack(refund);
                return;
            }
            log.info("退款调用接口结果：{}", refundResult);
            analysisResult(refundResult, indentPay, refundNo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }

    /**
     * 分析请求微信退款返回的结果
     *
     * @param refundResult
     * @param indentPay
     * @param refundNo
     * @return
     * @date 2019年10月18日 下午3:34:33
     */
    private void analysisResult(SecapiPayRefundResult refundResult, IndentPay indentPay, String refundNo) {
        int status = 0;
        String message;
        if ("SUCCESS".equals(refundResult.getReturn_code())) {
            if ("FAIL".equals(refundResult.getResult_code())) {
                message = "Refund failed. error code: " + refundResult.getErr_code() + ". error message: "
                        + refundResult.getErr_code_des();
            } else {
                status = 1;
                message = "Refund successfully.";
            }
        } else {
            message = "Return code: " + refundResult.getReturn_code() + ", return message: "
                    + refundResult.getErr_code_des();
        }
        log.info(message);
        // 记录
        PayRefund refund = PayRefund.builder().payId(indentPay.getMarkId()).refundNo(refundNo)
                .refundStatus(status).totalFee(indentPay.getPayAmount()).refundInfo(message).build();
        showOrderingClient.addRefundBack(refund);
    }

    private void onMessage(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }
}
