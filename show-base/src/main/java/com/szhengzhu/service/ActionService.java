package com.szhengzhu.service;

import com.szhengzhu.bean.base.ActionInfo;
import com.szhengzhu.bean.base.ActionItem;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface ActionService {

    /**
     * 获取事件信息
     *
     * @param code
     * @return
     */
    List<ActionItem> listItemByCode(String code);

    /**
     * 获取事件组分页列表
     *
     * @param page
     * @return
     */
    PageGrid<ActionInfo> pageAction(PageParam<ActionInfo> page);

    /**
     * 添加事件组
     *
     * @param actionInfo
     * @return
     */
    void addAction(ActionInfo actionInfo);

    /**
     * 修改事件组
     *
     * @param actionInfo
     * @return
     */
    void modifyAction(ActionInfo actionInfo);

    /**
     * 获取事件信息分页列表
     *
     * @param page
     * @return
     */
    PageGrid<ActionItem> pageItem(PageParam<ActionItem> page);

    /**
     * 获取事件详细信息
     *
     * @param markId
     * @return
     */
    ActionItem getItemInfo(String markId);

    /**
     * 添加事件信息
     *
     * @param item
     * @return
     */
    void addItem(ActionItem item);

    /**
     * 修改事件信息
     *
     * @param item
     * @return
     */
    void modifyItem(ActionItem item);

    /**
     * 删除事件信息
     *
     * @param markId
     * @return
     */
    void deleteItem(String markId);
}
