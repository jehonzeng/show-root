package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.goods.GoodsJudge;
import com.szhengzhu.bean.vo.GoodsJudgeVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface GoodsJudgeService {

    /**
     * 部分修改评论信息
     * 
     * @date 2019年3月15日 下午2:31:55
     * @param base
     * @return
     */
    Result<?> modifyJudgeInfo(GoodsJudge base);

    /**
     * 获取评论信息分页
     * 
     * @date 2019年3月15日 下午2:41:11
     * @param base
     * @return
     */
    Result<PageGrid<GoodsJudgeVo>> getPage(PageParam<GoodsJudge> base);

    /**
     * 根据库存商品id获取商品的评论信息列表
     * 
     * @date 2019年3月25日 下午5:28:08
     * @param goodsId
     * @return
     */
    Result<?> getJudgeInfosByGoodsId(String goodsId);

    
    /**
     * 添加评论信息（作为测试用）
     * 
     * @date 2019年3月15日 下午2:31:55
     * @param base
     * @return
     */
    Result<GoodsJudge> addJudgeInfo(GoodsJudge base);
    
    /**
     * 商城获取商品评论列表
     * 
     * @date 2019年6月27日 下午4:43:19
     * @param goodsId
     * @param userId
     * @return
     */
    Result<List<JudgeBase>> list(String goodsId, String userId);
}
