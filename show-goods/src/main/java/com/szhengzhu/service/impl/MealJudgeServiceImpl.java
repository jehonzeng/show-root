package com.szhengzhu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.MealJudge;
import com.szhengzhu.bean.vo.MealJudgeVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.MealJudgeMapper;
import com.szhengzhu.service.MealJudgeService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("mealJudgeService")
public class MealJudgeServiceImpl implements MealJudgeService {

    @Autowired
    private MealJudgeMapper mealJudgeMapper;

    @Override
    public Result<?> modifyJudge(MealJudge base) {
        if(base == null | base.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        mealJudgeMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<MealJudgeVo>> getJudgePage(PageParam<MealJudge> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() +" "+base.getSort());
        PageInfo<MealJudgeVo> page= new PageInfo<>(
                mealJudgeMapper.selectByExampleSelective(base.getData()));
         return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<MealJudge> addJudge(MealJudge base) {
        if(base == null || StringUtils.isEmpty(base.getMealId()))
            return new Result<>(StatusCode._4004);
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        base.setAddTime(TimeUtils.today());
        mealJudgeMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<List<JudgeBase>> list(String mealId, String userId) {
        List<JudgeBase> judges = mealJudgeMapper.selectJudge(userId, mealId, null);
        return new Result<>(judges);
    }

}
