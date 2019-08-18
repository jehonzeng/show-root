package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.wechat.vo.VoucherBase;
import com.szhengzhu.core.Result;

public interface UserVoucherService {

    /**
     * 获取用户商品代金券
     * 
     * @date 2019年6月12日 下午12:29:13
     * @param userId
     * @return
     */
    Result<List<VoucherBase>> listByUser(String userId);
    
}
