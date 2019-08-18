package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.goods.MealJudge;
import com.szhengzhu.bean.vo.MealJudgeVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface MealJudgeService {

    /**
     * 修改评论信息（部分改动）
     * 
     * @date 2019年4月22日 上午11:59:08
     * @param base
     * @return
     */
    Result<?> modifyJudge(MealJudge base);

    /**
     * 获取分页信息
     * 
     * @date 2019年4月22日 上午11:59:10
     * @param base
     * @return
     */
    Result<PageGrid<MealJudgeVo>> getJudgePage(PageParam<MealJudge> base);

    /**
     * 测试添加评论数据
     * 
     * @date 2019年4月22日 下午12:26:50
     * @param base
     * @return
     */
    Result<MealJudge> addJudge(MealJudge base);
    
    /**
     * 获取商城用户评论列表
     * 
     * @date 2019年6月27日 下午4:39:13
     * @param mealId
     * @param userId
     * @return
     */
    Result<List<JudgeBase>> list(String mealId, String userId);

}
