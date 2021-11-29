package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.Specs;
import com.szhengzhu.bean.ordering.SpecsItem;
import com.szhengzhu.bean.ordering.vo.SpecsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;
import java.util.Map;

public interface SpecsService {

    /**
     * 获取规格主列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<Specs> page(PageParam<Specs> pageParam);

    /**
     * 添加规格主信息
     *
     * @param specs
     * @return
     */
    void add(Specs specs);

    /**
     * 修改规格主信息
     *
     * @param specs
     * @return
     */
    void modify(Specs specs);

    /**
     * 批量修改规格主信息状态
     *
     * @param specsIds
     * @param status
     * @return
     */
    void modifyStatus(String[] specsIds, int status);

    /**
     * 获取已启用规格主信息
     *
     * @param storeId
     * @return
     */
    List<SpecsVo> getCombobox(String storeId);

    /**
     * 获取规格列表
     *
     * @param name
     * @return
     */
    List<Map<String, String>> list(String name);

    /**
     * 获取规格子信息分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<SpecsItem> pageItem(PageParam<SpecsItem> pageParam);

    /**
     * 添加规格子信息
     *
     * @param specsItem
     * @return
     */
    void addItem(SpecsItem specsItem);

    /**
     * 修改规格子信息
     *
     * @param specsItem
     * @return
     */
    void modifyItem(SpecsItem specsItem);

    /**
     * 批量修改规格子信息状态
     *
     * @param itemIds
     * @param status
     * @return
     */
    void modifyItemStatus(String[] itemIds, int status);

    /**
     * 根据分类获取规格列表
     *
     * @param cateIds
     * @return
     */
    List<SpecsVo> getComboboxByCateId(String[] cateIds, String storeId);
}
