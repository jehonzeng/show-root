package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.TableArea;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface TableAreaService {

    /**
     * 获取餐桌区域分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<TableArea> page(PageParam<TableArea> pageParam);

    /**
     * 添加餐桌区域信息
     *
     * @param tableArea
     * @return
     */
    String add(TableArea tableArea);

    /**
     * 修改餐桌区域信息
     *
     * @param tableArea
     * @return
     */
    TableArea modify(TableArea tableArea);

    /**
     * 删除餐桌区域
     *
     * @param areaId
     * @return
     */
    void delete(String areaId);

    /**
     * 获取餐桌区域键值对列表
     *
     * @param storeId
     * @return
     */
    List<Combobox> listCombobox(String storeId);
}
