package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.ServerSupport;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.ServerSupportMapper;
import com.szhengzhu.service.ServeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("serveService")
public class ServeServiceImpl implements ServeService {

    @Resource
    private ServerSupportMapper serverSupportMapper;

    @Override
    public ServerSupport saveServer(ServerSupport base) {
        int count = serverSupportMapper.repeatRecords(base.getTheme(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        serverSupportMapper.insertSelective(base);
        return base;
    }

    @Override
    public ServerSupport getServeById(String markId) {
        return serverSupportMapper.selectByPrimaryKey(markId);
    }

    @CheckGoodsChange
    @Override
    public ServerSupport modifyServer(ServerSupport base) {
        String theme = StrUtil.isEmpty(base.getTheme()) ? "" : base.getTheme();
        int count = serverSupportMapper.repeatRecords(theme, base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        serverSupportMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<ServerSupport> getPage(PageParam<ServerSupport> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<ServerSupport> page = new PageInfo<>(
                serverSupportMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public List<Combobox> listServer() {
        return serverSupportMapper.selectServeList();
    }

}
