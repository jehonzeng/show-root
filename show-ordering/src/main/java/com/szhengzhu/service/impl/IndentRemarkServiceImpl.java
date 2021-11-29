package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.bean.ordering.IndentInfo;
import com.szhengzhu.bean.ordering.IndentRemark;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.code.IndentStatus;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.IndentMapper;
import com.szhengzhu.mapper.IndentRemarkMapper;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.service.IndentRemarkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @since 2021-03-01 10:46:04
 */
@Service("indentRemarkService")
public class IndentRemarkServiceImpl implements IndentRemarkService {
    @Resource
    private Sender sender;

    @Resource
    private IndentMapper indentMapper;

    @Resource
    private IndentRemarkMapper indentRemarkMapper;

    @Resource
    private ShowMemberClient showMemberClient;

    private static final String SORT_KEY = "create_time ";

    @Override
    public IndentRemark queryById(String markId) {
        return indentRemarkMapper.queryById(markId);
    }

    @Override
    public PageGrid<IndentRemark> queryByPage(PageParam<IndentRemark> param) {
        if (ObjectUtil.isNotEmpty(showMemberClient.getMemberInfoByUserId(param.getData().getUserId()))) {
            param.getData().setIsMember(1);
        }
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(SORT_KEY + param.getSort());
        PageInfo<IndentRemark> pageInfo = new PageInfo<>(indentRemarkMapper.query(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public List<IndentRemark> queryByIndentId(IndentRemark indentRemark) {
        return indentRemarkMapper.query(indentRemark);
    }

    @Override
    public Map<String, Object> add(IndentRemark indentRemark) {
        Map<String, Object> map = new HashMap<>();
        int num = RandomUtil.randomInt(30, 66);
        IndentInfo indentInfo = indentMapper.selectById(indentRemark.getIndentId());
//        ShowAssert.checkTrue(!indentInfo.getIndentStatus().equals(IndentStatus.BILL.code), StatusCode._5038);
        String userId = "";
        if (StrUtil.isNotEmpty(indentInfo.getMemberId())) {
            Result<MemberAccount> memberAccountResult = showMemberClient.getMemberInfo(indentInfo.getMemberId());
            userId = memberAccountResult.getData().getUserId();
        }
        if (StrUtil.isNotEmpty(indentInfo.getUserId())) {
            userId = indentInfo.getUserId();
        } else {
            for (String user : indentInfo.getUserList()) {
                if (StrUtil.isNotEmpty(user)) {
                    userId = user;
                }
            }
        }
        ShowAssert.checkTrue(!userId.equals(indentRemark.getUserId()), StatusCode._5035);
        List<IndentRemark> list = indentRemarkMapper.query(IndentRemark.builder().indentId(indentRemark.getIndentId()).build());
        ShowAssert.checkTrue(ObjectUtil.isNotEmpty(list), StatusCode._5036);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        indentRemark.setMarkId(markId);
        indentRemark.setCreateTime(DateUtil.date());
        indentRemark.setStoreId(indentInfo.getStoreId());
        indentRemarkMapper.add(indentRemark);
        sender.giftIntegral(indentRemark.getUserId(), num);
        map.put("integral", num);
        map.put("remarkId", markId);
        return map;
    }

    @Override
    public void modify(IndentRemark indentRemark) {
        indentRemark.setModifyTime(DateUtil.date());
        indentRemarkMapper.modify(indentRemark);
    }
}

