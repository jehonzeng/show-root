package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.TicketTemplate;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.CommodityMapper;
import com.szhengzhu.mapper.TemplateCommodityMapper;
import com.szhengzhu.mapper.TicketTemplateMapper;
import com.szhengzhu.mapper.UserTicketMapper;
import com.szhengzhu.service.TemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService {

    @Resource
    private TicketTemplateMapper ticketTemplateMapper;

    @Resource
    private TemplateCommodityMapper templateCommodityMapper;

    @Resource
    private CommodityMapper commodityMapper;

    @Resource
    private UserTicketMapper userTicketMapper;

    @Override
    @Transactional
    public void add(TicketTemplate base) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setCreateTime(DateUtil.date());
        base.switchRankIdsString(base.getVipIds());
        ticketTemplateMapper.insertSelective(base);
        if (base.getCommodityIds() != null && base.getCommodityIds().length > 0) {
            templateCommodityMapper.insertBatchCommodity(base.getMarkId(), base.getCommodityIds());
        }
    }

    @Override
    public PageGrid<TicketTemplate> page(PageParam<TicketTemplate> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<TicketTemplate> list = ticketTemplateMapper.selectByExampleSelective(base.getData());
        for (TicketTemplate ticket : list) {
            List<String> commodityIdList = templateCommodityMapper.selectCommodityIds(ticket.getMarkId());
            ticket.setCommodityIds(commodityIdList.toArray(new String[0]));
            ticket.switchVipIdsArray(ticket.getRankIds());
        }
        PageInfo<TicketTemplate> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(TicketTemplate base) {
        base.setModifyTime(DateUtil.date());
        base.switchRankIdsString(base.getVipIds());
        ticketTemplateMapper.updateByPrimaryKeySelective(base);
        String templateId = base.getMarkId();
        String[] commodityIds = base.getCommodityIds();
        templateCommodityMapper.deleteByTemplateId(templateId);
        if (commodityIds != null && commodityIds.length > 0) {
            templateCommodityMapper.insertBatchCommodity(templateId, commodityIds);
        }
    }

    @Override
    public TicketTemplate getInfo(String markId) {
        TicketTemplate base = ticketTemplateMapper.selectByPrimaryKey(markId);
        List<String> commodityList = templateCommodityMapper.selectCommodityIds(base.getMarkId());
        base.setCommodityIds(commodityList.toArray(new String[0]));
        base.switchVipIdsArray(base.getRankIds());
        return base;
    }

    @Override
    @Transactional
    public void delete(String markId) {
        ticketTemplateMapper.updateByTempalteId(markId, -1);
        templateCommodityMapper.deleteByTemplateId(markId);
    }

    @Override
    public void modifyStatus(String[] templateIds, Integer status) {
        ticketTemplateMapper.updateBatchStatus(templateIds, status);
    }

    @Override
    public List<Map<String, String>> combobox() {
        return ticketTemplateMapper.selectCombobox();
    }
}
