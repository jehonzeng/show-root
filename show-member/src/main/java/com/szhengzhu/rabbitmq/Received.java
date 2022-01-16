package com.szhengzhu.rabbitmq;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.rabbitmq.client.Channel;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.member.param.SignInfoParam;
import com.szhengzhu.bean.ordering.IndentInfo;
import com.szhengzhu.bean.ordering.param.GiveParam;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.code.*;
import com.szhengzhu.core.*;
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
import java.util.*;

@Slf4j
@Component
public class Received {

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Resource
    private MemberGradeMapper memberGradeMapper;

    @Resource
    private MemberAccountMapper memberAccountMapper;

    @Resource
    private MemberDetailMapper memberDetailMapper;

    @Resource
    private PendDishesMapper pendDishesMapper;

    @Resource
    private ReceiveRecordMapper receiveRecordMapper;

    @Resource
    private IntegralAccountMapper integralAccountMapper;

    @Resource
    private IntegralDetailMapper integralDetailMapper;

    @Resource
    private MemberActivityMapper memberActivityMapper;

    @Resource
    private ShowOrderingClient showOrderingClient;

    @Resource
    private GradeTicketMapper gradeTicketMapper;

    @Resource
    private GradeRecordMapper gradeRecordMapper;

    @Resource
    private PrizeReceiveMapper prizeReceiveMapper;

    @Resource
    private PrizeInfoMapper prizeInfoMapper;

    @Resource
    private SignMemberMapper signMemberMapper;

    @Resource
    private SignRuleMapper signRuleMapper;

    @Resource
    private SignDetailMapper signDetailMapper;

    @Resource
    private TicketExchangeMapper ticketExchangeMapper;

    @Resource
    private IntegralExchangeMapper integralExchangeMapper;

    @Resource
    private MemberLotteryMapper memberLotteryMapper;

    @Resource
    private MatchChanceMapper matchChanceMapper;

