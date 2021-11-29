package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.base.ReplyInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.ReplyInfoMapper;
import com.szhengzhu.service.ReplyInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("replyInfoService")
public class ReplyInfoServiceImpl implements ReplyInfoService {

    @Resource
    private ReplyInfoMapper replyInfoMapper;

    @Override
    public ReplyInfo getInfoByMsg(String msg) {
        return replyInfoMapper.selectByMsg(msg);
    }

    @Override
    public ReplyInfo getInfo(String markId) {
        return replyInfoMapper.selectByPrimaryKey(markId);
    }

    @Override
    public PageGrid<ReplyInfo> page(PageParam<ReplyInfo> page) {
        PageMethod.startPage(page.getPageIndex(), page.getPageSize());
        PageMethod.orderBy(page.getSidx() + " " + page.getSort());
        List<ReplyInfo> list = replyInfoMapper.selectByExampleSelective(page.getData());
        PageInfo<ReplyInfo> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void add(ReplyInfo replyInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        replyInfo.setMarkId(snowflake.nextIdStr());
        replyInfo.setServerStatus(false);
        replyInfoMapper.insertSelective(replyInfo);
    }

    @Override
    public void modify(ReplyInfo replyInfo) {
        replyInfoMapper.updateByPrimaryKeySelective(replyInfo);
    }
}
