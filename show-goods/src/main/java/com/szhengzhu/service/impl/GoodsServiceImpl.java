package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckStatus;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.GoodsServer;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.GoodsDetail;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.code.GoodsStatus;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private GoodsServerMapper goodsServerMapper;

    @Resource
    private GoodsImageMapper goodsImageMapper;

    @Resource
    private GoodsJudgeMapper goodsJudgeMapper;

    @Resource
    private CookCertifiedMapper cookCertifiedMapper;

    @Resource
    private ServerSupportMapper serverSupportMapper;

    @Resource
    private TypeSpecMapper typeSpecMapper;

    @Resource
    private GoodsVoucherMapper goodsVoucherMapper;

    @Resource
    private MealInfoMapper mealInfoMapper;

    @Autowired
    private ExecutorService executor;

    @Resource
    private Redis redis;

    private final ConcurrentHashMap<String, Boolean> lockIdMap = new ConcurrentHashMap<>();

    @CheckStatus
    @Override
    public GoodsInfo addGoods(GoodsInfo base) {
        int count = goodsInfoMapper.repeatRecords(base.getGoodsName(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(GoodsStatus.NOT_ONLINE);
        base.setCreateTime(DateUtil.date());
        goodsInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    @CheckStatus
    @Transactional
    public GoodsInfo modifyGoods(GoodsInfo base) {
        String goodsName = StrUtil.isEmpty(base.getGoodsName()) ? "" : base.getGoodsName();
        int count = goodsInfoMapper.repeatRecords(goodsName, base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        GoodsInfo preGoods = goodsInfoMapper.selectByPrimaryKey(base.getMarkId());
        Date preUpperTime = preGoods.getPreUpperTime();
        Date preDownTime = preGoods.getPreDownTime();
        if ((ObjectUtil.isNotNull(base.getPreUpperTime()) && base.getPreUpperTime().getTime() > DateTime.now().getTime()) ||
                (ObjectUtil.isNotNull(base.getPreUpperTime()) && base.getPreUpperTime().getTime() < DateTime.now().getTime())) {
            base.setServerStatus(GoodsStatus.NOT_ONLINE);
        }
        if (ObjectUtil.isNull(base.getPreDownTime()) && ObjectUtil.isNotNull(base.getPreUpperTime())
                && ObjectUtil.isNotNull(preUpperTime) && ObjectUtil.isNotNull(preDownTime)) {
            // 时间置空
            goodsInfoMapper.clearTime(base.getMarkId());
            // 设置为待上架
            base.setServerStatus(GoodsStatus.NOT_ONLINE);
        }
        base.setModifyTime(DateUtil.date());
        goodsInfoMapper.updateByPrimaryKeySelective(base);
        goodsChange(base.getMarkId());
        return base;
    }

    private void goodsChange(String goodsId) {
        redis.del("goods:goods:recommend");
        redis.del("goods:goods:detail:" + goodsId);
    }

    @Override
    @CheckStatus
    @Transactional
    public GoodsInfo modifyGoodsStatus(GoodsInfo base) {
        GoodsInfo previousGoods = goodsInfoMapper.selectByPrimaryKey(base.getMarkId());
        // 已做预处理设置时间无法手动更改状态
        ShowAssert.checkTrue(ObjectUtil.isNotNull(previousGoods.getPreUpperTime()), StatusCode._5011);
        ShowAssert.checkTrue(ObjectUtil.isNotNull(previousGoods.getPreDownTime()), StatusCode._5011);
        String oldStatus = previousGoods.getServerStatus();
        String newStatus = base.getServerStatus();
        // 按一定顺序改变状态
        if (GoodsStatus.NOT_ONLINE.equals(oldStatus) && GoodsStatus.ONLINE.equals(newStatus)) {
            base.setUpperTime(DateUtil.date());
        } else if (GoodsStatus.ONLINE.equals(oldStatus) && GoodsStatus.OFFLINE.equals(newStatus)) {
            // 下架操作处理关联的商品券和套餐状态
            goodsVoucherMapper.updateStatusByGoods(base.getMarkId());
            mealInfoMapper.updateStatusByGoods(base.getMarkId());
            base.setDownTime(DateUtil.date());
        }
        base.setModifyTime(DateUtil.date());
        goodsInfoMapper.updateByPrimaryKeySelective(base);
        goodsChange(base.getMarkId());
        return base;
    }

    @Override
    public GoodsInfo getGoodsInfo(String markId) {
        return goodsInfoMapper.selectByPrimaryKey(markId);
    }

    @CheckStatus
    @Override
    public PageGrid<GoodsVo> getPage(PageParam<GoodsInfo> base) {
        if ("goods_name".equals(base.getSidx())) {
            base.setSidx("convert(goods_name using gbk)");
        }
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVo> page = new PageInfo<>(goodsInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public List<Combobox> listCombobox() {
        return goodsInfoMapper.selectCombobox();
    }

    @Override
    public List<Combobox> getListNotColumn() {
        return goodsInfoMapper.selectListNotInColumn();
    }

    @Override
    public List<Combobox> getListNotLabel(String labelId) {
        return goodsInfoMapper.selectListNotInLabel(labelId);
    }

    @Override
    public void addBatchServer(BatchVo base) {
        List<GoodsServer> list = new LinkedList<>();
        GoodsServer data;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (String serverId : base.getIds()) {
            data = GoodsServer.builder().markId(snowflake.nextIdStr()).goodsId(base.getCommonId()).serverId(serverId).build();
            list.add(data);
            goodsChange(base.getCommonId());
        }
        if (!list.isEmpty()) {
            goodsServerMapper.insertBatch(list);
        }
    }

    @Override
    public void moveBatchServer(BatchVo base) {
        goodsServerMapper.deletetBatch(base);
    }

    @Override
    public List<String> listInnerServer(String goodsId) {
        return goodsServerMapper.selectListByGoodsId(goodsId);
    }

    @Override
    public List<Combobox> getListNotSpecial() {
        return goodsInfoMapper.selectListNotSpecial();
    }

    @Override
    public List<Combobox> getListNotIcon() {
        return goodsInfoMapper.selectListNotIcon();
    }

    @Override
    public PageGrid<GoodsVo> getPageByColumn(PageParam<GoodsInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("g." + base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVo> page = new PageInfo<>(goodsInfoMapper.selectByExampleSelectiveNotColumn(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public PageGrid<GoodsVo> getPageByLabel(PageParam<GoodsInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("g." + base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVo> page = new PageInfo<>(goodsInfoMapper.selectByExampleSelectiveNotLabel(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public PageGrid<GoodsVo> getPageByIcon(PageParam<GoodsInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("g." + base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVo> page = new PageInfo<>(goodsInfoMapper.selectByExampleSelectiveNotIcon(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public PageGrid<GoodsVo> getPageBySpecial(PageParam<GoodsInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("g." + base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVo> page = new PageInfo<>(goodsInfoMapper.selectByExampleSelectiveNotSpecial(base.getData()));
        return new PageGrid<>(page);
    }

    @CheckStatus
    @SuppressWarnings("unchecked")
    @Override
    public List<GoodsBase> listRecommend(String userId) {
        List<GoodsBase> goodsList;
        if (!StrUtil.isEmpty(userId)) {
            // log in
            goodsList = goodsInfoMapper.selectRecommenByUser(userId);
            return goodsList;
        }
        goodsList = (List<GoodsBase>) redis.get("goods:goods:recommend");
        if (goodsList != null) {
            return goodsList;
        }
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            // 防止第一个线程之后的线程获取到锁之后又连接数据库查询，此时缓存中已有数据
            goodsList = (List<GoodsBase>) redis.get("goods:goods:recommend");
            if (goodsList == null) {
                // not log in
                goodsList = goodsInfoMapper.selectRecommend();
                redis.set("goods:goods:recommend", goodsList, 5 * 60 * 60L);
            }
        } finally {
            lock.unlock();
        }
        return goodsList;
    }

    @CheckStatus
    @Override
    public GoodsDetail getGoodsDetail(String goodsId, String userId) {
        GoodsDetail goods;
        String cacheKey = "goods:goods:detail:" + goodsId;
        try {
            Object goodsObj = redis.get(cacheKey);
            if (ObjectUtil.isNotNull(goodsObj)) {
                goods = JSON.parseObject(JSON.toJSONString(goodsObj), GoodsDetail.class);
                return goods;
            }
            // 防止数据库因请求量过大雪崩而做的拦截
            boolean lock = lockIdMap.put(goodsId, true) == null;
            ShowAssert.checkTrue(!lock, StatusCode._5010);
            goodsObj = redis.get(cacheKey);
            if (goodsObj != null) {
                goods = JSON.parseObject(JSON.toJSONString(goodsObj), GoodsDetail.class);
                return goods;
            }
            // goods info
            goods = goodsInfoMapper.selectById(goodsId);
            goods = getGoodsAttribute(goods, userId);
            redis.set(cacheKey, goods, 2 * 60 * 60L);
        } finally {
            lockIdMap.remove(goodsId);
        }
        return goods;
    }

    /**
     * 获取商品其他属性
     *
     * @param goods
     * @param userId
     * @return
     * @date 2019年10月10日 上午10:38:27
     */
    private GoodsDetail getGoodsAttribute(GoodsDetail goods, String userId) {

        try {
            CompletableFuture<Void> defaultFuture = CompletableFuture.runAsync(() -> {
                final Map<String, String> defaultSpecMap = typeSpecMapper.selectDefaultByGoods(goods.getGoodsId());
                String specIds = defaultSpecMap.getOrDefault("specIds", null);
                String specValues = defaultSpecMap.getOrDefault("specValues", null);
                goods.setDefSpecIds(specIds);
                goods.setDefSpecValues(specValues);
            }, executor);

            CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
                goods.setImagePaths(goodsImageMapper.selectBigByGoodsId(goods.getGoodsId()));
            }, executor);

            CompletableFuture<Void> serverFuture = CompletableFuture.runAsync(() -> {
                goods.setServers(serverSupportMapper.selectByGoods(goods.getGoodsId()));
            }, executor);

            CompletableFuture<Void> judgeFuture = CompletableFuture.runAsync(() -> {
                goods.setJudges(goodsJudgeMapper.selectJudge(userId, goods.getGoodsId(), 3));
            }, executor);

            goods.setCookerInfo(getCookerInfo(goods.getCooker(), userId));

            //等到所有任务都完成
            CompletableFuture.allOf(defaultFuture, imageFuture, serverFuture, imageFuture, judgeFuture).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goods;
    }

    /**
     * 获取商品厨师信息 1、获取厨师信息 2、获取该厨师商品列表
     *
     * @param cookerId
     * @param userId
     * @return 厨师信息
     * @date 2019年9月5日 上午11:00:29
     */
    private Cooker getCookerInfo(String cookerId, String userId) {
        Cooker cooker = cookCertifiedMapper.selectByUser(cookerId, userId);
        if (ObjectUtil.isNotNull(cooker)) {
            List<GoodsBase> cookerGoods = goodsInfoMapper.selectByCooker(cookerId, 4);
            cooker.setGoodsBases(cookerGoods);
        }
        return cooker;
    }
}
