package com.szhengzhu.service;

import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.bean.wechat.vo.TeambuyDetail;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.Map;

public interface TeambuyService {

    /**
     * 获取团购活动分页列表
     * 
     * @date 2019年3月13日 下午2:56:43
     * @param teambuyPage
     * @return
     */
    PageGrid<TeambuyInfo> pageTeambuy(PageParam<TeambuyInfo> teambuyPage);
    
    /**
     * 添加团购活动
     * 
     * @date 2019年3月13日 下午2:59:19
     * @param teambuyInfo
     * @return
     */
    TeambuyInfo addInfo(TeambuyInfo teambuyInfo);
    
    /**
     * 修改团购活动
     * 
     * @date 2019年3月13日 下午2:59:55
     * @param teambuyInfo
     * @return
     */
    void modifyInfo(TeambuyInfo teambuyInfo);
    
    /**
     * 获取团购活动详细信息
     * 
     * @date 2019年5月21日 下午5:06:50
     * @param markId
     * @return
     */
    TeambuyInfo getInfo(String markId);
    
    /**
     * 商城获取团购列表
     * 
     * @date 2019年10月9日 下午3:13:22
     * @param pageParam
     * @return
     */
    PageGrid<Map<String, Object>> pageForeList(PageParam<String> pageParam);
    
    /**
     * 获取团购详细信息
     * 
     * @date 2019年10月9日 下午3:45:12
     * @param markId
     * @return
     */
    TeambuyDetail getDetail(String markId);
    
    /**
     * 减库存
     * 
     * @date 2019年10月22日 上午9:49:41
     * @param markId
     */
    void subStock(String markId);
    
    /**
     * 加库存
     * 
     * @date 2019年10月22日 上午9:50:12
     * @param markId
     */
    void addStock(String markId);
}
