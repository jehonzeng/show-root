package com.szhengzhu.rabbitmq;

import cn.hutool.core.util.StrUtil;
import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.bean.ordering.TicketExpire;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.core.Result;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生产消息
 *
 * @author Jehon Zeng
 */
@Slf4j
@Data
@Component
public class Sender {

    @Autowired
    private RabbitMessagingTemplate rabbitTemplate;

    @Autowired
    private MappingJackson2MessageConverter converter;

    @Autowired
    private ShowMemberClient showMemberClient;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMessageConverter(converter);
    }

    public void calcIndentBaseTotal(String indentId) {
        rabbitTemplate.convertAndSend(RabbitQueue.CALC_INDENT_BASE_TOTAL, indentId);
    }

    public void addMemberDetail(String detailId, String userId, String indentId) {
        Map<String, String> map = new HashMap<>(4);
        map.put("detailId", detailId);
        map.put("userId", userId);
        map.put("indentId", indentId);
        rabbitTemplate.convertAndSend(RabbitQueue.INDENT_MEMBER_REFUND, map);
    }

    public void changeTablePush(String indentId, String tableId, String tableName) {
        log.info("send:change-table-push");
        Map<String, String> map = new HashMap<>(4);
        map.put("indentId", indentId);
        map.put("tableId", tableId);
        map.put("tableName", tableName);
        rabbitTemplate.convertAndSend(RabbitQueue.CHANGE_TABLE_PUSH, map);
    }

    public void giftIntegral(String userId, int num) {
        log.info("send:gift-integral:{},{}", userId, num);
        Map<String, String> map = new HashMap<>(4);
        map.put("userId", userId);
        map.put("num", String.valueOf(num));
        rabbitTemplate.convertAndSend(RabbitQueue.GIFT_INTEGRAL, map);
    }

    public void remarkInfo(String indentId) {
        log.info("send:indent-remark:{}", indentId);
        rabbitTemplate.convertAndSend(RabbitQueue.INDENT_REMARK, indentId);
    }

//    public void activity(String indentId) {
//        log.info("send:activity:{}", indentId);
//        rabbitTemplate.convertAndSend(RabbitQueue.ACTIVITY, indentId);
//    }

    public void indentRefund(String indentPayId) {
        rabbitTemplate.convertAndSend(RabbitQueue.INDENT_REFUND, indentPayId);
    }

    public void ticketExpire(TicketExpire ticket) {
        log.info("send:ticket-expire:{}", ticket);
        Map<String, String> map = new HashMap<>(4);
        map.put("userId", ticket.getUserId());
        map.put("date", ticket.getExpireTime().toString());
        map.put("total", ticket.getTotal().toString());
        map.put("num", ticket.getNum().toString());
        map.put("name", ticket.getName());
        rabbitTemplate.convertAndSend(RabbitQueue.TICKET_EXPIRE, map);
    }

    /**
     * 会员用餐消费
     */
    public void memberIndentConsume(String indentId) {
        log.info("send:member-consume:{}", indentId);
        Map<String, String> map = new HashMap<>(4);
        map.put("indentId", indentId);
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_INDENT_CONSUME, map);
    }

    /**
     * 会员用餐消费退回
     */
    public void memberIndentConsumeRefund(String indentId) {
        log.info("send:member-consume-refund:{}", indentId);
        Map<String, String> map = new HashMap<>(4);
        map.put("indentId", indentId);
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_INDENT_CONSUME_REFUND, map);
    }

    /**
     * 会员消费赠送投票次数
     *
     * @param userId
     * @param total
     */
    public void sendMatchChance(String userId, String memberId, BigDecimal total) {
        int limit = 299;
        if (total.compareTo(BigDecimal.valueOf(limit)) >= 0) {
            Map<String, Object> map = new HashMap<>();
            if (StrUtil.isEmpty(userId)) {
                Result<MemberAccount> result = showMemberClient.getMemberInfo(memberId);
                userId = result.getData().getUserId();
            }
            if (StrUtil.isEmpty(userId)) {
                return;
            }
            List<Map<String, Object>> mapList = showMemberClient.listMatch().getData();
            for (Map<String, Object> StringMap : mapList) {
                if (StringMap.get("giveChance").equals("1")) {
                    return;
                }
            }
            map.put("userId", userId);
            map.put("quantity", 1);
            rabbitTemplate.convertAndSend(RabbitQueue.MATCH_CHANCE_COUNT, map);
        }
    }

}
