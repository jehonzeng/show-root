package com.szhengzhu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.GoodsJudge;
import com.szhengzhu.bean.vo.GoodsJudgeVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.GoodsJudgeMapper;
import com.szhengzhu.service.GoodsJudgeService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("goodsJudgeService")
public class GoodsJudgeServiceImpl implements GoodsJudgeService {
    
    @Autowired
    private GoodsJudgeMapper goodsJudgeMapper;

    @Override
    public Result<?> modifyJudgeInfo(GoodsJudge base) {
        if(base == null | base.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        goodsJudgeMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<GoodsJudgeVo>> getPage(PageParam<GoodsJudge> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("j."+base.getSidx() +" "+base.getSort());
        PageInfo<GoodsJudgeVo> page= new PageInfo<>(
                goodsJudgeMapper.selectByExampleSelective(base.getData()));
         return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> getJudgeInfosByGoodsId(String goodsId) {
        if(StringUtils.isEmpty(goodsId))
            return new Result<>(StatusCode._4008);
        List<GoodsJudge> goodsJudge = goodsJudgeMapper.selectByGoodsId(goodsId);
        return new Result<>(goodsJudge);
    }

    @Override
    public Result<GoodsJudge> addJudgeInfo(GoodsJudge base) {
        if(base == null || StringUtils.isEmpty(base.getGoodsId()) )
            return new Result<>(StatusCode._4004);
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        base.setAddTime(TimeUtils.today());
        goodsJudgeMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<List<JudgeBase>> list(String goodsId, String userId) {
        List<JudgeBase> judges = goodsJudgeMapper.selectJudge(userId, goodsId, null);
        return new Result<>(judges);
    }

}
