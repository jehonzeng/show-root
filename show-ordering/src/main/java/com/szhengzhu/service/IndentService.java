package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.Indent;
import com.szhengzhu.bean.ordering.IndentInfo;
import com.szhengzhu.bean.ordering.UserIndent;
import com.szhengzhu.bean.ordering.param.DetailDiscountParam;
import com.szhengzhu.bean.ordering.param.DetailParam;
import com.szhengzhu.bean.ordering.param.IndentPageParam;
import com.szhengzhu.bean.ordering.param.IndentParam;
import com.szhengzhu.bean.ordering.vo.IndentBaseVo;
import com.szhengzhu.bean.ordering.vo.IndentVo;
import com.szhengzhu.bean.xwechat.vo.IndentModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IndentService {

    /**
     * 通过订单号获取订单信息
     *
     * @param indentNo
     * @return
     */
    Indent getInfoByNo(String indentNo);

    /**
     * 点餐平台直接下确认状态订单
     *
     * @param indentParam
     * @return
     */
    List<String> createBatch(IndentParam indentParam);

    /**
     * 服务员确认订单
     *
     * @param tableId
     * @param employeeId
     * @return
     */
    List<String> confirm(String tableId, String employeeId);

    /**
     * 小程序点餐创建该桌订单
     *
     * @param tableId
     * @param userId
     * @return
     */
    String create(String tableId, String userId);

    /**
     * 小程序获取订单列表
     *
     * @param param
     * @return
     */
    PageGrid<IndentModel> pageBase(PageParam<String> param);

    /**
     * 获取订单预览单
     *
     * @param indentId
     * @return
     */
    IndentModel getIndentModel(String indentId);

    /**
     * 点餐时修改订单商品
     *
     * @param detailParam
     * @return
     */
    void modifyDetail(DetailParam detailParam);

    /**
     * 点餐时退订单商品
     * 商品数量>1 就减1
     * 商品数量=1 设置该商品为删除状态
     *
     * @param detailId
     * @param employeeId
     * @return
     */
    List<String> deleteDetail(String detailId, String employeeId);

    /**
     * 点餐时获取订单商品列表
     *
     * @param tableId
     * @return
     */
//    IndentDetailVo listDetail(String tableId);

    /**
     * 点餐平台获取订单商品列表
     *
     * @param indentId
     * @return
     */
    IndentBaseVo listDetailById(String indentId);

    /**
     * 订单商品优惠
     *
     * @param discountParam
     * @return
     */
    void detailDiscount(DetailDiscountParam discountParam);


    /**
     * 点餐平台结账
     *
     * @param indentId
     * @param employeeId 收银员
     * @return
     */
    List<String> bill(String indentId, String employeeId);

    /**
     * 点餐平台反结账
     *
     * @param indentId
     * @param employeeId
     * @return
     */
    String cancelBill(String indentId, String employeeId);

    /**
     * 点餐平台获取账单管理分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<IndentVo> page(PageParam<IndentPageParam> pageParam);

    /**
     * 修改订单状态
     *
     * @param indentId
     * @param status
     * @return
     */
    void modifyIndentStatus(String indentId, String status);

    /**
     * 获取订单信息
     *
     * @param indentId
     * @return
     */
    Indent getInfo(String indentId);

    /**
     * 订单绑定会员
     *
     * @param indentId
     * @param memberId
     * @return
     */
    void bindMember(String indentId, String memberId);

    /**
     * 获取用户已选商品
     *
     * @param indentId
     * @return
     */
    List<Map<String, Object>> listIndentComm(String indentId);

    /**
     * 获取桌台当前订单信息
     *
     * @param tableId
     * @return
     */
    Indent getInfoByTable(String tableId);

    /**
     * 获取订单剩余支付金额
     *
     * @param indentId
     * @return
     */
    BigDecimal getCostTotal(String indentId);

    /**
     * 获取订单用户
     *
     * @param indentId
     * @return
     */
    List<String> listIndentUser(String indentId);

    /**
     * 根据订单id查询订单信息
     *
     * @param markId
     * @return
     */
    IndentInfo selectById(String markId);

    /**
     * 根据用户id查询订单信息
     *
     * @param userId 用户id
     * @return
     */
    UserIndent userIndent(String userId);

    BigDecimal getMemberDiscountTotal(String indentId);
}
