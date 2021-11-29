package com.szhengzhu.service;

import com.szhengzhu.bean.goods.ServerSupport;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface ServeService {

    ServerSupport saveServer(ServerSupport base);

    ServerSupport modifyServer(ServerSupport base);

    PageGrid<ServerSupport> getPage(PageParam<ServerSupport> base);

    List<Combobox> listServer();

    ServerSupport getServeById(String markId);
}
