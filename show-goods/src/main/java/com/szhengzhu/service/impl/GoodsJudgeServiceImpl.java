package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.GoodsJudge;
import com.szhengzhu.bean.vo.GoodsJudgeVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.GoodsJudgeMapper;
import com.szhengzhu.service.GoodsJudgeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("goodsJudgeService")
public class GoodsJudgeServiceImpl implements GoodsJudgeService {

    @Resource
    private GoodsJudgeMapper goodsJudgeMapper;

    @Override
    public GoodsJudge modifyJudgeInfo(GoodsJudge base) {
        goodsJudgeMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<GoodsJudgeVo> getPage(PageParam<GoodsJudge> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("j."+base.getSidx() +" "+base.getSort());
        PageInfo<GoodsJudgeVo> page= new PageInfo<>(
                goodsJudgeMapper.selectByExampleSelective(base.getData()));
         return new PageGrid<>(page);
    }

    @Override
    public List<GoodsJudge> getJudgeInfosByGoodsId(String goodsId) {
        return goodsJudgeMapper.selectByGoodsId(goodsId);
    }

    @Override
    public GoodsJudge addJudgeInfo(GoodsJudge base) {
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        base.setAddTime(DateUtil.date());
        goodsJudgeMapper.insertSelective(base);
        return base;
    }

    @Override
    public List<JudgeBase> list(String goodsId, String userId) {
        return goodsJudgeMapper.selectJudge(userId, goodsId, null);
    }

}
