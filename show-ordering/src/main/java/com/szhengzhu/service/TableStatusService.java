package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.TableStatus;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface TableStatusService {

    /**
     * 获取餐桌状态分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<TableStatus> page(PageParam<TableStatus> pageParam);

    /**
     * 添加餐桌状态
     *
     * @param tableStatus
     * @return
     */
    TableStatus add(TableStatus tableStatus);

    /**
     * 修改餐桌状态
     *
     * @param tableStatus
     * @return
     */
    void modify(TableStatus tableStatus);

    /**
     * 删除餐桌状态
     *
     * @param statusId
     * @return
     */
    void delete(String statusId);

    /**
     * 获取餐桌状态键值对列表
     *
     * @param storeId
     * @return
     */
    List<Combobox> listCombobox(String storeId);
}
