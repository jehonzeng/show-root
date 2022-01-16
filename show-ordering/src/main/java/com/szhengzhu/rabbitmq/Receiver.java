package com.szhengzhu.rabbitmq;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.szhengzhu.bean.ordering.*;
import com.szhengzhu.bean.ordering.vo.IndentPayVo;
import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.bean.member.vo.TicketTemplateVo;
import com.szhengzhu.code.PrintStatus;
import com.szhengzhu.core.Result;
import com.szhengzhu.mapper.*;
import com.szhengzhu.redis.Redis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class Receiver {

    @Resource
    private Redis redis;

    @Resource
    private IndentMapper indentMapper;

    @Resource
    private IndentDetailMapper indentDetailMapper;

    @Resource
    private CommodityPriceMapper commodityPriceMapper;

    @Resource
    private TicketTemplateMapper ticketTemplateMapper;

    @Resource
    private UserTicketMapper userTicketMapper;

    @Resource
    private ShowMemberClient showMemberClient;

    @Resource
    private PrinterLogMapper printerLogMapper;

    @Resource
    private IndentPayMapper indentPayMapper;

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.CALC_INDENT_BASE_TOTAL, containerFactory = "containerFactory")
    public void calcIndentBaseTotal(String indentId, Message msg, Channel channel) throws IOException {
        try {
            List<IndentDetail> detailList = indentDetailMapper.selectByIndent(indentId);
            BigDecimal baseTotal = BigDecimal.ZERO;
            for (IndentDetail detail : detailList) {
                if (detail.getStatus() == -1) {
                    continue;
                }
                CommodityPrice commodityPrice = commodityPriceMapper
                        .selectByPrimaryKey(detail.getPriceId());
                baseTotal = baseTotal.add(
                        commodityPrice.getBasePrice().multiply(new BigDecimal(detail.getQuantity())));
            }
            indentMapper.updateBaseTotal(indentId, baseTotal);
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
    @RabbitListener(queues = RabbitQueue.GIVE_COUPON, containerFactory = "containerFactory")
    public void giveCoupon(Map<String, String> map, Message msg, Channel channel)
            throws IOException {
        log.info("接收到信息" + new Gson().toJson(map));
        try {
            Result<List<TicketTemplateVo>> result = showMemberClient
                    .getRechargeTickets(map.get("ruleId"));
            List<TicketTemplateVo> tickets = result.getData();
            String userId = map.get("userId");
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            for (TicketTemplateVo ticket : tickets) {
                TicketTemplate template = ticketTemplateMapper
                        .selectByPrimaryKey(ticket.getTemplateId());
                if (template == null) {
                    continue;
                }
                List<UserTicket> list = new LinkedList<>();
                for (int i = 0, len = ticket.getQuantity(); i < len; i++) {
                    UserTicket userTicket = createUserTicket(snowflake, template, userId);
                    list.add(userTicket);
                }
                if (!list.isEmpty()) {
                    userTicketMapper.insertBatch(list);
                }
            }
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
     * 创建用户券对象
     *
     * @param snowflake
     * @param template
     * @param userId
     * @return
     */
    private UserTicket createUserTicket(Snowflake snowflake, TicketTemplate template, String userId) {
        UserTicket userTicket = UserTicket.builder().markId(snowflake.nextIdStr())
                .templateId(template.getMarkId()).userId(userId).name(template.getName())
                .type(template.getType()).description(template.getDescription())
                .discount(template.getDiscount()).limitPrice(template.getLimitPrice())
                .limitStore(template.getLimitStore()).overlayUse(template.getOverlayUse())
                .rankIds(template.getRankIds()).specialDate(template.getSpecialDate())
                .useTime(null).createTime(DateUtil.date()).issueType(false).status(1).createTime(DateUtil.date())
                .build();
        if (template.getMode() == 0) {
            userTicket.setStartTime(template.getStartTime());
            userTicket.setStopTime(template.getStopTime());
        } else if (template.getMode() == 1) {
            userTicket.setStartTime(DateUtil.date());
            userTicket.setStopTime(DateUtil.offsetDay(DateUtil.date(), template.getEffectiveDays()));
        }
        return userTicket;
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.PRINT_LOG_BACK, containerFactory = "containerFactory")
    public void printLogBack(String logId, Message msg, Channel channel) throws IOException {
        String logKey = "print:log:" + logId;
        try {
            log.info("print-log-back:" + logId);
            Integer statusCode = (Integer) redis.get(logKey);
            log.info("logBack:status:" + statusCode + ":" + PrintStatus.getValue(statusCode));
            printerLogMapper.updateStatusInfo(logId, statusCode, PrintStatus.getValue(statusCode));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redis.del(logKey);
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.MEMBER_RECEIVE_TICKET, containerFactory = "containerFactory")
    public void memberReceiveTicket(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("接收:member-receive-ticket：{}", map);
        Integer num = StrUtil.isEmpty(map.get("quantity")) ? 1 : Integer.parseInt(map.get("quantity"));
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        try {
            if (map.get("templateId") != null) {
                for (int i = 0; i < num; i++) {
                    TicketTemplate template = ticketTemplateMapper.selectByPrimaryKey(map.get("templateId"));
                    UserTicket userTicket = createUserTicket(snowflake, template, map.get("userId"));
                    userTicketMapper.insertSelective(userTicket);
                }
            }
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
    @RabbitListener(queues = RabbitQueue.MEMBER_RECEIVE_TICKET_REFUND, containerFactory = "containerFactory")
    public void memberReceiveTicketRefund(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("接收:member-receive-ticket-refund:{}", map);
        int num = Integer.parseInt(map.get("quantity"));
        try {
            for (int i = 0; i < num; i++) {
                List<String> userTicketList = userTicketMapper.queryUserTicket(map.get("userId"), map.get("templateId"));
                if (userTicketList.isEmpty()) {
                    continue;
                }
                for (String markId : userTicketList) {
                    userTicketMapper.deleteMemberTicket(markId);
                }
            }
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
    @RabbitListener(queues = RabbitQueue.MEMBER_DISCOUNT, containerFactory = "containerFactory")
    public void memberDiscount(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("接收:member-discount:{}", map);
        try {
            String indentId = map.get("indentId");
            String memberId = map.get("memberId");
            String ticketId = map.get("ticketId");
            if (StrUtil.isEmpty(memberId)) {
                indentDetailMapper.updateMemberDiscount(BigDecimal.ZERO, null, DateUtil.date(), indentId);
                return;
            }
            //查询订单商品是否有折扣
            List<IndentDetail> indentDetails = indentDetailMapper.selectDiscountProduct(indentId);
            //查询订单支付方式是否有折扣
            List<IndentPayVo> indentPayList = indentPayMapper.selectPayDiscount(indentId);
            if (ObjectUtil.isNotEmpty(indentPayList) || StrUtil.isNotEmpty(ticketId) || ObjectUtil.isNotEmpty(indentDetails)) {
                indentDetailMapper.updateMemberDiscount(BigDecimal.ZERO, null, DateUtil.date(), indentId);
                return;
            }
            BigDecimal memberDiscount = showMemberClient.selectMemberDiscount(memberId).getData();
            if (memberDiscount.compareTo(BigDecimal.ZERO) != 0) {
                indentDetailMapper.updateMemberDiscount(null, NumberUtil.div(memberDiscount, 10), DateUtil.date(), indentId);
            }
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
