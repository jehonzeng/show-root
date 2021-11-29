package com.szhengzhu.controller;

import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.member.IntegralDetail;
import com.szhengzhu.bean.order.ContactUser;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.user.ManagerCode;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.CustomerMessage;
import com.szhengzhu.bean.vo.SmsModel;
import com.szhengzhu.bean.vo.UserVo;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.util.SmsUtils;
import com.szhengzhu.util.WechatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.message.message.ImageMessage;
import weixin.popular.bean.message.message.NewsMessage;
import weixin.popular.bean.message.message.TextMessage;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Slf4j
@Api(tags = {"用户管理：UserController"})
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Resource
    private Sender sender;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowMemberClient showMemberClient;

    @Resource
    private WechatConfig wechatConfig;

    @ApiOperation(value = "获取用户分页列表", notes = "获取用户分页列表，当roleIds不为空时，查询属于该角色集的用户")
    @PostMapping(value = "/page")
    public Result<PageGrid<UserVo>> pageUser(@RequestBody PageParam<UserVo> userPage) {
        return showUserClient.pageUser(userPage);
    }

    @ApiOperation(value = "获取用户分页列表", notes = "获取用户分页列表，当roleIds不为空时，查询出不属于该角色集的用户")
    @PostMapping(value = "/page/notin")
    public Result<PageGrid<UserVo>> pageUserNotIn(@RequestBody PageParam<UserVo> userPage) {
        return showUserClient.pageOutRoleUser(userPage);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "获取用户详细信息")
    @GetMapping(value = "/{markId}")
    public Result<UserInfo> getUserById(@PathVariable("markId") @NotBlank String markId) {
        return showUserClient.getUserById(markId);
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @PatchMapping(value = "")
    public Result modifyUser(@RequestBody UserInfo userInfo) {
        return showUserClient.modifyUser(userInfo);
    }

    @ApiOperation(value = "认证厨师信息", notes = "可修改厨师认证信息")
    @PatchMapping(value = "/cook")
    public Result<CookCertified> modifyCertified(@RequestBody @Validated CookCertified cookCertified) {
        return showGoodsClient.modifyCertified(cookCertified);
    }

    @ApiOperation(value = "录入待认证厨师信息", notes = "录入待认证厨师信息")
    @PostMapping(value = "/cook")
    public Result<CookCertified> saveCertified(@RequestBody @Validated CookCertified cookCertified) {
        return showGoodsClient.addCertified(cookCertified);
    }

    @ApiOperation(value = "获取厨师认证分页列表", notes = "获取厨师认证分页列表")
    @PostMapping(value = "/cook/page")
    public Result<PageGrid<CookCertified>> pageCook(
            @RequestBody PageParam<CookCertified> cookPage) {
        return showGoodsClient.pageCook(cookPage);
    }

    @ApiOperation(value = "获取厨师认证信息", notes = "获取厨师认证信息")
    @GetMapping(value = "/cook/{markId}")
    public Result<CookCertified> getCookById(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.getCookById(markId);
    }

    @ApiOperation(value = "获取厨师下拉列表", notes = "获取厨师下拉列表")
    @GetMapping(value = "/cook/combobox")
    public Result<List<Combobox>> listCookCombobox() {
        return showGoodsClient.listCookerCombobox();
    }

    @ApiOperation(value = "获取用户优惠券分页列表", notes = "获取用户优惠券分页列表")
    @PostMapping(value = "/coupon/page")
    public Result<PageGrid<UserCoupon>> pageCoupon(@RequestBody PageParam<UserCoupon> couponPage) {
        return showOrderClient.pageCoupon(couponPage);
    }

//    @ApiOperation(value = "获取用户积分账户分页列表", notes = "获取用户积分账户分页列表")
//    @RequestMapping(value = "/integral/account/page")
//    public Result<PageGrid<IntegralVo>> pageIntegralAccount(
//            @RequestBody PageParam<Map<String, String>> integralPage) {
//        return showMemberClient.pageIntegralAccount(integralPage);
//    }

    @ApiOperation(value = "获取用户积分明细分页列表", notes = "获取用户积分明细分页列表")
    @PostMapping(value = "/integral/detail/page")
    public Result<PageGrid<IntegralDetail>> pageIntegral(@RequestBody PageParam<IntegralDetail> integralPage) {
        return showMemberClient.pageIntegralDetail(integralPage);
    }

    @ApiOperation(value = "获取用户地址列表", notes = "获取用户地址列表")
    @PostMapping(value = "/address/page")
    public Result<PageGrid<UserAddress>> pageAddress(
            @RequestBody PageParam<UserAddress> addressPage) {
        return showOrderClient.pageAddress(addressPage);
    }

    @ApiOperation(value = "获取用户下拉列表", notes = "获取用户下拉列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> getUserList() {
        return showUserClient.getUserList();
    }

    @ApiOperation(value = "管理员指定用户发送某种优惠券", notes = "管理员指定用户发送某种优惠券")
    @GetMapping(value = "/coupon/send")
    public Result sendCoupon(@RequestParam("userId") @NotBlank String userId,
                             @RequestParam("templateId") @NotBlank String templateId) {
        return showOrderClient.sendCoupon(userId, templateId);
    }

    @ApiOperation(value = "管理员指定用户发送某种商品券", notes = "管理员指定用户发送某种商品券")
    @GetMapping(value = "/voucher/send")
    public Result sendGoodsVoucher(@RequestParam("userId") @NotBlank String userId,
                                   @RequestParam("voucherId") @NotBlank String voucherId) {
        return showOrderClient.sendGoodsVoucher(userId, voucherId);
    }

    @ApiOperation(value = "批量推送配送信息(暂时没用)", notes = "批量推送配送信息(暂时没用)")
    @PostMapping(value = "/template/sendMsg")
    public Result<?> sendTemplateMsg(@RequestBody @NotEmpty List<SmsModel> list) {
        String[] contents;
        for (SmsModel model : list) {
            contents = model.getContent().trim().split("@");
            log.info(Arrays.toString(contents));
            SmsUtils.sendDynamicMsg(model, contents);
        }
        return new Result<>();
    }

    @ApiOperation(value = "验证内部用户(仅做测试)", notes = "验证内部用户(仅做测试)")
    @GetMapping(value = "/manage/{markId}")
    public Result<?> checkManage(@PathVariable("markId") @NotBlank String markId) {
        return new Result<>(showUserClient.checkManage(markId));
    }

    @ApiOperation(value = "获取内部口令列表", notes = "获取内部口令列表")
    @PostMapping(value = "/managerCode/page")
    public Result<PageGrid<ManagerCode>> page(@RequestBody PageParam<ManagerCode> pageParam) {
        return showUserClient.pageManagerCode(pageParam);
    }

    @ApiOperation(value = "刷新内部人员信息", notes = "刷新内部人员信息")
    @GetMapping(value = "/managerCode/reflush")
    public Result reflush() {
        return showUserClient.reflushManagerCode();
    }

    @ApiOperation(value = "修改内部人员口令", notes = "修改内部人员口令")
    @PatchMapping(value = "/managerCode")
    public Result modify(@RequestBody @Validated ManagerCode code) {
        return showUserClient.modifyManagerCode(code);
    }

    @ApiOperation(value = "主动发送客服信息", notes = "主动发送客服信息")
    @PostMapping(value = "/customer/message")
    public Result<?> sendUserCustomerMessage(@RequestBody @Validated CustomerMessage base) {
        // 1.消息类型：0文本、1图片、2图文
        if (Contacts.ACTION_TEXT_TYPE.equals(base.getMsgType())) {
            String text = base.getContent();
            text = text.replace("<br>", "\n");
            TextMessage message = new TextMessage(base.getOpenId(), text);
            WechatUtils.messageSend(wechatConfig, message);
        } else if (Contacts.ACTION_IMAGE_TYPE.equals(base.getMsgType())) {
            String picUrl = Contacts.IMAGE_SERVER + "/" + base.getImagePath();
            Media media = WechatUtils.uplodMedia(wechatConfig, picUrl);
            ImageMessage imageMessage = new ImageMessage(base.getOpenId(), media.getMedia_id());
            WechatUtils.messageSend(wechatConfig, imageMessage);
        } else if (Contacts.ACTION_ARTICLE_TYPE.equals(base.getMsgType())) {
            List<NewsMessage.Article> news = new ArrayList<>();
            String url = Contacts.IMAGE_SERVER + "/" + base.getAticleImage();
            NewsMessage.Article article = new NewsMessage.Article(base.getTitle(),
                    base.getDescription(), base.getLinkUrl(), url);
            news.add(article);
            NewsMessage articleMessage = new NewsMessage(base.getOpenId(), news);
            WechatUtils.messageSend(wechatConfig, articleMessage);
        }
        return new Result<>();
    }

    @ApiOperation(value = "通知用户异常", notes = "通知用户异常")
    @PostMapping(value = "/contact")
    public Result<?> contactUser(@RequestBody @Validated ContactUser user) {
        sender.contactUser(user.getOrderId(), user.getContent(), user.getReason());
        return new Result<>();
    }
}
