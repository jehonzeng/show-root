package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.TableCls;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.TableClsMapper;
import com.szhengzhu.service.TableClsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("tableClsService")
public class TableClsServiceImpl implements TableClsService {

    @Resource
    private TableClsMapper tableClsMapper;

    @Override
    public PageGrid<TableCls> page(PageParam<TableCls> pageParam) {
        String sidx = "mark_id".equals(pageParam.getSidx())? "create_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<TableCls> pageInfo = new PageInfo<>(tableClsMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public String add(TableCls tableCls) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        tableCls.setMarkId(markId);
        tableCls.setCreateTime(DateUtil.date());
        tableClsMapper.insertSelective(tableCls);
        return markId;
    }

    @Override
    public void modify(TableCls tableCls) {
        tableCls.setModifyTime(DateUtil.date());
        tableClsMapper.updateByPrimaryKeySelective(tableCls);
    }

    @Override
    public void delete(String clsId) {
        tableClsMapper.updateStatus(clsId, -1);
    }

    @Override
    public List<Combobox> listCombobox(String storeId) {
        return tableClsMapper.selectCombobox(storeId);
    }
}
