package com.szhengzhu.service;

import com.szhengzhu.bean.user.PartnerInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

/**
 * @author Jehon Zeng
 */
public interface PartnerService {

    /**
     * 录入合作商信息
     *
     * @date 2019年6月11日 下午6:01:56
     * @param base
     * @return
     */
    PartnerInfo addPartner(PartnerInfo base);

    /**
     * 编辑合作商信息
     *
     * @date 2019年6月11日 下午6:02:37
     * @param base
     * @return
     */
    PartnerInfo modify(PartnerInfo base);

    /**
     * 获取分页信息
     *
     * @date 2019年6月11日 下午6:04:01
     * @param base
     * @return
     */
    PageGrid<PartnerInfo> getPartnerPage(PageParam<PartnerInfo> base);

    /**
     * 删除合作商信息
     *
     * @date 2019年6月18日 下午4:13:34
     * @param markId
     * @return
     */
    void deletePartner(String markId);

    /**
     * 获取合作商下拉列表
     *
     * @date 2019年9月11日 下午2:52:32
     * @return
     */
    List<Combobox> listCombobox();
}
