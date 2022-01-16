package com.szhengzhu.rabbitmq;

import com.google.gson.Gson;
import com.szhengzhu.bean.member.vo.ReceiveVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产消息
 *
 * @author Jehon Zeng
 */
@Slf4j
@Component
public class Sender {

    @Autowired
    private RabbitMessagingTemplate rabbitTemplate;

    @Autowired
    private MappingJackson2MessageConverter converter;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMessageConverter(converter);
    }

    public void giveCoupon(Map<String, String> map) {
        log.info("发放券：" + new Gson().toJson(map));
        rabbitTemplate.convertAndSend(RabbitQueue.GIVE_COUPON, map);
    }

    public void memberReceiveTicket(Map<String, String> map) {
        log.info("send:member-receive-ticket:{}", map);
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_RECEIVE_TICKET, map);
    }

    public void memberReceiveTicketRefund(Map<String, String> map) {
        log.info("send:member-receive-ticket-refund:{}", map);
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_RECEIVE_TICKET_REFUND, map);
    }

    /**
     * 推送（参加活动领取菜苗）
     */
    public void dishesActivity(Date beginTime, Date endTime, String userId) {
        log.info("send:dishes-activity:{}{}{}", beginTime, endTime, userId);
        Map<String, Object> map = new HashMap<>();
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        map.put("userId", userId);
        rabbitTemplate.convertAndSend(RabbitQueue.DISHES_ACTIVITY, map);
    }

    /**
     * 推送（领取机会快过期）
     */
    public void expiring(String userId, String name) {
        log.info("send:expiring:{}", userId);
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("name", name);
        rabbitTemplate.convertAndSend(RabbitQueue.EXPIRE, map);
    }

    /**
     * 推送（菜品阶段）
     */
    public void dishesStage(ReceiveVo receiveVo) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", receiveVo.getUserId());
        map.put("name", receiveVo.getDishesInfo().getDishesName());
        map.put("stage", receiveVo.getDishesStage().getStage());
        map.put("beginDay", receiveVo.getDishesStage().getBeginDays().toString());
        map.put("endDays", receiveVo.getDishesStage().getEndDays().toString());
        log.info("send:dishes-stage:{}", map);
        rabbitTemplate.convertAndSend(RabbitQueue.DISHES_STAGE, map);
    }

    /**
     * 推送（成熟阶段）
     */
    public void mature(ReceiveVo receiveVo) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", receiveVo.getUserId());
        map.put("name", receiveVo.getDishesInfo().getDishesName());
        map.put("stage", receiveVo.getDishesStage().getStage());
        log.info("send:dishes-stage:{}", map);
        rabbitTemplate.convertAndSend(RabbitQueue.MATURE, map);
    }

    /**
     * 会员生日推送券String userId, String accountNo
     */
    public void birthdayTicket(Map<String, String> map) {
        log.info("send:birthday-ticket:{}", map);
        rabbitTemplate.convertAndSend(RabbitQueue.BIRTHDAY_TICKET, map);
    }

    public void checkAccount(String userId, Boolean flag) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("flag", flag);
        rabbitTemplate.convertAndSend(RabbitQueue.INTEGRAL_ACCOUNT_CHECK, map);
    }

    public void sendTeamStatus(String matchId, String teamId, Integer teamStatus, Boolean isLast) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("matchId", matchId);
        map.put("teamId", teamId);
        map.put("teamStatus", teamStatus);
        map.put("isLast", isLast);
        rabbitTemplate.convertAndSend(RabbitQueue.SEND_TEAM_STATUS, map);
    }

    public void integralExpire(Map<String, String> map) {
        log.info("send:integral-expire:{}", map);
        rabbitTemplate.convertAndSend(RabbitQueue.INTEGRAL_EXPIRE, map);
    }

    public void lotteryResult(String prizeId, String userId) {
        log.info("send:lottery-result:{},{}", prizeId, userId);
        Map<String, Object> map = new HashMap<>(4);
        map.put("prizeId", prizeId);
        map.put("userId", userId);
        rabbitTemplate.convertAndSend(RabbitQueue.LOTTERY_RESULT, map);
    }

    public void memberCombo(String markId, String userId, Integer quantity) {
        log.info("send:member-combo:{},{},{}", markId, userId, quantity);
        Map<String, String> map = new HashMap<>(4);
        map.put("markId", markId);
        map.put("userId", userId);
        map.put("quantity", quantity.toString());
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_COMBO, map);
    }
}
