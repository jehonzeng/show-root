package com.szhengzhu.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.user.UserIntegral;
import com.szhengzhu.bean.vo.IntegralVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.mapper.UserIntegralMapper;
import com.szhengzhu.service.UserIntegralService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.TimeUtils;

@Service("userIntegralService")
public class UserIntegralServiceImpl implements UserIntegralService {

    @Resource
    private UserIntegralMapper userIntegralMapper;

    @Override
    public Result<PageGrid<UserIntegral>> pageIntegral(PageParam<UserIntegral> integralPage) {
        PageHelper.startPage(integralPage.getPageIndex(), integralPage.getPageSize());
        PageHelper.orderBy(integralPage.getSidx() + " " + integralPage.getSort());
        PageInfo<UserIntegral> pageInfo = new PageInfo<>(
                userIntegralMapper.selectByExampleSelective(integralPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<PageGrid<IntegralVo>> pageIntegralTotal(PageParam<Map<String, String>> integralPage) {
        String nickName = "";
        String phone = "";
        if (integralPage.getData() != null) {
            nickName = integralPage.getData().get("nickName");
            phone = integralPage.getData().get("phone");
        }
        PageHelper.startPage(integralPage.getPageIndex(), integralPage.getPageSize());
        PageHelper.orderBy(integralPage.getSidx() + " " + integralPage.getSort());
        PageInfo<IntegralVo> pageInfo = new PageInfo<>(userIntegralMapper.selectUserTotal(nickName, phone));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<?> add(UserIntegral integral) {
        integral.setMarkId(IdGenerator.getInstance().nexId());
        integral.setCreateTime(TimeUtils.today());
        userIntegralMapper.insertSelective(integral);
        return new Result<>();
    }

}
