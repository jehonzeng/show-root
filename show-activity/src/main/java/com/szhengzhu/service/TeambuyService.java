package com.szhengzhu.service;

import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface TeambuyService {

    /**
     * 获取团购活动分页列表
     * 
     * @date 2019年3月13日 下午2:56:43
     * @param teambuyPage
     * @return
     */
    Result<PageGrid<TeambuyInfo>> pageTeambuy(PageParam<TeambuyInfo> teambuyPage);
    
    /**
     * 添加团购活动
     * 
     * @date 2019年3月13日 下午2:59:19
     * @param teambuyInfo
     * @return
     */
    Result<TeambuyInfo> saveTeambuy(TeambuyInfo teambuyInfo);
    
    /**
     * 修改团购活动
     * 
     * @date 2019年3月13日 下午2:59:55
     * @param teambuyInfo
     * @return
     */
    Result<TeambuyInfo> updateTeambuy(TeambuyInfo teambuyInfo);
    
    /**
     * 获取团购活动详细信息
     * 
     * @date 2019年5月21日 下午5:06:50
     * @param markId
     * @return
     */
    Result<TeambuyInfo> getTeambuyInfo(String markId, String specIds);
}
