package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.base.ActionInfo;
import com.szhengzhu.bean.base.ActionItem;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.ActionInfoMapper;
import com.szhengzhu.mapper.ActionItemMapper;
import com.szhengzhu.service.ActionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("actionService")
public class ActionServiceImpl implements ActionService {

    @Resource
    private ActionInfoMapper actionInfoMapper;

    @Resource
    private ActionItemMapper actionItemMapper;

    @Override
    public List<ActionItem> listItemByCode(String code) {
        return actionItemMapper.selectByCode(code);
    }

    @Override
    public PageGrid<ActionInfo> pageAction(PageParam<ActionInfo> page) {
        PageMethod.startPage(page.getPageIndex(), page.getPageSize());
        PageMethod.orderBy(page.getSidx() + " " + page.getSort());
        List<ActionInfo> list = actionInfoMapper.selectByExampleSelective(page.getData());
        PageInfo<ActionInfo> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void addAction(ActionInfo actionInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        actionInfo.setMarkId(snowflake.nextIdStr());
        actionInfoMapper.insertSelective(actionInfo);
    }

    @Override
    public void modifyAction(ActionInfo actionInfo) {
        actionInfoMapper.updateByPrimaryKeySelective(actionInfo);
    }

    @Override
    public PageGrid<ActionItem> pageItem(PageParam<ActionItem> page) {
        PageMethod.startPage(page.getPageIndex(), page.getPageSize());
        PageMethod.orderBy(page.getSidx() + " " + page.getSort());
        List<ActionItem> list = actionItemMapper.selectByExampleSelective(page.getData());
        PageInfo<ActionItem> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public ActionItem getItemInfo(String markId) {
        return actionItemMapper.selectByPrimaryKey(markId);
    }

    @Override
    public void addItem(ActionItem item) {
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        item.setMarkId(snowflake.nextIdStr());
        actionItemMapper.insertSelective(item);
    }

    @Override
    public void modifyItem(ActionItem item) {
        actionItemMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public void deleteItem(String markId) {
        actionItemMapper.deleteByPrimaryKey(markId);
    }
}
