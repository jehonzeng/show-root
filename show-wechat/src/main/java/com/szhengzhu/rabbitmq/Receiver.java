package com.szhengzhu.rabbitmq;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.rabbitmq.client.Channel;
import com.szhengzhu.bean.member.ComboBuy;
import com.szhengzhu.bean.member.MatchTeam;
import com.szhengzhu.bean.member.PrizeInfo;
import com.szhengzhu.bean.ordering.TicketTemplate;
import com.szhengzhu.feign.*;
import com.szhengzhu.bean.base.ScanReply;
import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.bean.member.vo.MemberAccountVo;
import com.szhengzhu.bean.order.*;
import com.szhengzhu.bean.ordering.IndentInfo;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.wechat.vo.OrderBase;
import com.szhengzhu.code.IndentStatus;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.context.OrderContext;
import com.szhengzhu.core.*;
import com.szhengzhu.handler.AbstractOrder;
import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.MediaType;
import weixin.popular.bean.message.message.MiniprogrampageMessage;
import weixin.popular.bean.message.message.MiniprogrampageMessage.Miniprogrampage;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageMiniProgram;
import weixin.popular.bean.paymch.SecapiPayRefundResult;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * @author Jehon Zeng
 */
@Slf4j
@Component
public class Receiver {

    @Resource
    private Sender sender;

    @Resource
    private WechatConfig config;

    @Resource
    private OrderContext orderContext;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ShowMemberClient showMemberClient;

    @Resource
    private ShowOrderingClient showOrderingClient;

