package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.base.ScanReply;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.ScanReplyMapper;
import com.szhengzhu.service.ScanReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("scanReplyService")
public class ScanReplyServiceImpl implements ScanReplyService {

    @Resource
    private ScanReplyMapper scanReplyMapper;

    @Override
    public List<ScanReply> listByCode(String code) {
        return scanReplyMapper.selectByCode(code);
    }

    @Override
    public ScanReply getInfo(String markId) {
        return scanReplyMapper.selectByPrimaryKey(markId);
    }

    @Override
    public PageGrid<ScanReply> page(PageParam<ScanReply> page) {
        PageMethod.startPage(page.getPageIndex(), page.getPageSize());
        PageMethod.orderBy(page.getSidx() + " " + page.getSort());
        List<ScanReply> list = scanReplyMapper.selectByExampleSelective(page.getData());
        PageInfo<ScanReply> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void add(ScanReply replyInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        replyInfo.setMarkId(snowflake.nextIdStr());
        replyInfo.setServerStatus(false);
        scanReplyMapper.insertSelective(replyInfo);
    }

    @Override
    public void modify(ScanReply replyInfo) {
        scanReplyMapper.updateByPrimaryKeySelective(replyInfo);
    }

}
