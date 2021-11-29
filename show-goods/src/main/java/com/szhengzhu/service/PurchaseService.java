package com.szhengzhu.service;

import com.szhengzhu.bean.excel.MealGoodsModel;
import com.szhengzhu.bean.goods.PurchaseHistory;
import com.szhengzhu.bean.goods.PurchaseInfo;
import com.szhengzhu.bean.vo.PurchaseFood;
import com.szhengzhu.bean.vo.PurchaseHistoryVo;
import com.szhengzhu.bean.vo.TodayProductVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface PurchaseService {
    /**
     * 获取采购订单列表
     * 
     * @date 2019年5月21日 上午11:06:15
     * @param base
     * @return
     */
    PageGrid<PurchaseFood> getPurchasePage(PageParam<PurchaseInfo> base);

    /**
     * 获取采购记录列表
     * 
     * @date 2019年5月21日 上午11:06:18
     * @param base
     * @return
     */
    PageGrid<PurchaseHistoryVo> getHistoryPage(PageParam<PurchaseHistory> base);

    /**
     * 指定指派人
     * 
     * @date 2019年5月21日 上午11:06:06
     * @param markId
     * @param userId
     * @return
     */
    void appoinStaff(String markId, String userId);

    /**
     * 撤销指派人
     * 
     * @date 2019年5月21日 上午11:06:12
     * @param markId
     * @return
     */
    void revokeStaff(String markId);
    
  
    void buyFood(PurchaseInfo base);

    /**
     * 生成今日采购单
     * 
     * @date 2019年5月21日 上午11:07:30
     * @return
     */
    void createPurchaseOrder();

    /**
     * 删除昨天的采购单
     * 
     * @date 2019年5月21日 上午11:07:34
     * @param buyTime
     */
    void deletePurchaseOrder(String buyTime);

    /**
     * 更新采购单
     * 
     * @date 2019年5月21日 上午11:07:37
     * @return
     */
    void refreshPurchaseOrder();

    /**
     * 获取不同状态列表
     *
     * @param userId
     * @param status
     * @return
     * @date 2019年7月10日
     */
    List<PurchaseFood> getListByStatus(String userId, Integer status);
    
    /**
     * 统计（已支付或者备货订单）采购商品
     * @param list 
     *
     * @return
     * @date 2019年9月16日
     */
    List<MealGoodsModel> getProductList(List<TodayProductVo> list);
}
