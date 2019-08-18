package com.szhengzhu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.ServerSupport;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.ServerSupportMapper;
import com.szhengzhu.service.ServeService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("serveService")
public class ServeServiceImpl implements ServeService {

    @Autowired
    private ServerSupportMapper serverSupportMapper;

    @Override
    public Result<?> saveServer(ServerSupport base) {
        if (base == null || StringUtils.isEmpty(base.getTheme())) {
            return new Result<>(StatusCode._4004);
        }
        int count = serverSupportMapper.repeatRecords(base.getTheme(), "");
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        serverSupportMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> getServeById(String markId) {
        return new Result<>(serverSupportMapper.selectByPrimaryKey(markId));
    }

    @Override
    public Result<?> modifyServer(ServerSupport base) {
        if (base == null || base.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        String theme = base.getTheme() == null ? "" : base.getTheme();
        int count = serverSupportMapper.repeatRecords(theme, base.getMarkId());
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        serverSupportMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<?>> getPage(PageParam<ServerSupport> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<ServerSupport> page = new PageInfo<>(
                serverSupportMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> listServer() {
        List<Combobox> list = serverSupportMapper.selectServeList();
        return new Result<>(list);
    }

}
