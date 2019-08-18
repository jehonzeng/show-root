package com.szhengzhu.service;

import com.szhengzhu.bean.goods.ServerSupport;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface ServeService {

    Result<?> saveServer(ServerSupport base);

    Result<?> modifyServer(ServerSupport base);

    Result<PageGrid<?>> getPage(PageParam<ServerSupport> base);
    
    Result<?> listServer();

    Result<?> getServeById(String markId);

}
