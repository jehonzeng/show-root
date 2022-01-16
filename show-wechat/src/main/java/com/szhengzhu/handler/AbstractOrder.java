package com.szhengzhu.handler;

import com.szhengzhu.feign.ShowOrderClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderBase;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.WechatUtils;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.paymch.MchBaseResult;
import weixin.popular.util.XMLConverUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
public abstract class AbstractOrder {

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private WechatConfig wechatConfig;

    /**
     * 订单支付成功回滚
     *
     * @param response
     * @param payNotify
     * @param payBackKey redis去重键值
     * @param payType
     * @throws Exception
     */
    public abstract void orderBack(HttpServletResponse response, Map<String, String> payNotify, String payBackKey, int payType) throws Exception;

    /**
     * 用户下单成功，给管理员发送信息
     *
     * @param orderNo
     */
    public abstract void sendManageMessage(String orderNo);

    /**
     * 获取订单基本信息
     *
     * @param orderNo
     * @return
     */
    public abstract OrderBase getOrderBase(String orderNo);

    /**
     * 根据订单号修改订单状态
     *
     * @param orderNo
     * @param orderStatus
     * @param userId
     * @return
     */
    public abstract Result<?> modifyStatusByNo(String orderNo, String orderStatus, String userId);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    public abstract Result<OrderDetail> getOrderDetail(String orderNo, String userId);

    /**
     * 获取需要评价的商品列表
     *
     * @param orderNo
     * @param userId
     * @return
     */
    public abstract Result<List<Judge>> listOrderItemJudge(String orderNo, String userId);

    /**
     * 订单评价
     *
     * @param orderNo
     * @param userId
     */
    public abstract String orderJudge(String orderNo, String userId);

    /**
     * 用户下单给管理员推送信息
     *
     * @param orderId
     * @param orderNo
     * @param orderTime
     */
    public void sendMessageToManager(String orderId, String orderNo, Date orderTime) {
        Result<OrderDelivery> deliveryResult = showOrderClient.getDeliveryByOrder(orderId);
        Result<List<Map<String, String>>> manageResult = showUserClient.listManager();
        if (manageResult.isSuccess() && !manageResult.getData().isEmpty()) {
            TemplateMessage message = WechatUtils.noticeOrderAction(orderNo, orderTime,
                    deliveryResult.getData());
            for (Map<String, String> user : manageResult.getData()) {
                if (StringUtils.isEmpty(user.get("wopenId"))) {
                    break;
                }
                message.setTouser(user.get("wopenId"));
                MessageAPI.messageTemplateSend(wechatConfig.refreshToken(), message);
            }
        }
    }

    /**
     * 回调成功
     *
     * @param response
     * @throws IOException
     */
    protected void successResponse(HttpServletResponse response) throws IOException {
        MchBaseResult baseResult = new MchBaseResult();
        baseResult.setReturn_code("SUCCESS");
        baseResult.setReturn_msg("OK");
        response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 回调失败
     *
     * @param response
     * @throws IOException
     */
    protected void failResponse(HttpServletResponse response) throws IOException {
        MchBaseResult baseResult = new MchBaseResult();
        baseResult.setReturn_code("FAIL");
        baseResult.setReturn_msg("ERROR");
        response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
