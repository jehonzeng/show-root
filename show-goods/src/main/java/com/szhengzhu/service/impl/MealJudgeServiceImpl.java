package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.MealJudge;
import com.szhengzhu.bean.vo.MealJudgeVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.MealJudgeMapper;
import com.szhengzhu.service.MealJudgeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("mealJudgeService")
public class MealJudgeServiceImpl implements MealJudgeService {

    @Resource
    private MealJudgeMapper mealJudgeMapper;

    @Override
    public MealJudge modifyJudge(MealJudge base) {
        mealJudgeMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<MealJudgeVo> getJudgePage(PageParam<MealJudge> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() +" "+base.getSort());
        PageInfo<MealJudgeVo> page= new PageInfo<>(
                mealJudgeMapper.selectByExampleSelective(base.getData()));
         return new PageGrid<>(page);
    }

    @Override
    public MealJudge addJudge(MealJudge base) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        base.setAddTime(DateUtil.date());
        mealJudgeMapper.insertSelective(base);
        return base;
    }

    @Override
    public List<JudgeBase> list(String mealId, String userId) {
        return mealJudgeMapper.selectJudge(userId, mealId, null);
    }
}
