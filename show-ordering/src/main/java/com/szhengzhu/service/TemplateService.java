package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.TicketTemplate;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;
import java.util.Map;

public interface TemplateService {

    /**
     * 添加券模板
     * @param base
     * @return
     */
    void add(TicketTemplate base);

    /**
     * 券模板分页列表
     * @param base
     * @return
     */
    PageGrid<TicketTemplate> page(PageParam<TicketTemplate> base);

    /**
     * 修改券模板
     * @param base
     * @return
     */
    void modify(TicketTemplate base);

    /**
     * 获取券模板
     * @param markId
     * @return
     */
    TicketTemplate getInfo(String markId);

    /**
     * 删除券模板
     *
     * @param markId
     * @return
     */
    void delete(String markId);

    /**
     * 批量券模板修改状态
     * @param templateIds
     * @param status
     * @return
     */
    void modifyStatus(String[] templateIds, Integer status);

    /**
     * 获取券模板列表
     *
     * @return
     */
    List<Map<String, String>> combobox();
}
