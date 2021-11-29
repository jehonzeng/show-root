package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.goods.CookFollow;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.CookCertifiedMapper;
import com.szhengzhu.mapper.CookFollowMapper;
import com.szhengzhu.mapper.GoodsInfoMapper;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.CookCertifiedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service("cookCertifiedService")
public class CookCertifiedServiceImpl implements CookCertifiedService {

    @Resource
    private CookCertifiedMapper cookCertifiedMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private CookFollowMapper cookFollowMapper;

    @Resource
    private Redis redis;

    private final ConcurrentHashMap<String, Boolean> lockIdsMap = new ConcurrentHashMap<>();

    @Override
    public PageGrid<CookCertified> page(PageParam<CookCertified> cookPage) {
        PageMethod.startPage(cookPage.getPageIndex(), cookPage.getPageSize());
        PageMethod.orderBy(cookPage.getSidx() + " " + cookPage.getSort());
        PageInfo<CookCertified> pageInfo = new PageInfo<>(
                cookCertifiedMapper.selectByExampleSelective(cookPage.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public CookCertified modify(CookCertified cookCertified) {
        cookCertifiedMapper.updateByPrimaryKeySelective(cookCertified);
        cookerChange(cookCertified.getMarkId());
        return cookCertified;
    }

    private void cookerChange(String cookerId) {
        redis.del("goods:cooker:detail:" + cookerId);
    }

    @Override
    public CookCertified add(CookCertified cookCertified) {
        int count = cookCertifiedMapper.repeatRecords(cookCertified.getShortName(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        String markId = snowflake.nextIdStr();
        cookCertified.setMarkId(markId);
        cookCertified.setCertified(false);
        cookCertified.setCookLevel("CR01");
        cookCertifiedMapper.insertSelective(cookCertified);
        cookerChange(cookCertified.getMarkId());
        return cookCertified;
    }

    @Override
    public List<Cooker> listCookerRank(String userId) {
        return cookCertifiedMapper.selectCookList(userId, 3);
    }

    @Override
    public PageGrid<Cooker> pageCookerRank(PageParam<String> cookerPage) {
        cookerPage.setSidx("c.quantity");
        PageMethod.startPage(cookerPage.getPageIndex(), cookerPage.getPageSize());
        PageMethod.orderBy(cookerPage.getSidx() + " " + cookerPage.getSort());
        List<Cooker> cookers = cookCertifiedMapper.selectCookList(cookerPage.getData(), null);
        for (Cooker cooker : cookers) {
            List<GoodsBase> goodsList = goodsInfoMapper.selectByCooker(cooker.getCookerId(), 4);
            cooker.setGoodsBases(goodsList);
        }
        PageInfo<Cooker> pageInfo = new PageInfo<>(cookers);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public Cooker getCookerDetail(String cookerId, String userId) {
        try {
            if (!StrUtil.isEmpty(userId)) {
                // goods cooker
                Cooker cooker = cookCertifiedMapper.selectByUser(cookerId, userId);
                // cooker goods
                List<GoodsBase> cookerGoods = goodsInfoMapper.selectByCooker(cookerId, null);
                cooker.setGoodsBases(cookerGoods);
                return cooker;
            }
            Cooker cooker = (Cooker) redis.get("goods:cooker:detail:" + cookerId);
            if (ObjectUtil.isNotNull(cooker)) { return cooker; }
            boolean lock = lockIdsMap.put(cookerId, true) == null;
            ShowAssert.checkTrue(!lock, StatusCode._5010);
            cooker = (Cooker) redis.get("goods:cooker:detail:" + cookerId);
            if (ObjectUtil.isNotNull(cooker)) { return cooker; }
            // goods cooker
            cooker = cookCertifiedMapper.selectByUser(cookerId, null);
            // cooker goods
            List<GoodsBase> cookerGoods = goodsInfoMapper.selectByCooker(cookerId, null);
            cooker.setGoodsBases(cookerGoods);
            redis.set("goods:cooker:detail:" + cookerId, cooker, 4 * 60 * 60L);
            return cooker;
        } finally {
            lockIdsMap.remove(cookerId);
        }
    }

    @Override
    public void followOr(CookFollow cookFollow) {
        CookFollow follow = cookFollowMapper.selectByPrimaryKey(cookFollow.getUserId(), cookFollow.getCookId());
        if (ObjectUtil.isNull(follow)) {
            cookFollowMapper.insert(cookFollow);
        } else {
            cookFollowMapper.updateByPrimaryKey(cookFollow);
        }
    }

    @Override
    public List<Combobox> listCombobox() {
        return cookCertifiedMapper.selectCombobox();
    }

    @Override
    public CookCertified getCookerById(String markId) {
        return cookCertifiedMapper.selectCookeById(markId);
    }
}
