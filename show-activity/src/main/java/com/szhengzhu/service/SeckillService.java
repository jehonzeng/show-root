package com.szhengzhu.service;

import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.wechat.vo.SeckillDetail;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.Map;

public interface SeckillService {

    /**
     * 获取秒杀活动分页列表
     * 
     * @date 2019年3月13日 下午3:01:42
     * @param seckillPage
     * @return
     */
    PageGrid<SeckillInfo> pageSeckill(PageParam<SeckillInfo> seckillPage);
    
    /**
     * 添加秒杀活动
     * 
     * @date 2019年3月13日 下午3:02:31
     * @param seckillInfo
     * @return
     */
    SeckillInfo addInfo(SeckillInfo seckillInfo);
    
    /**
     * 修改秒杀活动
     * 
     * @date 2019年3月13日 下午3:03:01
     * @param seckillInfo
     * @return
     */
    void modifyInfo(SeckillInfo seckillInfo);
    
    /**
     * 获取秒杀活动详细信息
     * 
     * @date 2019年5月21日 下午5:03:16
     * @param markId
     * @return
     */
    SeckillInfo getInfo(String markId);
    
    /**
     * 商城获取秒杀列表
     * 
     * @date 2019年10月9日 下午6:07:44
     * @param pageParam
     * @return
     */
    PageGrid<Map<String, Object>> pageInfo(PageParam<String> pageParam);
    
    /**
     * 获取秒杀信息
     * 
     * @date 2019年10月9日 下午6:08:19
     * @param markId
     * @return
     */
    SeckillDetail getDetail(String markId);
    
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
