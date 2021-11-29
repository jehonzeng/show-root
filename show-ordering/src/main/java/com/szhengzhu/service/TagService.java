package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.Tag;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface TagService {

    /**
     * 获取图标分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<Tag> page(PageParam<Tag> pageParam);

    /**
     * 添加图标
     *
     * @param tag
     * @return
     */
    void add(Tag tag);

    /**
     * 修改图标
     *
     * @param tag
     * @return
     */
    void modify(Tag tag);

    /**
     * 批量修改图标状态
     *
     * @param tagIds
     * @param status
     * @return
     */
    void modifyStatus(String[] tagIds, int status);

    /**
     * 获取有效图标列表
     *
     * @param storeId
     * @return
     */
    List<Combobox> getCombobox(String storeId);

    /**
     * 批量添加标签商品
     *
     * @param commodityIds
     * @param tagId
     * @return
     */
    void addTagCommodity(String[] commodityIds, String tagId);

    /**
     * 批量删除标签商品
     *
     * @param commodityIds
     * @param tagId
     * @return
     */
    void deleteTagCommodity(String[] commodityIds, String tagId);
}
