package com.szhengzhu.service;

import com.szhengzhu.bean.goods.AccessoryInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

public interface AccessoryService {

    /**
     * 添加附属品
     *
     * @date 2019年4月26日 下午5:14:21
     * @param base
     * @return
     */
    AccessoryInfo addAccessory(AccessoryInfo base);

    /**
     * 获取附属品列表
     *
     * @date 2019年4月26日 下午5:14:23
     * @param base
     * @return
     */
    PageGrid<AccessoryInfo> getAccessoryPage(PageParam<AccessoryInfo> base);

    /**
     * 编辑附属品
     *
     * @date 2019年4月26日 下午5:14:26
     * @param base
     * @return
     */
    AccessoryInfo editAccessory(AccessoryInfo base);

    /**
     * 根据id获取附属品信息
     *
     * @date 2019年4月26日 下午5:17:28
     * @param markId
     * @return
     */
    AccessoryInfo selectAccessoryById(String markId);
}
