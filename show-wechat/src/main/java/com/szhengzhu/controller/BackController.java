package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.feign.ShowActivityClient;
import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.base.ActionItem;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.base.ReplyInfo;
import com.szhengzhu.bean.base.ScanReply;
import com.szhengzhu.bean.ordering.Table;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.WechatInfo;
import com.szhengzhu.common.Commons;
import com.szhengzhu.config.FtpServer;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.context.OrderContext;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.handler.AbstractOrder;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.util.ExpireSet;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.WechatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.MediaType;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.message.message.ImageMessage;
import weixin.popular.bean.message.message.MiniprogrampageMessage;
import weixin.popular.bean.message.message.MiniprogrampageMessage.Miniprogrampage;
import weixin.popular.bean.message.message.NewsMessage;
import weixin.popular.bean.message.message.TextMessage;
import weixin.popular.bean.user.User;
import weixin.popular.util.XMLConverUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jehon Zeng
 */
@Slf4j
@Api(tags = {"微信回调主题：BackController"})
@RestController
@RequestMapping("/back")
public class BackController {

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Resource
    private WechatConfig config;

    @Resource
    private FtpServer ftpServer;

    @Resource
    private OrderContext orderContext;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowActivityClient showActivityClient;

    @Resource
    private ShowOrderingClient showOrderingClient;

    /**
     * 重复通知过滤
     */
    private static ExpireSet<String> expireSet = new ExpireSet<>(60);

    @ApiOperation(value = "微信校验处理接口", notes = "微信校验处理接口")
    @GetMapping(value = "/signature")
    public void signature(@RequestParam(value = "signature") String signature,
                          @RequestParam(value = "timestamp") String timestamp,
                          @RequestParam(value = "nonce") String nonce,
                          @RequestParam(value = "echostr") String echostr, HttpServletResponse response)
            throws IOException {
        PrintWriter writer = response.getWriter();
        writer.print(echostr);
    }

