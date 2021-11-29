package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.GoodsJudge;
import com.szhengzhu.bean.vo.GoodsJudgeVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.provider.JudgeProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author Administrator
 */
public interface GoodsJudgeMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(GoodsJudge record);

    int insertSelective(GoodsJudge record);

    GoodsJudge selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(GoodsJudge record);

    int updateByPrimaryKey(GoodsJudge record);
    
    List<GoodsJudgeVo> selectByExampleSelective(GoodsJudge record);

    @SelectProvider(type = JudgeProvider.class, method = "selectByGoodsId")
    List<GoodsJudge> selectByGoodsId(@Param("goodsId")String goodsId);
    
    List<JudgeBase> selectJudge(@Param("userId") String userId, @Param("goodsId") String goodsId, @Param("number") Integer number);
}