    private void onMessage(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 用户下单成功，给管理员发送信息
     *
     * @param orderNo
     * @param msg
     * @param channel
     * @throws IOException
     * @date 2019年9月3日 下午3:30:35
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.SEND_MANAGE_MESSAGE, containerFactory = "containerFactory")
    public void sendManageMessage(String orderNo, Message msg, Channel channel) throws IOException {
        log.info("received:send-manage-message:{}", orderNo);
        try {
            if (StrUtil.isEmpty(orderNo)) {
                return;
            }
            String type = Character.toString(orderNo.charAt(0));
            AbstractOrder handler = orderContext.getInstance(type);
            handler.sendManageMessage(orderNo);
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
     * 订单确认，给用户提醒
     *
     * @param map
     * @date 2019年8月26日 下午4:41:14
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.SEND_ORDER_CONFIRM_MSG, containerFactory = "containerFactory")
    public void sendOrderConfirmMsg(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("received:send-confirm-msg:{}", map);
        try {
            String orderNo = map.get("orderNo");
            String userId = map.get("userId");
            Result<UserInfo> userResult = showUserClient.getUserById(userId);
            if (!userResult.isSuccess()) {
                return;
            }
            String wopenId = userResult.getData().getWopenId();
            System.out.println(wopenId);
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.noticeOrderConfirm(orderNo);
                templateMessage.setTouser(wopenId);
                WechatUtils.messageSend(config, templateMessage);
            } else if (StringUtils.isEmpty(userResult.getData().getPhone())) {
                // 发送短信
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
     * 订单配送，给用户提醒
     *
     * @param map
     * @date 2019年8月26日 下午4:41:49
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.SEND_ORDER_DELIVERY_MSG, containerFactory = "containerFactory")
    public void sendOrderDeliveryMsg(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("received:send-delivery-msg:{}", map);
        try {
            String orderNo = map.get("orderNo");
            String userId = map.get("userId");
            Result<UserInfo> userResult = showUserClient.getUserById(userId);
            if (userResult.isSuccess()) {
                return;
            }
            String wopenId = userResult.getData().getWopenId();
            if (!StringUtils.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.noticeOrderFreight(orderNo);
                templateMessage.setTouser(wopenId);
                WechatUtils.messageSend(config, templateMessage);
            } else if (StringUtils.isEmpty(userResult.getData().getPhone())) {
                // 发送短信
//                Result<OrderDelivery> base  = showOrderClient.getDeliveryByOrder(orderId);
//                DeliveryMsgVo messageVo = new DeliveryMsgVo(base.getData());
//                SmsUtils.sendDeliveryMsg(messageVo);
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
     * 给用户退款
     *
     * @param map
     * @param msg
     * @param channel
     * @throws IOException
     * @date 2019年9月4日 上午10:34:52
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.ORDER_REFUND, containerFactory = "containerFactory")
    public void orderRefund(Map<String, String> map, Message msg, Channel channel) throws IOException {
        try {
            String orderNo = map.get("orderNo");
            String orderType = Character.toString(orderNo.charAt(0));
            log.info("Order refund, order number: {}", orderNo);
            AbstractOrder handler = orderContext.getInstance(orderType);
            OrderBase orderBase = handler.getOrderBase(orderNo);
            String refundNo = ShowUtils.createRefundNo(orderType);
            String backStatus = OrderStatus.REFUNDING;
            Date addTime = DateUtil.date();
            SecapiPayRefundResult refundResult = getWechatRefundResult(orderBase, refundNo);
            if (ObjectUtil.isNull(refundResult)) {
                // 退款失败将状态修改到退款中状态
                handler.modifyStatusByNo(orderNo, backStatus, null);
                return;
            }
            log.info("退款调用接口结果：{}", refundResult);
            Map<String, Object> analysisResult = analysisResult(refundResult, orderBase, addTime);
            int status = (int) analysisResult.get("status");
            String message = (String) analysisResult.get("message");
            if (status == 1) {
                // 修改库存
                sender.addCurrentStock(orderBase.getOrderNo());
                sender.addTotalStock(orderBase.getOrderNo());
            } else if (status == 0) {
                // 退款失败将状态修改到退款中状态
                handler.modifyStatusByNo(orderNo, backStatus, null);
            }
            addRefundBack(orderBase, refundNo, status, message, Integer.valueOf(orderType));
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
     * 请求微信退款
     *
     * @param orderBase
     * @param refundNo
     * @return
     * @date 2019年10月18日 下午3:30:25
     */
    private SecapiPayRefundResult getWechatRefundResult(OrderBase orderBase, String refundNo) {
        SecapiPayRefundResult refundResult = null;
        try {
            int totalFee = orderBase.getPayAmount().multiply(new BigDecimal(100)).intValue();
            int refundFee = totalFee;
            refundResult = WechatUtils.refundOrder(config, orderBase.getOrderNo(), refundNo, totalFee, refundFee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return refundResult;
    }

    /**
     * 分析请求微信退款返回的结果
     *
     * @param refundResult
     * @param orderBase
     * @param addTime
     * @return
     * @date 2019年10月18日 下午3:34:33
     */
    private Map<String, Object> analysisResult(SecapiPayRefundResult refundResult, OrderBase orderBase, Date addTime) {
        Map<String, Object> resultMap = new HashMap<>(4);
        int status = 0;
        String message;
        if (refundResult.getReturn_code().equals("SUCCESS")) {
            if (refundResult.getResult_code().equals("FAIL")) {
                message = "error code: " + refundResult.getErr_code() + ". error message: "
                        + refundResult.getErr_code_des();
            } else {
                status = 1;
                message = "Refund successfully.";
                // 给用户发送信息
                noticeRefund(orderBase, addTime);
            }
        } else {
            message = "Return code: " + refundResult.getReturn_code() + ", return message: "
                    + refundResult.getErr_code_des();
        }
        log.info(message);
        resultMap.put("status", status);
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 给用户发送退款信息
     *
     * @param orderBase
     * @param addTime
     * @date 2019年10月18日 下午3:34:02
     */
    private void noticeRefund(OrderBase orderBase, Date addTime) {
        // 发送信息给用户
        Result<UserInfo> userResult = showUserClient.getUserById(orderBase.getUserId());
        if (userResult.isSuccess() && !StringUtils.isEmpty(userResult.getData().getWopenId())) {
            TemplateMessage templateMessage = WechatUtils.noticeRefund(orderBase.getOrderNo(), addTime,
                    orderBase.getPayAmount());
            templateMessage.setTouser(userResult.getData().getWopenId());
            WechatUtils.messageSend(config, templateMessage);
        } else if (userResult.isSuccess() && !StringUtils.isEmpty(userResult.getData().getPhone())) {
            // 发送短信通知
        }
    }

    /**
     * 添加退款记录
     *
     * @param orderBase
     * @param refundNo
     * @param status
     * @param message
     * @param orderType
     * @date 2019年10月18日 下午3:30:10
     */
    private void addRefundBack(OrderBase orderBase, String refundNo, int status, String message, int orderType) {
        RefundBack refundBack = new RefundBack();
        refundBack.setOrderNo(orderBase.getOrderNo());
        refundBack.setRefundNo(refundNo);
        refundBack.setTotalFee(orderBase.getPayAmount());
        refundBack.setAddTime(DateUtil.date());
        refundBack.setRefundStatus(status);
        refundBack.setRemark(message);
        refundBack.setOrderType(orderType);
        showOrderClient.addRefundBack(refundBack);
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.SCENE_ORDER_REFUND, containerFactory = "containerFactory")
    public void sceneOrderRefund(String orderNo, Message msg, Channel channel) throws IOException {
        try {
            String orderType = Character.toString(orderNo.charAt(0));
            log.info("Scene order refund, order number: {}", orderNo);
            AbstractOrder handler = orderContext.getInstance(orderType);
            OrderBase orderBase = handler.getOrderBase(orderNo);
            String orderStatus = orderBase.getOrderStatus();
            if (!(OrderStatus.REFUNDED.equals(orderStatus))) {
                return;
            }
            String refundNo = ShowUtils.createRefundNo(orderType);
            String backStatus = OrderStatus.PAID;
            SecapiPayRefundResult refundResult = getWechatRefundResult(orderBase, refundNo);
            if (refundResult == null) {
                // 退款失败将状态修改到退款中状态
                handler.modifyStatusByNo(orderNo, backStatus, null);
                return;
            }
            log.info("退款调用接口结果：{}", refundResult);
            Map<String, Object> analysisResult = analysisResult(refundResult, orderBase, DateUtil.date());
            int status = (int) analysisResult.get("status");
            String message = (String) analysisResult.get("message");
            if (status == 1) {
                // 修改库存
                sender.addSceneGoodsStock(orderBase.getOrderId());
            } else if (status == 0) {
                // 退款失败将状态修改到退款中状态
                handler.modifyStatusByNo(orderNo, backStatus, null);
            }
            addRefundBack(orderBase, refundNo, status, message, Integer.valueOf(orderType));
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
    @RabbitListener(queues = RabbitQueue.CHANGE_TABLE_PUSH, containerFactory = "containerFactory")
    public void changeTablePush(Map<String, String> map, Message msg, Channel channel) throws IOException {
        try {
            String indentId = map.get("indentId");
            String tableId = map.get("tableId");
            String tableName = map.get("tableName");
            Result<List<String>> indentResult = showOrderingClient.listIndentUser(indentId);
            if (!indentResult.isSuccess()) {
                return;
            }
            List<String> userIds = indentResult.getData();
            Result<List<String>> userResult = showUserClient.listWopenIdsByUserIds(userIds);
            if (!userResult.isSuccess()) {
                return;
            }
            List<String> openIds = userResult.getData();
            Result<List<ScanReply>> replyResult = showBaseClient.listScanRelyByCode(Contacts.TABLE_CODE_PREFIX);
            if (!replyResult.isSuccess()) {
                return;
            }
            ScanReply reply = replyResult.getData().get(0);
            String title = reply.getTitle();
            for (String openId : openIds) {
                title = String.format(title, tableName);
                String url = Contacts.IMAGE_SERVER + "/" + reply.getImagePath();
                Media media = WechatUtils.uploadMedia(config, url, MediaType.image);
                if (!media.isSuccess()) {
                    return;
                }
                Miniprogrampage miniprogrampage = new Miniprogrampage();
                miniprogrampage.setAppid(config.getXappid());
                miniprogrampage.setPagepath(String.format(config.getXtablePage(), tableId, System.currentTimeMillis()));
                miniprogrampage.setTitle(title);
                miniprogrampage.setThumb_media_id(media.getMedia_id());
                MiniprogrampageMessage message = new MiniprogrampageMessage(openId, miniprogrampage);
                WechatUtils.messageSend(config, message);
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
    @RabbitListener(queues = RabbitQueue.DISHES_ACTIVITY, containerFactory = "containerFactory")
    public void dishesActivity(Map<String, Object> map, Message msg, Channel channel) throws IOException {
        log.info("received:dishes-activity:{}", map);
        try {
            Result<UserInfo> userResult = showUserClient.getUserById(map.get("userId").toString());
            if (!userResult.isSuccess()) {
                return;
            }
            String wopenId = userResult.getData().getWopenId();
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.dishesActivity(map.get("beginTime"), map.get("endTime"));
                templateMessage.setTouser(wopenId);
                WechatUtils.messageSend(config, templateMessage);
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
    @RabbitListener(queues = RabbitQueue.MATURE, containerFactory = "containerFactory")
    public void mature(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("received:mature:{}", map);
        try {
            Result<UserInfo> userResult = showUserClient.getUserById(map.get("userId"));
            if (!userResult.isSuccess()) {
                return;
            }
            String wopenId = userResult.getData().getWopenId();
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.dishesStage(map.get("name"), map.get("stage"), null, null);
                templateMessage.setTouser(wopenId);
                WechatUtils.messageSend(config, templateMessage);
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
    @RabbitListener(queues = RabbitQueue.DISHES_STAGE, containerFactory = "containerFactory")
    public void dishesStage(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("received:dishes-stage:{}", map);
        try {
            Result<UserInfo> userResult = showUserClient.getUserById(map.get("userId"));
            if (!userResult.isSuccess()) {
                return;
            }
            String wopenId = userResult.getData().getWopenId();
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.dishesStage(map.get("name"), map.get("stage"),
                        map.get("beginDay"), map.get("endDays"));
                templateMessage.setTouser(wopenId);
                WechatUtils.messageSend(config, templateMessage);
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
    @RabbitListener(queues = RabbitQueue.EXPIRE, containerFactory = "containerFactory")
    public void expire(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("received:expiring:{}", map);
        try {
            Result<UserInfo> userResult = showUserClient.getUserById(map.get("userId"));
            if (!userResult.isSuccess()) {
                return;
            }
            String wopenId = userResult.getData().getWopenId();
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.expire(map.get("name"));
                templateMessage.setTouser(wopenId);
                WechatUtils.messageSend(config, templateMessage);
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
    @RabbitListener(queues = RabbitQueue.INDENT_REMARK, containerFactory = "containerFactory")
    public void remarkInfo(String markId, Message msg, Channel channel) throws IOException {
        log.info("received:indent-remark:{}", markId);
        try {
            BigDecimal indentTotal = showOrderingClient.selectAmount(markId).getData();
            Result<IndentInfo> indentInfo = showOrderingClient.selectById(markId);
            String userId = indentInfo.getData().getUserId();
            if (IndentStatus.BILL.code.equals(indentInfo.getData().getIndentStatus())) {
                if (StrUtil.isEmpty(userId) && StrUtil.isEmpty(indentInfo.getData().getMemberId())) {
                    return;
                }
            }
            if (StrUtil.isNotEmpty(indentInfo.getData().getMemberId())) {
                Result<MemberAccountVo> memberAccount = showMemberClient.getVoInfo(indentInfo.getData().getMemberId());
                userId = memberAccount.getData().getUserId();
            }
            Result<UserInfo> userResult = showUserClient.getUserById(userId);
            if (!userResult.isSuccess() || ObjectUtil.isEmpty(userResult.getData())) {
                return;
            }
            Result<MemberAccount> memberAccount = showMemberClient.getMemberInfoByUserId(userResult.getData().getMarkId());
            Result<Integer> integralTotal = showMemberClient.getIntegralTotalByUserId(userResult.getData().getMarkId());
            Integer amount = null;
            if (ObjectUtil.isNotEmpty(memberAccount.getData().getMemberGrade())) {
                amount = indentTotal.multiply(memberAccount.getData().getMemberGrade().getIntegralProportion()).intValue();
            }
            String wopenId = userResult.getData().getWopenId();
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.remarkInfo(userResult.getData().getNickName(), indentInfo.getData(),
                        indentTotal, integralTotal.getData() == null ? 0 : integralTotal.getData(), memberAccount.getData().getTotalAmount(), amount);
                templateMessage.setTouser(wopenId);
                TemplateMessageMiniProgram miniprogram = new TemplateMessageMiniProgram();
                miniprogram.setAppid(config.getXappid());
                miniprogram.setPagepath("/pages/order/feedback/index?indentId=" + indentInfo.getData().getMarkId());
                templateMessage.setMiniprogram(miniprogram);
                WechatUtils.messageSend(config, templateMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }

    /**
     * 通知用户异常
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.CONTACT_USER, containerFactory = "containerFactory")
    public void contactUser(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("received:contact-user:{}", map);
        try {
            Result<OrderInfo> order = showOrderClient.getOrderById(map.get("orderId"));
            Result<OrderDelivery> delivery = showOrderClient.getDeliveryByOrder(map.get("orderId"));
            if (ObjectUtil.isEmpty(order) || StrUtil.isEmpty(order.getData().getUserId())) {
                return;
            }
            Result<UserInfo> userResult = showUserClient.getUserById(order.getData().getUserId());
            if (!userResult.isSuccess()) {
                return;
            }
            String wopenId = userResult.getData().getWopenId();
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.contactUser(ContactUser.builder().orderNo(order.getData().getOrderNo())
                        .name(delivery.getData().getContact()).phone(delivery.getData().getPhone()).content(map.get("content"))
                        .reason(map.get("reason")).build());
                templateMessage.setTouser(wopenId);
                WechatUtils.messageSend(config, templateMessage);
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
     * 通知用户优惠券过期提醒
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.TICKET_EXPIRE, containerFactory = "containerFactory")
    public void ticketExpire(Map<String, String> map, Message msg, Channel channel) throws IOException, ParseException {
        log.info("received:ticket_expire:{}", map);
        try {
            Result<UserInfo> userResult = showUserClient.getUserById(map.get("userId"));
            if (!userResult.isSuccess()) {
                return;
            }
            String wopenId = userResult.getData().getWopenId();
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.ticketExpire(map.get("date"), map.get("total"), map.get("num"), map.get("name"));
                templateMessage.setTouser(wopenId);
                TemplateMessageMiniProgram miniprogram = new TemplateMessageMiniProgram();
                miniprogram.setAppid(config.getXappid());
                miniprogram.setPagepath("/pages/ticket/list/index?userId=" + map.get("userId"));
                templateMessage.setMiniprogram(miniprogram);
                WechatUtils.messageSend(config, templateMessage);
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
     * 会员生日推送券
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.BIRTHDAY_TICKET, containerFactory = "containerFactory")
    public void birthdayTicket(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("received:birthday-ticket:{}", map);
        try {
            Result<UserInfo> userResult = showUserClient.getUserById(map.get("userId"));
            if (!userResult.isSuccess()) {
                return;
            }
            String wopenId = userResult.getData().getWopenId();
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.birthdayTicket(userResult.getData().getNickName(), map.get("accountNo"));
                templateMessage.setTouser(wopenId);
                WechatUtils.messageSend(config, templateMessage);
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
    @RabbitListener(queues = RabbitQueue.SEND_TEAM_STATUS, containerFactory = "containerFactory")
    public void sendTeamStatus(Map<String, Object> map, Message msg, Channel channel) throws IOException {
        log.info("发送队伍状态信息：", map);
        try {
            String matchId = (String) map.get("matchId");
            String teamId = (String) map.get("teamId");
            Integer teamStatus = (Integer) map.get("teamStatus");
            Boolean isLast = (Boolean) map.get("isLast");
            Result<List<String>> result = showMemberClient.listVoteUserByMatch(matchId, teamId);
            Result<MatchTeam> teamResult = showMemberClient.getMatchTeamInfo(teamId);
            List<String> userList = result.getData();
            for (String userId : userList) {
                Result<MemberAccount> memberAccountResult = showMemberClient.getMemberInfoByUser(userId);
                Result<UserInfo> userResult = showUserClient.getUserById(userId);
                String wopenId = userResult.getData().getWopenId();
                if (!userResult.isSuccess() || StrUtil.isEmpty(wopenId)) {
                    continue;
                }
                TemplateMessage templateMessage;
                if (Boolean.TRUE.equals(isLast)) {
                    templateMessage = WechatUtils.matchWinSend(teamResult.getData().getTeamName(), teamStatus, memberAccountResult.getData().getPhone());
                } else {
                    templateMessage = WechatUtils.matchStatusSend(teamResult.getData().getTeamName(), teamStatus, memberAccountResult.getData().getPhone());
                }
                templateMessage.setTouser(wopenId);
                TemplateMessageMiniProgram miniprogram = new TemplateMessageMiniProgram();
                miniprogram.setAppid(config.getXappid());
                miniprogram.setPagepath(config.getXmatchPage());
                templateMessage.setMiniprogram(miniprogram);
                WechatUtils.messageSend(config, templateMessage);
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
     * 通知用户积分过期提醒
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.INTEGRAL_EXPIRE, containerFactory = "containerFactory")
    public void integralExpire(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("received:integral-expire:{}", map);
        try {
            Result<UserInfo> userResult = showUserClient.getUserById(map.get("userId"));
            if (!userResult.isSuccess()) {
                return;
            }
            String wopenId = userResult.getData().getWopenId();
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.integralExpire(map.get("integral"), map.get("days"),
                        map.get("integralTotal"));
                templateMessage.setTouser(wopenId);
                WechatUtils.messageSend(config, templateMessage);
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
     * 通知用户积分过期提醒
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.LOTTERY_RESULT, containerFactory = "containerFactory")
    public void lotteryResult(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("received:lottery-result:{}", map);
        try {
            Result<UserInfo> userResult = showUserClient.getUserById(map.get("userId"));
            if (!userResult.isSuccess()) {
                return;
            }
            PrizeInfo prizeInfo = showMemberClient.queryPrizeById(map.get("prizeId")).getData();
            String wopenId = userResult.getData().getWopenId();
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.lotteryResult(prizeInfo.getPrizeName());
                templateMessage.setTouser(wopenId);
                TemplateMessageMiniProgram miniprogram = new TemplateMessageMiniProgram();
                miniprogram.setAppid(config.getXappid());
                miniprogram.setPagepath("/pages/ticket/list/index?userId=" + map.get("userId"));
                templateMessage.setMiniprogram(miniprogram);
                WechatUtils.messageSend(config, templateMessage);
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
     * 购买成功通知
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.MEMBER_COMBO, containerFactory = "containerFactory")
    public void memberCombo(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("received:member-combo:{}", map);
        try {
            String userId = map.get("userId");
            Result<UserInfo> userResult = showUserClient.getUserById(userId);
            if (!userResult.isSuccess()) {
                return;
            }
            ComboBuy combo = showMemberClient.queryByComboId(map.get("markId")).getData();
            String wopenId = userResult.getData().getWopenId();
            if (!StrUtil.isEmpty(wopenId)) {
                TemplateMessage templateMessage = WechatUtils.comboBuy(combo.getName(), map.get("quantity"),
                        combo.getComboEnd(), combo.getEffectiveDays());
                templateMessage.setTouser(wopenId);
                TemplateMessageMiniProgram miniprogram = new TemplateMessageMiniProgram();
                miniprogram.setAppid(config.getXappid());
                miniprogram.setPagepath("/pages/ticket/list/index?userId=" + map.get("userId"));
                templateMessage.setMiniprogram(miniprogram);
                WechatUtils.messageSend(config, templateMessage);
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
     * 预约通知
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.RESERVATION_NOTIFY, containerFactory = "containerFactory")
    public void reservation(String comboId, Message msg, Channel channel) throws IOException {
        log.info("received:reservation-notify:{}", comboId);
        try {
            ComboBuy combo = showMemberClient.queryByComboId(comboId).getData();
            BigDecimal discount = combo.getMemberPrice().divide(combo.getPrice(), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(BigDecimal.valueOf(10));
            Result<List<UserInfo>> userResult = showUserClient.selectFocusUser();
            if (!userResult.isSuccess()) {
                return;
            }
            for (UserInfo userInfo : userResult.getData()) {
                String wopenId = userInfo.getWopenId();
                if (!StrUtil.isEmpty(wopenId)) {
                    TemplateMessage templateMessage = WechatUtils.reservation(combo.getName(),
                            combo.getStartTime(), combo.getEndTime(), discount);
                    templateMessage.setTouser(wopenId);
                    TemplateMessageMiniProgram miniprogram = new TemplateMessageMiniProgram();
                    miniprogram.setAppid(config.getXappid());
                    WechatUtils.messageSend(config, templateMessage);
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
}
