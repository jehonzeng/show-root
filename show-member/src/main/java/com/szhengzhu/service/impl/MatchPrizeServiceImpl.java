package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.MatchPrize;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.MatchPrizeMapper;
import com.szhengzhu.service.MatchPrizeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jehon
 */
@Service("matchPrizeService")
public class MatchPrizeServiceImpl implements MatchPrizeService {
    
    @Resource
    private MatchPrizeMapper matchPrizeMapper;

    @Override
    public PageGrid<MatchPrize> page(PageParam<MatchPrize> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy("create_time " + param.getSort());
        PageInfo<MatchPrize> pageInfo = new PageInfo<>(
                matchPrizeMapper.selectByExampleSelective(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void add(MatchPrize matchPrize) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        matchPrize.setMarkId(snowflake.nextIdStr());
        matchPrize.setCreateTime(DateUtil.date());
        matchPrize.setStatus(true);
        matchPrizeMapper.insertSelective(matchPrize);
    }

    @Override
    public void modify(MatchPrize matchPrize) {
        matchPrize.setModifyTime(DateUtil.date());
        matchPrizeMapper.updateByPrimaryKeySelective(matchPrize);
    }
}
