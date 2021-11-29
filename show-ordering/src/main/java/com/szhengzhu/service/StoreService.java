package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.Store;
import com.szhengzhu.bean.ordering.param.StoreParam;
import com.szhengzhu.bean.ordering.vo.StoreMapVo;
import com.szhengzhu.bean.xwechat.vo.StoreModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface StoreService {

    /**
     * 获取门店分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<Store> page(PageParam<StoreParam> pageParam);

    /**
     * 获取门店详细信息
     *
     * @param storeId
     * @return
     */
    Store getInfo(String storeId);

    /**
     * 添加门店
     *
     * @param store
     * @return
     */
    String add(Store store);

    /**
     * 修改门店
     *
     * @param store
     * @return
     */
    void modify(Store store);

    /**
     * 修改门店状态
     *
     * @param storeId
     * @return
     */
    void delete(String storeId);

    /**
     * 获取用户所属门店列表
     *
     * @param employeeId
     * @return
     */
    List<StoreMapVo> listByEmployee(String employeeId);

    /**
     * 小程序获取城市门店列表
     *
     * @param city
     * @return
     */
    List<StoreModel> listLjsStore(String city);
}
