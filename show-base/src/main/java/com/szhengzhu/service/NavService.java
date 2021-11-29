package com.szhengzhu.service;

import com.szhengzhu.bean.base.NavInfo;
import com.szhengzhu.bean.base.NavItem;
import com.szhengzhu.bean.wechat.vo.NavBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface NavService {

    /**
     * 添加标识信息
     *
     * @date 2019年6月11日 下午2:42:45
     * @param base
     * @return
     */
    NavInfo save(NavInfo base);

    /**
     * 编辑标识信息
     *
     * @date 2019年6月11日 下午2:42:47
     * @param base
     * @return
     */
    NavInfo update(NavInfo base);

    /**
     * 获取标识信息分页
     *
     * @date 2019年6月11日 下午2:42:50
     * @param base
     * @return
     */
    PageGrid<NavInfo> page(PageParam<NavInfo> base);

    /**
     * 添加主题信息
     *
     * @date 2019年6月11日 下午2:42:52
     * @param base
     * @return
     */
    NavItem saveItem(NavItem base);

    /**
     * 获取主题分页列表
     *
     * @date 2019年6月11日 下午2:42:58
     * @param base
     * @return
     */
    PageGrid<NavItem> getItemPage(PageParam<NavItem> base);

    /**
     * 编辑主题信息
     *
     * @date 2019年6月11日 下午2:43:00
     * @param base
     * @return
     */
    NavItem modifyItem(NavItem base);

    /**
     * 根据id获取修改的主题信息
     *
     * @date 2019年6月11日 下午2:43:02
     * @param markId
     * @return
     */
    NavItem getItemById(String markId);

    /**
     * 商城内容列表
     *
     * @date 2019年6月11日 下午2:43:05
     * @return
     */
    List<NavBase> listNavAndItem();

    /**
     * 删除主题信息
     *
     * @date 2019年6月18日 下午3:43:38
     * @param markId
     * @return
     */
    void deleteItem(String markId);

}
