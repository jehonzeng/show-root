package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.goods.PurchaseHistory;
import com.szhengzhu.bean.goods.PurchaseInfo;
import com.szhengzhu.bean.vo.PurchaseFood;
import com.szhengzhu.bean.vo.PurchaseHistoryVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface PurchaseService {
    /**
     * 获取采购订单列表
     * 
     * @date 2019年5月21日 上午11:06:15
     * @param base
     * @return
     */
    Result<PageGrid<PurchaseFood>> getPurchasePage(PageParam<PurchaseInfo> base);

    /**
     * 获取采购记录列表
     * 
     * @date 2019年5月21日 上午11:06:18
     * @param base
     * @return
     */
    Result<PageGrid<PurchaseHistoryVo>> getHistoryPage(PageParam<PurchaseHistory> base);

    /**
     * 指定指派人
     * 
     * @date 2019年5月21日 上午11:06:06
     * @param markId
     * @param userId
     * @return
     */
    Result<?> appoinStaff(String markId, String userId);

    /**
     * 撤销指派人
     * 
     * @date 2019年5月21日 上午11:06:12
     * @param markId
     * @return
     */
    Result<?> revokeStaff(String markId);
    
  
    Result<?> buyFood(PurchaseInfo base);

    /**
     * 生成今日采购单
     * 
     * @date 2019年5月21日 上午11:07:30
     * @return
     */
    Result<?> createPurchaseOrder();

    /**
     * 删除昨天的采购单
     * 
     * @date 2019年5月21日 上午11:07:34
     * @param buyTime
     */
    Result<?> deletePurchaseOrder(String buyTime);

    /**
     * 更新采购单
     * 
     * @date 2019年5月21日 上午11:07:37
     * @return
     */
    Result<?> reflashPurchaseOrder();

    /**
     * 获取不同状态列表
     *
     * @param userId
     * @param status
     * @return
     * @date 2019年7月10日
     */
    Result<List<PurchaseFood>> getListByStatus(String userId, Integer status);
}
