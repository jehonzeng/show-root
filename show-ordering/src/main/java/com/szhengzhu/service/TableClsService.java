package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.TableCls;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface TableClsService {

    /**
     * 获取餐桌类型分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<TableCls> page(PageParam<TableCls> pageParam);

    /**
     * 添加餐桌类型信息
     *
     * @param tableCls
     * @return
     */
    String add(TableCls tableCls);

    /**
     * 修改餐桌类型信息
     *
     * @param tableCls
     * @return
     */
    void modify(TableCls tableCls);

    /**
     * 删除餐桌类型
     *
     * @param clsId
     * @return
     */
    void delete(String clsId);

    /**
     * 获取餐桌类型键值对列表
     *
     * @param storeId
     * @return
     */
    List<Combobox> listCombobox(String storeId);
}
