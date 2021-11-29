package com.szhengzhu.service;

import com.szhengzhu.bean.base.PacksInfo;
import com.szhengzhu.bean.base.PacksItem;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.PacksVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface PacksService {

    /**
     * 领取大礼包
     *
     * @param markId
     * @param userId
     * @return
     */
    List<UserCoupon> manualCoupon(String markId, String userId);

    /**
     * 添加礼包信息
     *
     * @param base
     * @return
     */
    PacksInfo addPacks(PacksInfo base);

    /**
     * 修改礼包信息
     * @param base
     * @return
     */
    PacksInfo modifyPacks(PacksInfo base);

    /**
     * @param base
     * @return
     */
    PageGrid<PacksInfo> getPacksPage(PageParam<PacksInfo> base);

    /**
     * @param base
     * @return
     */
    PageGrid<PacksVo> getPacksItemPage(PageParam<PacksItem> base);

    /**
     * @param base
     * @return
     */
    void batchPacksTemplate(BatchVo base);

    /**
     * @param base
     * @return
     */
    PacksItem updatePacksTemplate(PacksItem base);

    /**
     * @param markId
     * @return
     */
    PacksInfo getPacksInfo(String markId);

}
