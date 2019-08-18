package com.szhengzhu.service;

import com.szhengzhu.bean.user.PartnerInfo;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface PartnerService {

    /**
     * 录入合作商信息
     * 
     * @date 2019年6月11日 下午6:01:56
     * @param base
     * @return
     */
    Result<?> addPartner(PartnerInfo base);

    /**
     * 编辑合作商信息
     * 
     * @date 2019年6月11日 下午6:02:37
     * @param base
     * @return
     */
    Result<?> editPartner(PartnerInfo base);

    /**
     * 获取分页信息
     * 
     * @date 2019年6月11日 下午6:04:01
     * @param base
     * @return
     */
    Result<?> getPartnerPage(PageParam<PartnerInfo> base);

    /**
     * 删除合作商信息
     * 
     * @date 2019年6月18日 下午4:13:34
     * @param markId
     * @return
     */
    Result<?> deletePartner(String markId);
}