    @ApiOperation(value = "微信支付后回滚", notes = "微信支付之后回滚")
    @RequestMapping(value = "/wechatPay", method = {RequestMethod.POST, RequestMethod.GET})
    public void wechatPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        payBack(request, response, 1);
    }

    @ApiOperation(value = "web支付后回滚", notes = "web支付之后回滚")
    @RequestMapping(value = "/webPay", method = {RequestMethod.POST, RequestMethod.GET})
    public void webPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        payBack(request, response, 2);
    }

    /**
     * 支付回调
     *
     * @param request
     * @param response
     * @param payType 1:微信支付  2：web支付
     * @throws Exception
     */
    private void payBack(HttpServletRequest request, HttpServletResponse response, int payType) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder xml = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            xml.append(line + "\n");
        }
        Map<String, String> payNotify = XMLConverUtil.convertToMap(xml.toString());
        String outTradeNo = payNotify.get("out_trade_no");
        String transactionId = payNotify.get("transaction_id");
        log.info("微信支付回滚：{}", outTradeNo);
        if (StrUtil.isEmpty(outTradeNo)) {
            return;
        }
        // 已处理 去重
        String payBackKey = "wechat:pay:back:" + transactionId;
        if (redis.hasKey(payBackKey)) {
            return;
        }
        redis.set(payBackKey, 1, 30);
        String type = Character.toString(outTradeNo.charAt(0));
        AbstractOrder handler = orderContext.getInstance(type);
        handler.orderBack(response, payNotify, payBackKey, payType);
    }

    @ApiOperation(value = "微信事件处理接口", notes = "微信事件处理接口")
    @PostMapping(value = "/signature")
    public void signature(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("微信事件处理");
        if (request.getInputStream() == null) {
            response.getOutputStream().flush();
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
        EventMessage eventMsg = XMLConverUtil.convertToObject(EventMessage.class, reader);
        response.getOutputStream().flush();
        String openId = eventMsg.getFromUserName();
        String msgType = eventMsg.getMsgType();
        String event = eventMsg.getEvent();
        String eventKey = eventMsg.getEventKey();
        if (Contacts.WECHAT_EVENT_MSG_TYPE_EVNET.equals(msgType)) {
            wechatEvent(event, openId, eventKey);
        } else if (Contacts.WECHAT_EVENT_MSG_TYPE_TEXT.equals(msgType)) {
            // 微信处理 自动回复
            autoReply(openId, eventMsg.getContent());
        }
    }

    public void wechatEvent(String event, String openId, String eventKey) {
        switch (event) {
            case Contacts.WECHAT_EVENT_CLICK:
                log.info("微信点击事件，用户：{}， 编码：{}", openId, eventKey);
                codeReply(openId, eventKey);
                break;
            case Contacts.WECHAT_EVENT_SCAN:
                log.info("扫码提示，用户：{}， 编码：{}", openId, eventKey);
                scanAction(openId, Commons.QR_PRE + eventKey);
                break;
            case Contacts.WECHAT_EVENT_SUBSCRIBE:
                log.info("微信关注提示，用户：{}， 编码：{}", openId, eventKey);
                subscribeAction(openId, eventKey);
                break;
            case Contacts.WECHAT_EVENT_UNSUBSCRIBE:
                log.info("微信取消关注提示，用户：{}", openId);
                sender.modifyWechatStatus(openId, 2);
                break;
            default:
                break;
        }
    }

    /**
     * 微信扫码进入公众号提示（已关注的用户）
     *
     * @param openId
     * @param qrCode
     */
    private void scanAction(String openId, String qrCode) {
        if (!StrUtil.isEmpty(qrCode)) {
            qrcodeReply(openId, qrCode);
        }
        Result<UserInfo> userResult = showUserClient.getUserByOpenId(openId);
        if (!userResult.isSuccess()) {
            saveUserInfo(openId, qrCode);
        }
        sender.modifyWechatStatus(openId, 1);
    }

    /**
     * 用户关注公众号或扫码关注公众号，自动推送
     *
     * @param openId
     * @param qrCode
     */
    private void subscribeAction(String openId, String qrCode) {
        // 用户关注微信公众号自动推送
        autoReply(openId, Contacts.WECHAT_FOLLOW);
        scanAction(openId, qrCode);
    }

    private void saveUserInfo(String openId, String source) {
        User user = UserAPI.userInfo(config.refreshToken(), openId);
        if (!user.isSuccess()) {
            return;
        }
        WechatInfo wechat = WechatInfo.builder()
                .openId(openId).nickName(user.getNickname()).headerImg(user.getHeadimgurl()).source(source)
                .wechatStatus(1).build();
        showUserClient.addWechat(wechat);
        ImageInfo imageInfo = WechatUtils.downLoadImage(ftpServer, user.getHeadimgurl());
        String headerImg = null;
        if (ObjectUtil.isNotNull(imageInfo)) {
            showBaseClient.addImgInfo(imageInfo);
            headerImg = imageInfo.getMarkId();
        }
        UserInfo userInfo = UserInfo.builder()
                .nickName(user.getNickname()).headerImg(headerImg).gender(user.getSex())
                .city(user.getCity()).province(user.getProvince()).country(user.getCountry()).language(user.getLanguage())
                .wopenId(user.getOpenid()).unionId(user.getUnionid())
                .wechatStatus(user.getSubscribe()).build();
        showUserClient.addUser(userInfo);
    }

    /**
     * 用户关注公众号自动推送、用户公众号自动回复
     *
     * @param openId
     * @param msg
     */
    private void autoReply(String openId, String msg) {
        String managerOpenId = "oUX2qwUMT3SqANiBJVNTOeWI1JO0";
        if (Contacts.OPEN_THE_DOOR.equals(msg) && managerOpenId.equals(openId)) {
            // 补充操作
            return;
        }
        Result<ReplyInfo> replyResult = showBaseClient.getReplyInfoByMsg(msg);
        String code = ObjectUtil.isNull(replyResult.getData()) ? "" : replyResult.getData().getActionCode();
        codeReply(openId, code);
    }

    /**
     * 带action code, 自动推送
     * 例：code=HQ_1
     *
     * @param openId
     * @param code
     */
    private void codeReply(String openId, String code) {
        code = StringUtils.isEmpty(code) ? "" : code;
        Result<List<ActionItem>> itemResult = showBaseClient.listActionItemByCode(code);
        if (!itemResult.isSuccess()) {
            return;
        }
        List<ActionItem> itemList = itemResult.getData();
        // 图文推送
        List<ActionItem> tempList = itemList.stream().filter(item -> Contacts.ACTION_ARTICLE_TYPE.equals(item.getServerType())).collect(Collectors.toList());
        sendArticle(tempList, openId);
        // 非图文推送
        tempList = itemList.stream().filter(item -> !Contacts.ACTION_ARTICLE_TYPE.equals(item.getServerType())).collect(Collectors.toList());
        for (ActionItem item : tempList) {
            if (Contacts.ACTION_TEXT_TYPE.equals(item.getServerType())) {
                // 文本推送
                sendText(item.getContent(), openId);
            } else if (Contacts.ACTION_IMAGE_TYPE.equals(item.getServerType())) {
                // 图片推送
                sendImage(item.getImagePath(), openId);
            }
        }
    }

    /**
     * 推送文本信息
     *
     * @param text
     * @param openId
     */
    private void sendText(String text, String openId) {
        text = text.replace("<br>", "\n");
        text = String.format(text, config.getXappid(), config.getXuserPage());
        TextMessage message = new TextMessage(openId, text);
        WechatUtils.messageSend(config, message);
    }

    /**
     * 推送图片信息
     *
     * @param imagePath
     * @param openId
     */
    private void sendImage(String imagePath, String openId) {
        String url = Contacts.IMAGE_SERVER + "/" + imagePath;
        Media media = WechatUtils.uploadMedia(config, url, MediaType.image);
        if (!media.isSuccess()) {
            return;
        }
        ImageMessage message = new ImageMessage(openId, media.getMedia_id());
        WechatUtils.messageSend(config, message);
    }

    /**
     * 推送文章
     *
     * @param articles
     * @param openId
     */
    private void sendArticle(List<ActionItem> articles, String openId) {
        if (articles.isEmpty()) {
            return;
        }
        List<NewsMessage.Article> news = new ArrayList<>();
        for (ActionItem temp : articles) {
            String url = Contacts.IMAGE_SERVER + "/" + temp.getImageUrl();
            NewsMessage.Article Article = new NewsMessage.Article(temp.getTitle(), temp.getContent(),
                    temp.getLinkUrl(), url);
            news.add(Article);
        }
        NewsMessage message = new NewsMessage(openId, news);
        WechatUtils.messageSend(config, message);
    }

    /**
     * 扫带自定义参数二维码，自动推送
     *
     * @param openId
     * @param qrCode
     */
    private void qrcodeReply(String openId, String qrCode) {
        log.info("扫带自定义参数二维码，自动推送 ({})", qrCode);
        String code = qrCode.split("_")[1];
        Result<List<ScanReply>> replyResult = showBaseClient.listScanRelyByCode(code);
        List<ScanReply> replyList = replyResult.getData();
        // 图文推送
        List<ScanReply> tempList = replyList.stream().filter(reply -> Contacts.ACTION_ARTICLE_TYPE.equals(reply.getServerType())).collect(Collectors.toList());
        sendArticle(openId, tempList);
        // 非图文推送
        tempList = replyList.stream().filter(reply -> !Contacts.ACTION_ARTICLE_TYPE.equals(reply.getServerType())).collect(Collectors.toList());
        for (ScanReply reply : tempList) {
            if (Contacts.ACTION_TEXT_TYPE.equals(reply.getServerType())) {
                // 推送文本
                sendText(reply.getContent(), openId);
            } else if (Contacts.ACTION_IMAGE_TYPE.equals(reply.getServerType())) {
                // 推送图片
                sendImage(reply.getImagePath(), openId);
            } else if (Contacts.ACTION_SCAN_WIN_TYPE.equals(reply.getServerType())) {
                // 推送扫码抽奖
                scanWin(code, openId);
            } else if (Contacts.ACTION_MIN_TYPE.equals(reply.getServerType())) {
                // 小程序扫码推送桌台信息
                String title = reply.getTitle();
                String storeId = qrCode.split("_")[2];
                String tableCode = qrCode.split("_")[3];
                sendMinCar(openId, reply.getImagePath(), title, storeId, tableCode);
            }
        }
    }

    /**
     * 扫带自定义参数二维码推送文章
     *
     * @param openId
     * @param articles
     */
    private void sendArticle(String openId, List<ScanReply> articles) {
        if (articles.isEmpty()) {
            return;
        }
        List<NewsMessage.Article> news = new ArrayList<>();
        for (ScanReply temp : articles) {
            String url = Contacts.IMAGE_SERVER + "/" + temp.getImagePath();
            NewsMessage.Article Article = new NewsMessage.Article(temp.getTitle(), temp.getContent(),
                    temp.getLinkUrl(), url);
            news.add(Article);
        }
        NewsMessage message = new NewsMessage(openId, news);
        WechatUtils.messageSend(config, message);
    }

    /**
     * 扫码中奖
     *
     * @param scanCode
     * @param openId
     */
    private void scanWin(String scanCode, String openId) {
        Result<String> result = showActivityClient.scanWin(scanCode, openId);
        if (!result.isSuccess()) {
            return;
        }
        sendText(result.getData(), openId);
    }

    /**
     * 发送小程序卡片
     *
     * @param openId
     */
    private void sendMinCar(String openId, String imagePath, String title, String storeId, String tableCode) {
        Result<Table> tableResult = showOrderingClient.getTableInfoByStoreCode(storeId, tableCode);
        if (!tableResult.isSuccess()) {
            return;
        }
        Table table = tableResult.getData();
        title = String.format(title, table.getName());
        String url = Contacts.IMAGE_SERVER + "/" + imagePath;
        Media media = WechatUtils.uploadMedia(config, url, MediaType.image);
        if (!media.isSuccess()) {
            return;
        }
        log.info(media.getMedia_id());
        Miniprogrampage miniprogrampage = new Miniprogrampage();
        miniprogrampage.setAppid(config.getXappid());
        miniprogrampage.setPagepath(String.format(config.getXtablePage(), table.getMarkId(), System.currentTimeMillis()));
        miniprogrampage.setTitle(title);
        miniprogrampage.setThumb_media_id(media.getMedia_id());
        MiniprogrampageMessage message = new MiniprogrampageMessage(openId, miniprogrampage);
        WechatUtils.messageSend(config, message);
    }
}
