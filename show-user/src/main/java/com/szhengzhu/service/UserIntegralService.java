package com.szhengzhu.service;

import java.util.Map;

import com.szhengzhu.bean.user.UserIntegral;
import com.szhengzhu.bean.vo.IntegralVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface UserIntegralService {

    /**
     * 获取用户积分分页列表
     * 
     * @date 2019年2月26日 上午11:09:16
     * @param integralPage
     * @return
     */
    Result<PageGrid<UserIntegral>> pageIntegral(PageParam<UserIntegral> integralPage);
    
    /**
     * 获取用户积分结算列表
     * 
     * @date 2019年3月4日 下午6:54:20
     * @param integralPage
     * @return
     */
    Result<PageGrid<IntegralVo>> pageIntegralTotal(PageParam<Map<String, String>> integralPage);
    
    /**
     * 添加用户积分
     * 
     * @date 2019年7月26日 上午10:42:12
     * @param integral
     * @return
     */
    Result<?> add(UserIntegral integral);
}
