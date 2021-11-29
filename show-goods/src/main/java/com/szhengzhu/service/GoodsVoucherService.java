package com.szhengzhu.service;

import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVoucherVo;
import com.szhengzhu.bean.wechat.vo.GoodsDetail;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface GoodsVoucherService {

    /**
     * 添加商品券信息
     *
     * @date 2019年3月12日 下午6:38:39
     * @param base
     * @return
     */
    GoodsVoucher addGoodsVoucher(GoodsVoucher base);

    /**
     * 修改商品券信息
     *
     * @date 2019年3月12日 下午6:38:42
     * @param base
     * @return
     */
    GoodsVoucher editGoodsVoucher(GoodsVoucher base);

    /**
     * 获取商品券分页信息
     *
     * @date 2019年3月12日 下午6:38:44
     * @param base
     * @return
     */
    PageGrid<GoodsVoucherVo> getCouponPage(PageParam<GoodsVoucher> base);

    /**
     * 获取菜品券下来列表
     *
     * @date 2019年3月20日 上午10:32:22
     * @return
     */
    List<Combobox> listCombobox();

    /**
     * 根据id获取商品券信息
     *
     * @date 2019年3月25日 下午5:23:21
     * @param markId
     * @return
     */
    GoodsVoucher getGoodsVoucherInfo(String markId);

    /**
     * 商城获取菜品券详细信息
     *
     * @date 2019年6月19日 下午6:44:25
     * @param voucherId
     * @param userId
     * @return
     */
    GoodsDetail getVoucherDetail(String voucherId, String userId);

    /**
     * 商城获取菜品券评论列表 （即对应商品评论列表）
     *
     * @date 2019年6月27日 下午5:00:40
     * @param voucherId
     * @param userId
     * @return
     */
    List<JudgeBase> listVoucherJudge(String voucherId, String userId);

    /**
     * 获取商品券库存
     *
     * @date 2019年7月8日 下午5:21:06
     * @param voucherId
     * @return
     */
    StockBase getStockInfo(String voucherId);

    /**
     * 修改商品券库存
     *
     * @date 2019年8月6日 下午2:29:42
     * @param voucherId
     * @param quantity
     */
    void subStock(String voucherId, int quantity);

    /**
     * 用户取消订单恢复库存
     *
     * @date 2019年8月6日 下午3:59:42
     * @param voucherId
     * @param quantity
     */
    void addStock(String voucherId, int quantity);
}
