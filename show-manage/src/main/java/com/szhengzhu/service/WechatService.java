package com.szhengzhu.service;

import com.szhengzhu.core.Result;

public interface WechatService {

    /**
     * 更新公众号菜单
     * 
     * @date 2019年5月7日 下午3:07:08
     * @param menuJson
     * @return
     */
    Result<?> refreshMenu(String menuJson);
}
