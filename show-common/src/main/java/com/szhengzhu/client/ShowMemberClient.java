package com.szhengzhu.client;

import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.member.param.*;
import com.szhengzhu.bean.member.vo.*;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ExceptionAdvice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@FeignClient(name = "show-member", fallback = ExceptionAdvice.class)
public interface ShowMemberClient {

    /**
     * 活动
     */
    @PostMapping(value = "/activity/add", consumes = Contacts.CONSUMES)
    Result addActivity(@RequestBody Activity activity);

    @PatchMapping(value = "/activity/modify", consumes = Contacts.CONSUMES)
    Result modifyActivity(@RequestBody Activity activity);

    @GetMapping(value = "/activity/info/code")
    Result<Activity> getActivityWellInfoByCode(@RequestParam("code") String code);

    @PostMapping(value = "/activity/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Activity>> pageActivity(PageParam<Activity> pageParam);

    @DeleteMapping(value = "/activity/del/{markId}")
    Result deleteByActivityId(@PathVariable("markId") @NotBlank String markId);

    /**
     * 菜品信息
     */
    @PostMapping(value = "/dishes/info", consumes = Contacts.CONSUMES)
    Result<PageGrid<DishesInfo>> queryDishes(@RequestBody PageParam<DishesInfo> param);

    @PostMapping(value = "/dishes/info/add", consumes = Contacts.CONSUMES)
    Result addDishes(@RequestBody @Validated DishesInfo dishesInfo);

    @PatchMapping(value = "/dishes/info/modify", consumes = Contacts.CONSUMES)
    Result modifyDishes(@RequestBody DishesInfo dishesInfo);

    @PatchMapping(value = "/dishes/info/{markId}")
    Result modifyDishesStatus(@PathVariable("markId") @NotBlank String markId);

    @PostMapping(value = "/dishes/image", consumes = Contacts.CONSUMES)
    Result<List<DishesImage>> queryImage(@RequestBody DishesImageVo image);

    @PostMapping(value = "/dishes/image/add", consumes = Contacts.CONSUMES)
    Result addImage(@RequestBody @Validated DishesImageVo image);

    @PatchMapping(value = "/dishes/image/modify", consumes = Contacts.CONSUMES)
    Result modifyImage(@RequestBody DishesImageVo image);

    @PostMapping(value = "/dishes/stage", consumes = Contacts.CONSUMES)
    Result<List<DishesStage>> queryStage(@RequestBody DishesStageVo stage);

    @PostMapping(value = "/dishes/stage/add", consumes = Contacts.CONSUMES)
    Result addStage(@RequestBody @Validated DishesStageVo stage);

    @PatchMapping(value = "/dishes/stage/modify", consumes = Contacts.CONSUMES)
    Result modifyStage(@RequestBody DishesStageVo stage);

    /**
     * 积分
     */
    @PostMapping(value = "/integral/consume")
    Result integralConsume(@RequestBody @Validated IntegralDetail detail);

    @PostMapping(value = "/integral/record", consumes = Contacts.CONSUMES)
    Result<PageGrid<IntegralDetail>> integralRecord(@RequestBody PageParam<MemberRecord> param);

    @GetMapping(value = "/integral/account/sum/{userId}")
    Result<Map<String, Integer>> getIntegralSumByUser(@PathVariable("userId") @NotBlank String userId);

    @GetMapping(value = "/integral/account/total")
    Result<Integer> getIntegralTotalByUserId(@RequestParam("userId") String userId);

    //    @PostMapping(value = "/integral/account/page", consumes = Contacts.CONSUMES)
//    Result<PageGrid<IntegralVo>> pageIntegralAccount(
//            @RequestBody PageParam<Map<String, String>> integralPage);

    @PostMapping(value = "/integral/detail/add", consumes = Contacts.CONSUMES)
    Result addIntegral(@RequestBody IntegralDetail integral);

    @PostMapping(value = "/integral/detail/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<IntegralDetail>> pageIntegralDetail(@RequestBody PageParam<IntegralDetail> integralPage);

    @PostMapping(value = "/integral/detail/user/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<IntegralDetail>> pageUserIntegral(@RequestBody PageParam<String> param);

    @GetMapping(value = "/integral/exchange/{markId}")
    Result<IntegralExchange> queryByIntegralExchangeId(@PathVariable("markId") @NotBlank String markId);

    @PostMapping(value = "/integral/exchange/list")
    Result<List<IntegralExchange>> queryIntegralExchangeList(@RequestBody IntegralExchange integralExchange);

    @PostMapping(value = "/integral/exchange/add")
    Result addIntegralExchange(@RequestBody IntegralExchange integralExchange);

    @PatchMapping(value = "/integral/exchange/modify")
    Result modifyIntegralExchange(@RequestBody IntegralExchange integralExchange);

    @PostMapping(value = "/integral/exchange/ticket/page")
    Result<PageGrid<TicketExchange>> queryTicketExchangePage(@RequestBody(required = false) PageParam<TicketExchange> param);

    @PostMapping(value = "/integral/exchange/ticket/list")
    Result<List<TicketExchange>> queryTicketExchangeList(@RequestBody(required = false) TicketExchange ticketExchange);

    @GetMapping(value = "/integral/expire")
    Result<IntegralExpire> queryByIntegralExpireId();

    @PostMapping(value = "/integral/expire/add")
    Result addIntegralExpire(@RequestBody IntegralExpire integralExpire);

    @GetMapping(value = "/integral/expire/show")
    Result<List<Map<String, String>>> selectPushUser(@RequestParam(value = "userId", required = false) String userId);

    /**
     * 竞赛活动
     */
    @PostMapping(value = "/match/page")
    Result<PageGrid<MatchInfo>> pageMatch(@RequestBody PageParam<MatchInfo> param);

    @PostMapping(value = "/match/add")
    Result addMatch(@RequestBody MatchInfo matchInfo);

    @PatchMapping(value = "/match/modify")
    Result modifyMatch(@RequestBody @Validated MatchInfo matchInfo);

    @GetMapping(value = "/match/list")
    Result<List<Map<String, Object>>> listMatch();

    @GetMapping(value = "/match/create/mark")
    Result<Map<String, Object>> createMatchExchangeMark(@RequestParam("matchId") String matchId, @RequestParam("userId") @NotBlank String userId);

    @GetMapping(value = "/match/scancode")
    Result scanExchangeCode(@RequestParam(value = "mark") String mark);

    @PostMapping(value = "/match/item/{matchId}")
    Result addMatchItem(@PathVariable("matchId") String matchId, @RequestBody List<String> teamIds);

    @GetMapping(value = "/match/pay/add")
    Result<MatchPay> addMatchPay(@RequestParam("matchId") String matchId, @RequestParam("quantity") Integer quantity,
                                 @RequestParam("userId") String userId, @RequestParam("openId") String openId);

    @PostMapping(value = "/match/exchange")
    Result exchange(@RequestBody @Validated ExchangeParam exchangeParam);

    @PostMapping(value = "/match/prize/page")
    Result<PageGrid<MatchPrize>> pageMatchPrize(@RequestBody PageParam<MatchPrize> param);

    @PostMapping(value = "/match/prize/add")
    Result addMatchPrize(@RequestBody @Validated MatchPrize matchPrize);

    @PatchMapping(value = "/match/prize/modify")
    Result modifyMatchPrize(@RequestBody @Validated MatchPrize matchPrize);

    @GetMapping(value = "/match/stage/list")
    Result<List<MatchStage>> listByMatch(@RequestParam("matchId") String matchId);

    @PostMapping(value = "/match/stage/add")
    Result addMatchStage(@RequestBody @Validated MatchStage matchStage);

    @PatchMapping(value = "/match/stage/modify")
    Result modifyMatchStage(@RequestBody @Validated MatchStage matchStage);

    @PostMapping(value = "/match/stage/team/add")
    Result addMatchStageTeam(@RequestBody MatchResult matchResult);

    @PostMapping(value = "/match/team/page")
    Result<PageGrid<MatchTeam>> pageMatchTeam(@RequestBody PageParam<MatchTeam> param);

    @GetMapping(value = "/match/team/{markId}")
    Result<MatchTeam> getMatchTeamInfo(@PathVariable("markId") String markId);

    @PostMapping(value = "/match/team/add")
    Result addMatchTeam(@RequestBody @Validated MatchTeam matchteam);

    @PatchMapping(value = "/match/team/modify")
    Result modifyMatchTeam(@RequestBody @Validated MatchTeam matchteam);

    @GetMapping(value = "/match/team/list")
    Result<List<MatchTeam>> listMatchTeam();

    @GetMapping(value = "/match/team/match/list/vo")
    Result<List<MatchTeamVo>> listMatchTeamByMatch(@RequestParam("matchId") String matchId);

    @GetMapping(value = "/match/team/match/list")
    Result<List<MatchTeamVo>> listMatchTeamList(@RequestParam("userId") String userId, @RequestParam("matchId") String matchId);

    @GetMapping(value = "/match/vote/team/list")
    Result<List<MatchTeamVo>> listVoteTeam(@RequestParam("userId") String userId, @RequestParam("matchId") String matchId);

    @GetMapping(value = "/match/vote/count")
    Result<Integer> getUserVoteCount(@RequestParam("userId") String userId);

    @PostMapping(value = "/match/vote/team")
    Result voteMatchTeams(@RequestBody MatchVoteParam matchVoteParam);

    @GetMapping(value = "/match/vote/team/user")
    Result<List<String>> listVoteUserByMatch(@RequestParam("matchId") String matchId, @RequestParam("teamId") String teamId);

    @GetMapping(value = "/match/vote/info")
    Result<List<VoteInfo>> selectVoteInfo(@RequestParam(value = "matchId", required = false) String matchId,
                                          @RequestParam(value = "teamId", required = false) String teamId,
                                          @RequestParam(value = "stageId", required = false) String stageId);

    /**
     * 会员
     */
    @PostMapping(value = "/member/consume", consumes = Contacts.CONSUMES)
    Result<String> memberConsume(@RequestBody MemberDetail detail);

    @GetMapping(value = "/member/create/bar/mark")
    Result<String> createMemberBarMark(@RequestParam("userId") String userId);

    @GetMapping(value = "/member/recharge/rule/x")
    Result<MemberDetail> rechargeByRule(@RequestParam("ruleId") String ruleId, @RequestParam("userId") String userId, @RequestParam("xopenId") String xopenId,
                                        @RequestParam(value = "indentTotal", required = false) BigDecimal indentTotal);

    @GetMapping(value = "/member/scancode")
    Result<String> scanCode(@RequestParam(value = "mark", required = false) String mark,
                            @RequestParam(value = "phone", required = false) String phone);

    @PostMapping(value = "/member/account/add", consumes = Contacts.CONSUMES)
    Result<String> addMemberAccount(@RequestBody MemberAccount account);

    @GetMapping(value = "/member/account/info")
    Result<Map<String, Object>> selectMemberInfo();

    @GetMapping(value = "/member/account/u/{userId}")
    Result<MemberAccount> getMemberInfoByUser(@PathVariable("userId") String userId);

    @GetMapping(value = "/member/account/total")
    Result<BigDecimal> getTotalByUserId(@RequestParam("userId") @NotBlank String userId);

    @GetMapping(value = "/member/account/vo/{markId}")
    Result<MemberAccountVo> getVoInfo(@PathVariable("markId") @NotBlank String markId);

    @GetMapping(value = "/member/account/{markId}")
    Result<MemberAccount> getMemberInfo(@PathVariable("markId") String markId);

    @GetMapping(value = "/member/account/u/{userId}")
    Result<MemberAccount> getMemberInfoByUserId(@PathVariable("userId") String userId);

    @GetMapping(value = "/member/account/vo/{markId}")
    Result<MemberAccountVo> getMemberVoInfo(@PathVariable("markId") String markId);

    @GetMapping(value = "/member/account/p/{phone}")
    Result<List<MemberAccountVo>> getMemberInfoByPhone(@PathVariable("phone") String phone);

    @GetMapping(value = "/member/account/n/{accountNo}")
    Result<List<MemberAccountVo>> getMemberInfoByNo(@PathVariable("accountNo") String accountNo);

    @GetMapping(value = "/member/account/info/{memberId}/res")
    Result<MemberTicketVo> memberInfoById(@PathVariable("memberId") String memberId);

    @PostMapping(value = "/member/account/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<MemberAccount>> pageMemberAccount(@RequestBody PageParam<MemberByType> param);

    @PatchMapping(value = "/member/account/modify", consumes = Contacts.CONSUMES)
    Result<MemberAccount> modifyMemberAccount(MemberAccount base);

    @PostMapping(value = "/member/recharge/res", consumes = Contacts.CONSUMES)
    Result<MemberAccount> recharge(@RequestBody MemberDetailParam detail);

    @PostMapping(value = "/member/recharge/rule/res", consumes = Contacts.CONSUMES)
    Result rechargeByRule(@RequestBody RechargeParam param);

    /**
     * 会员活动
     */
    @PostMapping(value = "/member/activity/info")
    Result<PageGrid<MemberActivity>> dishesActivity(@RequestBody PageParam<MemberActivity> param);

    @PostMapping(value = "/member/activity/add")
    Result addActivity(@RequestBody @Validated MemberActivity memberActivity);

    @PatchMapping(value = "/member/activity/modify")
    Result modifyActivity(@RequestBody MemberActivity memberActivity);

    @PatchMapping(value = "/member/activity/{markId}")
    Result statusActivity(@PathVariable("markId") @NotBlank String markId);

    @DeleteMapping(value = "/member/activity/{markId}")
    Result deleteActivity(@PathVariable("markId") @NotBlank String markId);

    @GetMapping(value = "/member/activity/ticket")
    Result<List<ReceiveTicket>> queryTicketById(@RequestParam("receiveId") @NotBlank String receiveId);

    /**
     * 会员明细
     */
    @PostMapping(value = "/member/detail/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<MemberDetail>> pageMemberDetail(@RequestBody PageParam<MemberDetail> param);

    @PostMapping(value = "/member/detail/user/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<MemberDetail>> pageUserMemberDetail(@RequestBody PageParam<String> param);

    @DeleteMapping(value = "/member/detail/{detailId}")
    Result deleteMemberDetail(@PathVariable("detailId") String detailId, @RequestParam(value = "userId", required = false) String userId);

    @PostMapping(value = "/member/detail/record")
    Result<PageGrid<MemberRecordParam>> memberRecord(@RequestBody PageParam<MemberRecord> param);

    @PostMapping(value = "/member/detail/total")
    Result<Map<String, Object>> memberDetailTotal(@RequestBody MemberRecord memberRecord);

    @PostMapping(value = "/member/detail/payment")
    Result<PageGrid<MemberPaymentParam>> memberPayment(@RequestBody PageParam<MemberRecord> param);

    @GetMapping(value = "/member/detail/markId")
    Result<MemberDetail> selectByMarkId(@RequestParam(value = "markId") @NotBlank String markId);

    /**
     * 会员等级
     */
    @GetMapping(value = "/member/grade/{markId}")
    Result<MemberGrade> selectOne(@PathVariable("markId") @NotBlank String markId);

    @PostMapping(value = "/member/grade/queryAll")
    Result<List<MemberGrade>> queryAll(@RequestBody MemberGrade memberGrade);

    @PostMapping(value = "/member/grade/add")
    Result add(@RequestBody MemberGrade memberGrade);

    @PatchMapping(value = "/member/grade/modify")
    Result modify(@RequestBody MemberGrade memberGrade);

    @DeleteMapping(value = "/member/grade/{markId}")
    Result delete(@PathVariable("markId") @NotBlank String markId);

    @GetMapping(value = "/member/grade/show")
    Result<List<MemberGradeShow>> memberGradeShow(@RequestParam("userId") String userId);

    @PostMapping(value = "/member/grade/queryAll")
    Result<List<MemberGrade>> queryMemberGrade(@RequestBody MemberGrade memberGrade);

    @PostMapping(value = "/member/grade/consume/record")
    Result<PageGrid<GradeRecord>> queryGradeRecord(@RequestBody PageParam<GradeRecord> param);

    @GetMapping(value = "/member/grade/ticket")
    Result<List<GradeTicket>> queryGradeTicket(@RequestParam("gradeId") @NotBlank String gradeId);

    @GetMapping(value = "/member/discount")
    Result<BigDecimal> selectMemberDiscount(@RequestParam("markId") String markId);

    /* 支付回滚 */
    @GetMapping(value = "/pay/back/modify")
    Result modifyPayBack(@RequestParam("payId") String payId);

    @PostMapping(value = "/pay/back/add")
    Result matchPayBack(@RequestBody PayBack payBack);

    /**
     * 抽奖
     */
    @GetMapping(value = "/lottery/info")
    Result<MemberLottery> queryLottery(@RequestParam("markId") String markId);

    @GetMapping(value = "/lottery/type")
    Result<MemberLottery> selectLotteryByType(@RequestParam("type") Integer type,
                                              @RequestParam(value = "status", required = false) Integer status);

    @PostMapping(value = "/lottery/add")
    Result addLottery(@RequestBody MemberLottery memberLottery);

    @GetMapping(value = "/lottery/prize/{markId}")
    Result<PrizeInfo> queryPrizeById(@PathVariable("markId") String markId);

    @PostMapping(value = "/lottery/prize/list")
    Result<List<PrizeInfo>> queryAllPrize(@RequestBody PrizeInfo prizeInfo);

    @PostMapping(value = "/lottery/prize/add")
    Result addPrize(@RequestBody PrizeInfo prizeInfo);

    @PatchMapping(value = "/lottery/prize/modify")
    Result modifyPrize(@RequestBody PrizeInfo prizeInfo);

    @DeleteMapping(value = "/lottery/prize/{markId}")
    Result deletePrizeById(@PathVariable("markId") String markId);

    @PostMapping(value = "/lottery/user/receive")
    Result<List<PrizeInfo>> selectUserReceive(@RequestBody MemberLottery lottery, @RequestParam("userId") String userId);

    @PostMapping(value = "/lottery/receive/list")
    Result<PageGrid<PrizeReceive>> prizeReceiveByPage(@RequestBody(required = false) PageParam<PrizeReceive> param);

    @GetMapping(value = "/lottery")
    Result<PrizeInfo> lottery(@RequestParam("userId") String userId);

    @GetMapping(value = "/lottery/detail/{lotteryId}")
    Result<LotteryInfo> queryInfoById(@PathVariable("lotteryId") String lotteryId);

    @PostMapping(value = "/lottery/detail/add")
    Result addLotteryInfo(@RequestBody LotteryInfo lotteryInfo);

    @PatchMapping(value = "/lottery/detail/modify")
    Result modifyLotteryInfo(@RequestBody LotteryInfo lotteryInfo);

    /**
     * 领取
     */
    @GetMapping(value = "/receive/info/{markId}")
    Result<List<ReceiveVo>> selectByDish(@PathVariable("markId") @NotBlank String markId);

    @PatchMapping(value = "/receive/pend/modify", consumes = Contacts.CONSUMES)
    Result modifyPend(@RequestBody PendDishes pendDishes);

    @PostMapping(value = "/receive/pend", consumes = Contacts.CONSUMES)
    Result<List<PendDishes>> queryPend(@RequestBody PendDishes pendDishes);

    @PostMapping(value = "/receive/info", consumes = Contacts.CONSUMES)
    Result<PageGrid<ReceiveVo>> queryReceive(PageParam<ReceiveDishes> param);

    @PostMapping(value = "/receive/info/add", consumes = Contacts.CONSUMES)
    Result addReceive(@RequestBody @Validated ReceiveDishes receiveDishes);

    @PatchMapping(value = "/receive/info/modify", consumes = Contacts.CONSUMES)
    Result modifyReceive(@RequestBody ReceiveDishes receiveDishes);

    @PostMapping(value = "/receive/Record", consumes = Contacts.CONSUMES)
    Result<List<ReceiveRecord>> queryRecord(@RequestBody ReceiveRecord receiveRecord);

    @PatchMapping(value = "/receive/Record/modify", consumes = Contacts.CONSUMES)
    Result modifyRecord(@RequestBody ReceiveRecord receiveRecord);

    @PostMapping(value = "/receive/ticket/{markId}")
    Result receiveTicket(@PathVariable("markId") @NotBlank String markId);

    /**
     * 会员充值模板
     */
    @GetMapping(value = "/recharge/list")
    Result<List<RechargeRule>> listRechargeRule();

    @GetMapping(value = "/recharge/combobox")
    Result<List<Combobox>> listRechargeRuleCombobox();


    @PostMapping(value = "/recharge/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<RechargeRule>> pageRecharge(@RequestBody PageParam<RechargeRule> base);

    @PostMapping(value = "/recharge/add", consumes = Contacts.CONSUMES)
    Result addRechargeRule(@RequestBody RechargeRule base);

    @GetMapping(value = "/recharge/{markId}")
    Result<RechargeRule> getRechargeRuleInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/recharge/modify", consumes = Contacts.CONSUMES)
    Result modifyRechargeRule(@RequestBody RechargeRule base);

    @DeleteMapping(value = "/recharge/{ruleId}")
    Result deleteRechargeRule(@PathVariable("ruleId") String ruleId);

    @PatchMapping(value = "/recharge/batch/{status}", consumes = Contacts.CONSUMES)
    Result modifyRechargeRuleStatus(@RequestBody String[] ruleIds, @PathVariable("status") Integer status);

    @GetMapping(value = "/recharge/ticket/{ruleId}")
    Result<List<TicketTemplateVo>> getRechargeTickets(@PathVariable("ruleId") String ruleId);

    /**
     * 签到
     */
    @GetMapping(value = "/sign/{markId}")
    Result<SignInfoParam> selectBySignMemberId(@PathVariable("markId") @NotBlank String markId);

    @PostMapping(value = "/sign/info")
    Result<List<SignInfoParam>> selectBySignMember(@RequestBody(required = false) SignMember signMember);

    @GetMapping(value = "/sign/rule/{markId}")
    Result<SignRule> queryBySignRuleId(@PathVariable("markId") @NotBlank String markId);

    @PostMapping(value = "/sign/rule/info")
    Result<List<SignRule>> querySignRule(@RequestBody(required = false) SignRule signRule);

    @PostMapping(value = "/sign/rule/add")
    Result addSignRule(@RequestBody SignRule signRule);

    @PatchMapping(value = "/sign/rule/modify")
    Result modifySignRule(@RequestBody SignRule signRule);

    @PostMapping(value = "/sign/detail/page")
    Result<PageGrid<SignDetail>> querySignDetailList(PageParam<SignDetail> param);

    @PostMapping(value = "/sign/user/show")
    Result<SignInfoParam> queryUserSign(@RequestBody(required = false) SignMember signMember);

    @DeleteMapping(value = "/sign/rule/{markId}")
    Result deleteBySignRuleId(@PathVariable("markId") @NotBlank String markId);
}
