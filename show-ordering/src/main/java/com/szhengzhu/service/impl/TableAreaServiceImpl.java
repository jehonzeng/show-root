package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.TableArea;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.TableAreaMapper;
import com.szhengzhu.service.TableAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("tableAreaService")
public class TableAreaServiceImpl implements TableAreaService {

    @Resource
    private TableAreaMapper tableAreaMapper;

    @Override
    public PageGrid<TableArea> page(PageParam<TableArea> pageParam) {
        String sidx = "mark_id".equals(pageParam.getSidx())? "create_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<TableArea> pageInfo = new PageInfo<>(tableAreaMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public String add(TableArea tableArea) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        tableArea.setMarkId(markId);
        tableArea.setCreateTime(DateUtil.date());
        tableAreaMapper.insertSelective(tableArea);
        return markId;
    }

    @Override
    public TableArea modify(TableArea tableArea) {
        tableArea.setModifyTime(DateUtil.date());
        tableAreaMapper.updateByPrimaryKeySelective(tableArea);
        return tableArea;
    }

    @Override
    public void delete(String areaId) {
        tableAreaMapper.updateStatus(areaId, -1);
    }

    @Override
    public List<Combobox> listCombobox(String storeId) {
        return tableAreaMapper.selectCombobx(storeId);
    }
}
