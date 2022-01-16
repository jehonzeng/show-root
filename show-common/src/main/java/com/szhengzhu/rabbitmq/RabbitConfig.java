package com.szhengzhu.rabbitmq;

import com.szhengzhu.core.Contacts;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {

    @Bean
    public Queue subSceneGoodsStock() {
        return new Queue(RabbitQueue.SUB_SCENE_GOOD_STOCK);
    }

    @Bean
    public Queue receiveGoods() {
        return new Queue(RabbitQueue.RECEIVE_GOODS);
    }

    @Bean
    public Queue addSceneGoodsStock() {
        return new Queue(RabbitQueue.ADD_SCENE_GOODS_STOCK);
    }

    @Bean
    public Queue addCurrentStock() {
        return new Queue(RabbitQueue.ADD_CURRENT_STOCK);
    }

    @Bean
    public Queue subCurrentStock() {
        return new Queue(RabbitQueue.SUB_CURRENT_STOCK);
    }

    @Bean
    public Queue subTotalStock() {
        return new Queue(RabbitQueue.SUB_TOTAL_STOCK);
    }

    @Bean
    public Queue addTotalStock() {
        return new Queue(RabbitQueue.ADD_TOTAL_STOCK);
    }

    @Bean
    public Queue scanWinner() {
        return new Queue(RabbitQueue.SCAN_WINNER);
    }

    @Bean
    public Queue indentMemberRefund() {
        return new Queue(RabbitQueue.INDENT_MEMBER_REFUND);
    }

    @Bean
    public Queue activity() {
        return new Queue(RabbitQueue.ACTIVITY);
    }

    @Bean
    public Queue clearCoupon() {
        return new Queue(RabbitQueue.CLEAR_COUPON);
    }

    @Bean
    public Queue sendCoupon() {
        return new Queue(RabbitQueue.SEND_COUPON);
    }

    @Bean
    public Queue sendVoucher() {
        return new Queue(RabbitQueue.SEND_VOUCHER);
    }

    @Bean
    public Queue calcIndentBaseTotal() {
        return new Queue(RabbitQueue.CALC_INDENT_BASE_TOTAL);
    }

    @Bean
    public Queue giveCoupon() {
        return new Queue(RabbitQueue.GIVE_COUPON);
    }

    @Bean
    public Queue printLogBack() {
        return new Queue(RabbitQueue.PRINT_LOG_BACK);
    }

    @Bean
    public Queue giftIntegral() {
        return new Queue(RabbitQueue.GIFT_INTEGRAL);
    }

    @Bean
    public Queue remarkInfo() {
        return new Queue(RabbitQueue.INDENT_REMARK);
    }

    @Bean
    public Queue refreshToken() {
        return new Queue(RabbitQueue.REFRESH_TOKEN);
    }

    @Bean
    public Queue modifyWechatStatus() {
        return new Queue(RabbitQueue.MODIFY_WECHAT_STATUS);
    }

    @Bean
    public Queue receiveGift() {
        return new Queue(RabbitQueue.RECEIVE_GIFT);
    }

    @Bean
    public Queue sendManageMessage() {
        return new Queue(RabbitQueue.SEND_MANAGE_MESSAGE);
    }

    @Bean
    public Queue sendOrderConfirmMsg() {
        return new Queue(RabbitQueue.SEND_ORDER_CONFIRM_MSG);
    }

    @Bean
    public Queue sendOrderDeliveryMsg() {
        return new Queue(RabbitQueue.SEND_ORDER_DELIVERY_MSG);
    }

    @Bean
    public Queue orderRefund() {
        return new Queue(RabbitQueue.ORDER_REFUND);
    }

    @Bean
    public Queue showRobot() {
        return new Queue(RabbitQueue.SHOW_ROBOT);
    }

    @Bean
    public Queue sceneOrderRefund() {
        return new Queue(RabbitQueue.SCENE_ORDER_REFUND);
    }

    @Bean
    public Queue changeTablePush() {
        return new Queue(RabbitQueue.CHANGE_TABLE_PUSH);
    }

    @Bean
    public Queue mature() {
        return new Queue(RabbitQueue.MATURE);
    }

    @Bean
    public Queue dishesActivity() {
        return new Queue(RabbitQueue.DISHES_ACTIVITY);
    }

    @Bean
    public Queue dishesStage() {
        return new Queue(RabbitQueue.DISHES_STAGE);
    }

    @Bean
    public Queue expire() {
        return new Queue(RabbitQueue.EXPIRE);
    }

    @Bean
    public Queue indentRefund() {
        return new Queue(RabbitQueue.INDENT_REFUND);
    }

    @Bean
    public Queue contactUser() {
        return new Queue(RabbitQueue.CONTACT_USER);
    }

    @Bean
    public Queue ticketExpire() {
        return new Queue(RabbitQueue.TICKET_EXPIRE);
    }

    @Bean
    public Queue birthdayTicket() {
        return new Queue(RabbitQueue.BIRTHDAY_TICKET);
    }

    @Bean
    public Queue integralAccountCheck() {
        return new Queue(RabbitQueue.INTEGRAL_ACCOUNT_CHECK);
    }

    @Bean
    public Queue memberReceiveTicket() {
        return new Queue(RabbitQueue.MEMBER_RECEIVE_TICKET);
    }

    @Bean
    public Queue memberReceiveTicketRefund() {
        return new Queue(RabbitQueue.MEMBER_RECEIVE_TICKET_REFUND);
    }

    @Bean
    public Queue memberConsume() {
        return new Queue(RabbitQueue.MEMBER_CONSUME);
    }

    @Bean
    public Queue memberConsumeRefund() {
        return new Queue(RabbitQueue.MEMBER_CONSUME_REFUND);
    }

    @Bean
    public Queue memberGrade() {
        return new Queue(RabbitQueue.MEMBER_GRADE);
    }

    @Bean
    public Queue memberDiscount() {
        return new Queue(RabbitQueue.MEMBER_DISCOUNT);
    }

    @Bean
    public Queue memberLottery() {
        return new Queue(RabbitQueue.MEMBER_LOTTERY);
    }

    @Bean
    public Queue memberSign() {
        return new Queue(RabbitQueue.MEMBER_SIGN);
    }

    @Bean
    public Queue integralExchange() {
        return new Queue(RabbitQueue.INTEGRAL_EXCHANGE);
    }

    @Bean
    public Queue integralExpire() {
        return new Queue(RabbitQueue.INTEGRAL_EXPIRE);
    }

    @Bean
    public Queue matchChanceCount() {
        return new Queue(RabbitQueue.MATCH_CHANCE_COUNT);
    }

    @Bean
    public Queue sendTeamStatus() {
        return new Queue(RabbitQueue.SEND_TEAM_STATUS);
    }

    @Bean
    public Queue memberIndentConsume() {
        return new Queue(RabbitQueue.MEMBER_INDENT_CONSUME);
    }

    @Bean
    public Queue memberIndentConsumeRefund() {
        return new Queue(RabbitQueue.MEMBER_INDENT_CONSUME_REFUND);
    }

    @Bean
    public Queue lotteryResult() {
        return new Queue(RabbitQueue.LOTTERY_RESULT);
    }

    @Bean
    public Queue memberCombo() {
        return new Queue(RabbitQueue.MEMBER_COMBO);
    }

    @Bean
    public Queue reservation() {
        return new Queue(RabbitQueue.RESERVATION_NOTIFY);
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(jackson2Converter());
        return factory;
    }

    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory containerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setPrefetchCount(Contacts.RABBITMQ_DEF_PREFETCH);// rabmq一次性待消费的消息个数
        factory.setConcurrentConsumers(Contacts.RABBITMQ_DEF_CONCURRENT);// rabmq并发消费者的个数
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
