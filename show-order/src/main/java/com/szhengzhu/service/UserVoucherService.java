package com.szhengzhu.service;

import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.order.UserVoucher;
import com.szhengzhu.bean.wechat.vo.VoucherBase;

import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
public interface UserVoucherService {

    /**
     * 获取用户商品代金券
     * 
     * @date 2019年6月12日 下午12:29:13
     * @param userId
     * @return
     */
    List<VoucherBase> listByUser(String userId);

    /**
     * 通过id列表获取id对应信息
     *
     * @param vouchers
     * @return
     */
    Map<String, UserVoucher> mapByIds(List<String> vouchers);
    
    /**
     * 创建用户商品券信息
     *
     * @param userId
     * @param voucher
     * @return
     * @date 2019年9月18日
     */
    void addGoodsVoucher(String userId,GoodsVoucher voucher);
}
