package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.TableStatus;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.TableStatusMapper;
import com.szhengzhu.service.TableStatusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("tableStatusService")
public class TableStatusServiceImpl implements TableStatusService {

    @Resource
    private TableStatusMapper tableStatusMapper;

    @Override
    public PageGrid<TableStatus> page(PageParam<TableStatus> pageParam) {
        String sidx = "mark_id".equals(pageParam.getSidx())? "sort " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<TableStatus> pageInfo = new PageInfo<>(tableStatusMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public TableStatus add(TableStatus tableStatus) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        tableStatus.setMarkId(markId);
        tableStatus.setCreateTime(DateUtil.date());
        tableStatusMapper.insertSelective(tableStatus);
        return tableStatus;
    }

    @Override
    public void modify(TableStatus tableStatus) {
        tableStatus.setModifyTime(DateUtil.date());
        tableStatusMapper.updateByPrimaryKeySelective(tableStatus);
    }

    @Override
    public void delete(String statusId) {
        tableStatusMapper.updateStatus(statusId, -1);
    }

    @Override
    public List<Combobox> listCombobox(String storeId) {
        return tableStatusMapper.selectCombobox(storeId);
    }

}