    @Resource
    private MatchInfoMapper matchInfoMapper;

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.INDENT_MEMBER_REFUND, containerFactory = "containerFactory")
    public void indentMemberRefund(Map<String, String> map, Message msg, Channel channel) throws IOException {
        try {
            String detailId = map.get("detailId");
//        BigDecimal amount = new BigDecimal(map.get("amount"));
            String userId = map.get("userId");
            String indentId = map.get("indentId");
            MemberDetail detail = memberDetailMapper.selectByPrimaryKey(detailId);
            MemberAccount account = memberAccountMapper.selectByUserId(userId);
            if (ObjectUtil.isNull(detail) || !indentId.equals(detail.getIndentId()) || ObjectUtil.isNull(account)) {
                return;
            }
            BigDecimal amount = detail.getAmount().negate();
            account.setTotalAmount(account.getTotalAmount().add(amount));
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            detail.setMarkId(snowflake.nextIdStr());
            detail.setAmount(amount);
            detail.setCreateTime(DateUtil.date());
            detail.setType(MemberCode.CONSUME_REFUND.code);
            detail.setStatus(1);
            // 充值后余额（注册或者充值）
            detail.setSurplusAmount(account.getTotalAmount());
            memberAccountMapper.updateByPrimaryKeySelective(account);
            memberDetailMapper.insertSelective(detail);
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
    @RabbitListener(queues = RabbitQueue.ACTIVITY, containerFactory = "containerFactory")
    public void activity(String indentId, Message msg, Channel channel) throws IOException {
        try {
            MemberActivity memberActivity = memberActivityMapper.queryById(MemberActivityCode.RECEIVE_DISH.code);
            Result<IndentInfo> indent = showOrderingClient.selectById(indentId);
            if (memberActivity.getStatus() == 0 && !indent.getData().getIndentStatus().equals(IndentStatus.BILL.code)) {
                return;
            }
            MemberAccount member = memberInfo(indent.getData());
            if (ObjectUtil.isEmpty(member) && indent.getData().getTotal().intValue() < 50) {
                return;
            }
            //判断用户今天是否领取了机会
            List<PendDishes> list = pendDishesMapper.queryAll(PendDishes.builder().userId(member.getUserId()).createTime(DateUtil.date()).build());
            if (ObjectUtil.isNotEmpty(list)) {
                return;
            }
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            //给用户添加待领取信息
            PendDishes pendDishes = PendDishes.builder().markId(snowflake.nextIdStr()).activityId(MemberActivityCode.RECEIVE_DISH.code)
                    .indentId(indentId).userId(member.getUserId()).status(1).createTime(DateUtil.date()).build();
            pendDishesMapper.insert(pendDishes);
            //添加领取菜品的动态记录
            receiveRecordMapper.insert(ReceiveRecord.builder().markId(snowflake.nextIdStr()).userId(member.getUserId())
                    .description(ReceiveInfo.receive()).createTime(DateUtil.date()).build());
            log.info("key：pend_" + pendDishes.getMarkId() + ",录入时间：" + DateUtil.format(DateUtil.date(), "yyyy-MM-dd HH:mm:ss"));
            //推送待领取信息给用户(推送活动)
            sender.dishesActivity(memberActivity.getBeginTime(), memberActivity.getEndTime(), member.getUserId());
            //7 * 24 * 60 * 60
            redis.set("pend_" + pendDishes.getMarkId(), pendDishes, 120);
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
    @RabbitListener(queues = RabbitQueue.GIFT_INTEGRAL, containerFactory = "containerFactory")
    public void giftIntegral(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("gift_integral:{}", map);
        try {
            String userId = map.get("userId");
            //评价赠送的积分
            Integer num = Integer.valueOf(map.get("num"));
            memberIntegral(userId, num, IntegralCode.JUDGE_GIVE);
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
    @RabbitListener(queues = RabbitQueue.INTEGRAL_ACCOUNT_CHECK, containerFactory = "containerFactory")
    public void integralAccountCheck(Map<String, Object> map, Message msg, Channel channel) throws IOException {
        log.info("checkAccount:{}", map);
        try {
            String userId = (String) map.get("userId");
            Boolean flag = (Boolean) map.get("flag");
            if (StrUtil.isEmpty(userId)) {
                return;
            }
            int total = integralDetailMapper.selectTotalByUser(userId);
            IntegralAccount account = integralAccountMapper.selectByUser(userId);
            if (ObjectUtil.isNull(account)) {
                Snowflake snowflake = IdUtil.getSnowflake(1, 1);
                String markId = snowflake.nextIdStr();
                account = IntegralAccount.builder().markId(markId).accountNo(new StringBuffer(markId.substring(0, 12)).reverse().toString())
                        .createTime(DateUtil.date()).userId(userId).build();
                account.setTotalIntegral(total);
                integralAccountMapper.insertSelective(account);
            } else {
                account.setTotalIntegral(total);
                account.setModifyTime(flag ? DateUtil.date() : account.getModifyTime());
                integralAccountMapper.updateByPrimaryKey(account);
            }
            integralDetailMapper.updateAccountByUser(account.getMarkId(), userId);
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
    @RabbitListener(queues = RabbitQueue.MEMBER_CONSUME_REFUND, containerFactory = "containerFactory")
    public void consumeAmountRefund(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("member_consume_refund:{}", map);
        try {
            MemberAccount member = memberAccountMapper.selectByUserId(map.get("userId"));
            if (ObjectUtil.isEmpty(member) || StrUtil.isEmpty(member.getGradeId())) {
                return;
            }
            consumeOrRefund(member, null, new BigDecimal(map.get("amount")), IntegralCode.EXCHANGE);
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
    @RabbitListener(queues = RabbitQueue.MEMBER_INDENT_CONSUME_REFUND, containerFactory = "containerFactory")
    public void consumeIndentAmountRefund(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("member_indent_consume_refund:{}", map);
        try {
            String indentId = map.get("indentId");
            IndentInfo indent = showOrderingClient.selectById(indentId).getData();
            MemberAccount member = memberInfo(indent);
            if (ObjectUtil.isEmpty(member) || StrUtil.isEmpty(member.getGradeId())) {
                return;
            }
            GradeRecord gradeRecord = gradeRecordMapper.selectIndentId(indentId);
            if (ObjectUtil.isEmpty(gradeRecord) && gradeRecord.getConsumeAmount().compareTo(BigDecimal.ZERO) < 0) {
                return;
            }
            BigDecimal amount = showOrderingClient.selectAmount(indentId).getData();
            consumeOrRefund(member, indentId, amount.negate(), IntegralCode.EXCHANGE);
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
    @RabbitListener(queues = RabbitQueue.MEMBER_CONSUME, containerFactory = "containerFactory")
    public void memberConsume(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("member_consume:{}", map);
        try {
            MemberAccount member = memberAccountMapper.selectByUserId(map.get("userId"));
            if (ObjectUtil.isEmpty(member) || StrUtil.isEmpty(member.getGradeId())) {
                return;
            }
            consumeOrRefund(member, null, new BigDecimal(map.get("amount")), IntegralCode.INDENT_GIVE);
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
    @RabbitListener(queues = RabbitQueue.MEMBER_INDENT_CONSUME, containerFactory = "containerFactory")
    public void memberIndentConsume(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("member_indent_consume:{}", map);
        try {
            String indentId = map.get("indentId");
            MemberAccount member = memberInfo(showOrderingClient.selectById(indentId).getData());
            if (ObjectUtil.isEmpty(member) || StrUtil.isEmpty(member.getGradeId())) {
                return;
            }
            GradeRecord gradeRecord = gradeRecordMapper.selectIndentId(indentId);
            if (ObjectUtil.isNotEmpty(gradeRecord) && gradeRecord.getConsumeAmount().compareTo(BigDecimal.ZERO) > 0) {
                return;
            }
            BigDecimal amount = showOrderingClient.selectAmount(indentId).getData();
            consumeOrRefund(member, indentId, amount, IntegralCode.INDENT_GIVE);
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
     * 会员消费或退款
     */
    public void consumeOrRefund(MemberAccount member, String indentId, BigDecimal amount, IntegralCode integralCode) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        MemberGrade grade = memberGradeMapper.queryById(member.getGradeId());
        //会员消费金额（成长值）
        gradeRecordMapper.add(GradeRecord.builder().markId(snowflake.nextIdStr()).memberId(member.getMarkId()).consumeAmount(amount).
                remark(integralCode.code != -1 ? Contacts.MEMBER_CONSUME : Contacts.MEMBER_CONSUME_REFUND).indentId(indentId).createTime(DateUtil.date()).build());
        int consume = amount.multiply(grade.getIntegralProportion()).intValue();
        //赠送会员消费积分
        memberIntegral(member.getUserId(), consume, integralCode.code != -1 ? IntegralCode.INDENT_GIVE : IntegralCode.EXCHANGE);
        //查询会员消费金额 会员等级
        MemberAccount memberAccount = memberAccountMapper.selectByPrimaryKey(member.getMarkId());
        BigDecimal consumeAmount = gradeRecordMapper.consumeTotal(memberAccount.getMarkId());
        MemberGrade memberGrade = memberGradeMapper.selectByGradeId(ObjectUtil.isEmpty(consumeAmount) ? 0 : consumeAmount.intValue());
        if (!memberGrade.getMarkId().equals(grade.getMarkId())) {
            updateMemberGrade(grade, memberGrade, memberAccount.getUserId(), memberAccount.getMarkId(), integralCode.code != -1 ? IntegralCode.MEMBER_GRADE : IntegralCode.EXCHANGE);
        }
    }

    /**
     * 修改等级
     *
     * @param newGrade     会员消费或退款前等级
     * @param oldGrade     会员消费或退款后等级
     * @param userId
     * @param memberId
     * @param integralCode -1 兑换/撤回赠送， 0 其他赠送， 1 评价得积分， 2 撤回兑换菜品
     */
    private void updateMemberGrade(MemberGrade oldGrade, MemberGrade newGrade, String userId, String memberId, IntegralCode integralCode) {
        if (ObjectUtil.isNull(newGrade)) {
            return;
        }
        //修改会员等级
        MemberGrade grade = integralCode.code != -1 ? newGrade : oldGrade;
        memberAccountMapper.updateByPrimaryKeySelective(MemberAccount.builder().markId(memberId).
                gradeId(newGrade.getMarkId()).modifyTime(DateUtil.date()).build());
        if (ObjectUtil.isNull(newGrade.getGiveType())) {
            return;
        }
        //会员等级升级赠送
        switch (grade.getGiveType()) {
            case 1:
                memberIntegral(userId, grade.getGiveIntegral(), integralCode);
                break;
            case 2:
                memberTicket(grade.getMarkId(), userId, integralCode);
                break;
            case 3:
                memberIntegral(userId, grade.getGiveIntegral(), integralCode);
                memberTicket(grade.getMarkId(), userId, integralCode);
                break;
            default:
                break;
        }
    }

    private MemberAccount memberInfo(IndentInfo info) {
        //查询用户是否是会员
        if (StrUtil.isNotEmpty(info.getUserId())) {
            return memberAccountMapper.selectByUserId(info.getUserId());
        } else if (StrUtil.isNotEmpty(info.getMemberId())) {
            return memberAccountMapper.selectByPrimaryKey(info.getMemberId());
        }
        return null;
    }

    /**
     * 操作积分明细
     *
     * @param userId
     * @param amount
     * @param integralCode -1 兑换/撤回赠送， 0 其他赠送， 1 评价得积分/赠送， 2 撤回兑换菜品
     */
    private void memberIntegral(String userId, Integer amount, IntegralCode integralCode) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        IntegralDetail detail = IntegralDetail.builder().markId(snowflake.nextIdStr()).userId(userId).
                integralLimit(amount).createTime(DateUtil.date()).build();
        detail.setIntegralType(integralCode);
        integralDetailMapper.insertSelective(detail);
        sender.checkAccount(userId, true);
    }

    /**
     * 会员等级赠送
     *
     * @param gradeId
     * @param userId
     * @param integralCode -1 兑换/撤回赠送， 0 其他赠送， 1 评价得积分/赠送， 2 撤回兑换菜品
     */
    private void memberTicket(String gradeId, String userId, IntegralCode integralCode) {
        List<GradeTicket> tickets = gradeTicketMapper.queryById(gradeId);
        log.info(String.valueOf(tickets.size()));
        for (GradeTicket ticket : tickets) {
            Map<String, String> map = new HashMap<>();
            map.put("userId", userId);
            map.put("templateId", ticket.getTemplateId());
            map.put("quantity", ticket.getQuantity().toString());
            if (integralCode.code == -1) {
                //撤回会员等级升级礼包
                sender.memberReceiveTicketRefund(map);
            } else {
                //送会员等级升级礼包给用户
                sender.memberReceiveTicket(map);
            }
        }
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.MEMBER_GRADE, containerFactory = "containerFactory")
    public void memberGrade(String memberId, Message msg, Channel channel) throws IOException {
        log.info("member_grade:{}", memberId);
        try {
            MemberAccount account = memberAccountMapper.selectByPrimaryKey(memberId);
            BigDecimal consumeAmount = gradeRecordMapper.consumeTotal(account.getMarkId());
            MemberGrade memberGrade = memberGradeMapper.selectByGradeId(ObjectUtil.isEmpty(consumeAmount) ? 0 : consumeAmount.intValue());
            updateMemberGrade(null, memberGrade, account.getUserId(), account.getMarkId(), IntegralCode.MEMBER_GRADE);
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
    @RabbitListener(queues = RabbitQueue.MEMBER_LOTTERY, containerFactory = "containerFactory")
    public void memberLottery(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("member_lottery:{}", map);
        try {
            String prizeId = map.get("prizeId");
            String userId = map.get("userId");
            PrizeInfo prizeInfo = prizeInfoMapper.queryById(prizeId);
            MemberLottery memberLottery = memberLotteryMapper.queryById(prizeInfo.getLotteryId());
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            //添加积分消费记录
            if (ObjectUtil.isNotEmpty(memberLottery.getConsumeIntegral())) {
                memberIntegral(userId, memberLottery.getConsumeIntegral(), IntegralCode.LOTTERY_CONSUME);
            }
            //更新奖品数量
            prizeInfoMapper.modify(PrizeInfo.builder().markId(prizeId).quantity(prizeInfo.getQuantity() - 1).modifyTime(DateUtil.date()).build());
            //添加领取记录
            prizeReceiveMapper.add(PrizeReceive.builder().markId(snowflake.nextIdStr()).userId(userId).prizeId(prizeId).
                    createTime(DateUtil.date()).build());
            if (prizeInfo.getPrizeType() == 1) {
                //送积分
                memberIntegral(userId, prizeInfo.getIntegral(), IntegralCode.LOTTERY_GIVE);
            } else if (prizeInfo.getPrizeType() == 2) {
                //送优惠券
                showOrderingClient.giveUserTicket(GiveParam.builder().templateId(prizeInfo.getTemplateId()).quantity(1).userId(userId).build());
            }
            if (memberLottery.getType().equals(LotteryTypeCode.FRESH_LOTTERY.code) && prizeInfo.getPrizeType() == 2) {
                sender.lotteryResult(prizeId, userId);
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
    @RabbitListener(queues = RabbitQueue.MEMBER_SIGN, containerFactory = "containerFactory")
    public void memberSign(Map<String, String> map, Message msg, Channel channel) throws IOException {
        log.info("member_sign:{}", map);
        try {
            String userId = map.get("userId");
            DateTime date = DateUtil.parse(map.get("date"));
            Integer year = DateUtil.year(date);
            Integer month = DateUtil.month(date);
            Integer day = DateUtil.dayOfMonth(date);
            Integer continueSign = 1;
            StringBuffer sign = new StringBuffer();
            //查询用户这个月是否添加了签到记录
            SignInfoParam signMember = signMemberMapper.queryByUserId(userId, year, month + 1);
            if (ObjectUtil.isEmpty(signMember)) {
                Snowflake snowflake = IdUtil.getSnowflake(1, 1);
                String markId = snowflake.nextIdStr();
                for (int i = 0; i < getDayOfMonth(); i++) {
                    sign.append(Contacts.NO_SIGN_IN);
                }
                sign.replace(day - 1, day, Contacts.SIGN_IN);
                SignInfoParam lastSignMember = signMemberMapper.queryByUserId(userId, year, month);
                if (ObjectUtil.isNotEmpty(lastSignMember)) {
                    long betweenDay = DateUtil.betweenDay(lastSignMember.getSignTime(), date, true);
                    if (betweenDay == 1) {
                        continueSign += ObjectUtil.isEmpty(lastSignMember.getContinueSign()) ? 0 : lastSignMember.getContinueSign();
                    }
                }
                signMemberMapper.add(SignMember.builder().markId(markId).userId(userId).sign(sign.toString()).year(year).
                        month(month + 1).signTime(date).continueSign(continueSign).build());
                give(markId, userId, date);
            } else {
                long betweenDay = DateUtil.betweenDay(signMember.getSignTime(), date, true);
                if (betweenDay == 1) {
                    continueSign += ObjectUtil.isEmpty(signMember.getContinueSign()) ? 0 : signMember.getContinueSign();
                }
                sign = new StringBuffer(signMember.getSign());
                sign.replace(day - 1, day, Contacts.SIGN_IN);
                signMemberMapper.modify(SignMember.builder().markId(signMember.getMarkId()).sign(sign.toString()).
                        signTime(date).continueSign(continueSign).build());
                give(signMember.getMarkId(), userId, date);
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
     * 会员签到领取礼包
     *
     * @param signId
     * @param userId
     */
    public void give(String signId, String userId, DateTime date) {
        SignInfoParam signMember = signMemberMapper.queryById(signId);
        List<SignRule> signRuleList = signRuleMapper.queryAll(SignRule.builder().sort("desc").status(true).build());
        for (SignRule rule : signRuleList) {
            if (signMember.getContinueSign() % rule.getDays() == 0 && rule.getDays() != 1) {
                signDetail(signId, userId, rule, date);
            } else if (rule.getDays() == 1) {
                signDetail(signId, userId, rule, date);
            }
        }
    }

    /**
     * 赠送积分或优惠券
     *
     * @param signId
     * @param userId
     * @param rule
     * @param date
     */
    public void signDetail(String signId, String userId, SignRule rule, DateTime date) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        if (rule.getGiveType() == 1) {
            memberIntegral(userId, rule.getGiveIntegral(), IntegralCode.MEMBER_SIGN);
        } else if (rule.getGiveType() == 2) {
            showOrderingClient.giveUserTicket(GiveParam.builder().templateId(rule.getTemplateId()).quantity(1).userId(userId).build());
        }
        if (ObjectUtil.isEmpty(signDetailMapper.queryAll(SignDetail.builder().signId(signId).createTime(date).build()))) {
            signDetailMapper.add(SignDetail.builder().markId(snowflake.nextIdStr()).signId(signId).ruleId(rule.getMarkId()).createTime(date).build());
        }
    }

    public static int getDayOfMonth() {
        //获取这个月有多少天
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        return aCalendar.getActualMaximum(Calendar.DATE);
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.INTEGRAL_EXCHANGE, containerFactory = "containerFactory")
    public void integralExchange(Map<String, String> map, Message msg, Channel channel) throws Exception {
        log.info("integral_exchange:{}", map);
        try {
            String exchangeId = map.get("exchangeId");
            String userId = map.get("userId");
            IntegralExchange exchange = integralExchangeMapper.queryById(exchangeId);
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            //兑换成功 减库存
            integralExchangeMapper.modify(IntegralExchange.builder().markId(exchangeId).modifyTime(DateUtil.date()).
                    exchangeQuantity(exchange.getExchangeQuantity() - 1).build());
            memberIntegral(userId, exchange.getConsumeIntegral(), IntegralCode.EXCHANGE);
            //给用户发送券
            showOrderingClient.giveUserTicket(GiveParam.builder().templateId(exchange.getTemplateId()).quantity(1).userId(userId).build());
            //添加用户兑换券的记录
            ticketExchangeMapper.add(TicketExchange.builder().markId(snowflake.nextIdStr()).userId(userId).exchangeId(exchangeId).createTime(DateUtil.date()).build());
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
    @RabbitListener(queues = RabbitQueue.MATCH_CHANCE_COUNT, containerFactory = "containerFactory")
    public void receiveMatchChance(Map<String, Object> map, Message msg, Channel channel) throws IOException {
        try {
            String userId = (String) map.get("userId");
            Integer quantity = (Integer) map.get("quantity");
            int count = matchInfoMapper.selectCount();
            if (count < 1) {
                return;
            }
            if (StrUtil.isNotBlank(userId) && ObjectUtil.isNotNull(quantity) && quantity > 0) {
                MatchChance matchChance = matchChanceMapper.selectByPrimaryKey(userId);
                if (ObjectUtil.isNotNull(matchChance)) {
                    matchChance.setTotalCount(matchChance.getTotalCount() + quantity);
                    matchChance.setModifyTime(DateUtil.date());
                    matchChanceMapper.updateByPrimaryKeySelective(matchChance);
                    return;
                }
                matchChance = new MatchChance();
                matchChance.setUserId(userId);
                matchChance.setTotalCount(quantity);
                matchChance.setUsedCount(0);
                matchChance.setCreateTime(DateUtil.date());
                matchChanceMapper.insertSelective(matchChance);
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
