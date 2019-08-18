package com.szhengzhu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.goods.CookFollow;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.CookCertifiedMapper;
import com.szhengzhu.mapper.CookFollowMapper;
import com.szhengzhu.mapper.GoodsInfoMapper;
import com.szhengzhu.service.CookCertifiedService;
import com.szhengzhu.util.IdGenerator;

@Service("cookCertifiedService")
public class CookCertifiedServiceImpl implements CookCertifiedService {

    @Resource
    private CookCertifiedMapper cookCertifiedMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;
    
    @Resource
    private CookFollowMapper cookFollowMapper;

    @Override
    public Result<PageGrid<CookCertified>> page(PageParam<CookCertified> cookPage) {
        PageHelper.startPage(cookPage.getPageIndex(), cookPage.getPageSize());
        PageHelper.orderBy(cookPage.getSidx() + " " + cookPage.getSort());
        PageInfo<CookCertified> pageInfo = new PageInfo<>(
                cookCertifiedMapper.selectByExampleSelective(cookPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<CookCertified> modify(CookCertified cookCertified) {
        cookCertifiedMapper.updateByPrimaryKeySelective(cookCertified);
        return new Result<>(cookCertified);
    }

    @Override
    public Result<CookCertified> add(CookCertified cookCertified) {
        int count = cookCertifiedMapper.repeatRecords(cookCertified.getShortName(), "");
        if (count > 0)
            return new Result<>(StatusCode._4007);
        cookCertified.setMarkId(IdGenerator.getInstance().nexId());
        cookCertified.setCertified(false);
        cookCertified.setCookLevel("CR01");
        cookCertifiedMapper.insertSelective(cookCertified);
        return new Result<>(cookCertified);
    }

    @Override
    public Result<List<Cooker>> listCookerRank(String userId) {
        List<Cooker> cookers = cookCertifiedMapper.selectCookList(userId, 3);
        return new Result<>(cookers);
    }
    
    @Override
    public Result<PageGrid<Cooker>> pageCookerRank(PageParam<String> cookerPage) {
        cookerPage.setSidx("c.quantity");
        PageHelper.startPage(cookerPage.getPageIndex(), cookerPage.getPageSize());
        PageHelper.orderBy(cookerPage.getSidx() + " " + cookerPage.getSort());
        List<Cooker> cookers = cookCertifiedMapper.selectCookList(cookerPage.getData(), null);
        for (int index = 0, size = cookers.size(); index < size; index++) {
            String cookerId = cookers.get(index).getCookerId();
            List<GoodsBase> goodsList = goodsInfoMapper.selectByCooker(cookerId, 4);
            cookers.get(index).setGoodsBases(goodsList);
        }
        PageInfo<Cooker> pageInfo = new PageInfo<>(cookers);
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<?> getCookerDetail(String cookerId, String userId) {
        Cooker cooker = cookCertifiedMapper.selectByUser(cookerId, userId); // goods cooker
        if (cooker == null)
            return new Result<>(StatusCode._4004);
        List<GoodsBase> cookerGoods = goodsInfoMapper.selectByCooker(cookerId, null); // cooker goods
        cooker.setGoodsBases(cookerGoods);
        return new Result<>(cooker);
    }

    @Override
    public Result<?> followOr(CookFollow cookFollow) {
        CookFollow follow = cookFollowMapper.selectByPrimaryKey(cookFollow.getUserId(), cookFollow.getCookId());
        if (follow != null)
            follow = cookFollow;
        else 
            cookFollowMapper.insert(cookFollow);
        cookFollowMapper.updateByPrimaryKey(cookFollow);
        return new Result<>();
    }
}
