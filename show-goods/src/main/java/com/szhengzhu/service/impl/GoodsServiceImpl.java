package com.szhengzhu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.GoodsServer;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.GoodsInfoVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.CookCertifiedMapper;
import com.szhengzhu.mapper.GoodsImageMapper;
import com.szhengzhu.mapper.GoodsInfoMapper;
import com.szhengzhu.mapper.GoodsJudgeMapper;
import com.szhengzhu.mapper.GoodsServerMapper;
import com.szhengzhu.mapper.GoodsVoucherMapper;
import com.szhengzhu.mapper.MealInfoMapper;
import com.szhengzhu.mapper.ServerSupportMapper;
import com.szhengzhu.mapper.TypeSpecMapper;
import com.szhengzhu.service.GoodsService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsServerMapper goodsServerMapper;

    @Autowired
    private GoodsImageMapper goodsImageMapper;

    @Autowired
    private GoodsJudgeMapper goodsJudgeMapper;

    @Autowired
    private CookCertifiedMapper cookCertifiedMapper;

    @Autowired
    private ServerSupportMapper serverSupportMapper;

    @Autowired
    private TypeSpecMapper typeSpecMapper;

    @Autowired
    private GoodsVoucherMapper goodsVoucherMapper;

    @Autowired
    private MealInfoMapper mealInfoMapper;

    @Override
    public Result<?> addGoods(GoodsInfo base) {
        if (base == null || StringUtils.isEmpty(base.getGoodsName())) {
            return new Result<>(StatusCode._4004);
        }
        int count = goodsInfoMapper.repeatRecords(base.getGoodsName(), "");
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus("ZT01");
        base.setCreateTime(TimeUtils.today());
        goodsInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Transactional
    public Result<?> editGoods(GoodsInfo base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4008);
        String goodsName = base.getGoodsName() == null ? "" : base.getGoodsName();
        int count = goodsInfoMapper.repeatRecords(goodsName, base.getMarkId());
        if (count > 0)
            return new Result<>(StatusCode._4007);
        GoodsInfo preGoods = goodsInfoMapper.selectByPrimaryKey(base.getMarkId());
        if (preGoods == null)
            return new Result<>(StatusCode._4008);
        Date preUpperTime = preGoods.getPreUpperTime();
        Date preDownTime = preGoods.getPreDownTime();
        // 未设置过时间
        if (preUpperTime == null && preDownTime == null) {
            if (base.getPreUpperTime() != null && base.getPreDownTime() != null) {
                base.setServerStatus("ZT01");
            }
        }
        // 已经设置过时间
        if (preUpperTime != null && preDownTime != null) {
            if (preUpperTime.compareTo(base.getPreUpperTime()) != 0
                    || preDownTime.compareTo(base.getPreDownTime()) != 0) {
                // 清空预上架时间
                if (base.getPreDownTime() == null && base.getPreUpperTime() == null) {
                    goodsInfoMapper.clearTime(base.getMarkId()); // 时间置空
                }
                base.setServerStatus("ZT01");// 设置为待上架
            }
        }
        base.setModifyTime(TimeUtils.today());
        goodsInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Transactional
    public Result<?> editGoodsStatus(GoodsInfo base) {
        if (base == null || base.getMarkId() == null
                || StringUtils.isEmpty(base.getServerStatus())) {
            return new Result<>(StatusCode._4008);
        }
        GoodsInfo previousGoods = goodsInfoMapper.selectByPrimaryKey(base.getMarkId());
        if (previousGoods == null)
            return new Result<>(StatusCode._4008);
        // 已做预处理设置时间无法手动更改状态
        if (previousGoods.getPreUpperTime() != null || previousGoods.getPreDownTime() != null)
            return new Result<>(StatusCode._5011);
        String oldStatus = previousGoods.getServerStatus();
        String newStatus = base.getServerStatus();
        // 按一定顺序改变状态
        if (oldStatus.equals("ZT01") && newStatus.equals("ZT02")) {
            base.setUpperTime(TimeUtils.today());
        } else if (oldStatus.equals("ZT02") && newStatus.equals("ZT03")) {
            // 下架操作处理关联的商品券和套餐状态 
            goodsVoucherMapper.updateStatusByGoods(base.getMarkId());
            mealInfoMapper.updateStatusByGoods(base.getMarkId());
            base.setDownTime(TimeUtils.today());
        } else if (oldStatus.equals("ZT03") && newStatus.equals("ZT01")) {
            
        } else {
            return new Result<>(StatusCode._5012);
        }
        base.setModifyTime(TimeUtils.today());
        goodsInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> getGoodsInfo(String markId) {
        GoodsInfo info = goodsInfoMapper.selectByPrimaryKey(markId);
        return new Result<>(info);
    }

    @Override
    public Result<PageGrid<GoodsVo>> getPage(PageParam<GoodsInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVo> page = new PageInfo<>(
                goodsInfoMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<List<Combobox>> listCombobox() {
        List<Combobox> comboboxs = goodsInfoMapper.selectCombobox();
        return new Result<>(comboboxs);
    }

    @Override
    public Result<List<Combobox>> getListNotColumn() {
        List<Combobox> comboboxs = goodsInfoMapper.selectListNotInColumn();
        return new Result<>(comboboxs);
    }

    @Override
    public Result<List<Combobox>> getListNotLabel(String labelId) {
        List<Combobox> comboboxs = goodsInfoMapper.selectListNotInLabel(labelId);
        return new Result<>(comboboxs);
    }

    @Override
    public Result<?> addBatchServer(BatchVo base) {
        List<GoodsServer> list = new LinkedList<>();
        GoodsServer data;
        IdGenerator generator = IdGenerator.getInstance();
        for (String serverId : base.getIds()) {
            data = new GoodsServer();
            data.setMarkId(generator.nexId());
            data.setGoodsId(base.getCommonId());
            data.setServerId(serverId);
            list.add(data);
        }
        if (list.size() > 0) {
            goodsServerMapper.insertBatch(list);
        }
        return new Result<>();
    }

    @Override
    public Result<?> moveBatchServer(BatchVo base) {
        goodsServerMapper.deletetBatch(base);
        return new Result<>();
    }

    @Override
    public Result<?> listInnerServer(String goodsId) {
        return new Result<>(goodsServerMapper.selectListByGoodsId(goodsId));
    }

    @Override
    public Result<List<Combobox>> getListNotSpecial() {
        List<Combobox> list = goodsInfoMapper.selectListNotSpecial();
        return new Result<>(list);
    }

    @Override
    public Result<List<Combobox>> getListNotIcon() {
        List<Combobox> list = goodsInfoMapper.selectListNotIcon();
        return new Result<>(list);
    }

    @Override
    public Result<PageGrid<GoodsVo>> getPageByColumn(PageParam<GoodsInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("g." + base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVo> page = new PageInfo<>(
                goodsInfoMapper.selectByExampleSelectiveNotColumn(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<PageGrid<GoodsVo>> getPageByLabel(PageParam<GoodsInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("g." + base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVo> page = new PageInfo<>(
                goodsInfoMapper.selectByExampleSelectiveNotLabel(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<PageGrid<GoodsVo>> getPageByIcon(PageParam<GoodsInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("g." + base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVo> page = new PageInfo<>(
                goodsInfoMapper.selectByExampleSelectiveNotIcon(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<PageGrid<GoodsVo>> getPageBySpecial(PageParam<GoodsInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("g." + base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVo> page = new PageInfo<>(
                goodsInfoMapper.selectByExampleSelectiveNotSpecial(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<List<GoodsBase>> listRecommend(String userId) {
        List<GoodsBase> goodsList = new ArrayList<>();
        if (StringUtils.isEmpty(userId))
            goodsList = goodsInfoMapper.selectRecommend(); // not log in
        else
            goodsList = goodsInfoMapper.selectRecommenByUser(userId); // log in
        return new Result<>(goodsList);
    }

    @Override
    public Result<GoodsInfoVo> getGoodsDetail(String goodsId, String userId) {
        if (StringUtils.isEmpty(goodsId))
            return new Result<>(StatusCode._4004);
        GoodsInfoVo goods = goodsInfoMapper.selectById(goodsId); // goods info
        if (goods == null)
            return new Result<>(StatusCode._4004);
        Map<String, String> defaultSpecMap = typeSpecMapper
                .selectDefaultByGoods(goods.getGoodsId());
        String specIds = null;
        String specValues = null;
        if (defaultSpecMap != null) {
            specIds = defaultSpecMap.containsKey("specIds") ? defaultSpecMap.get("specIds") : null;
            specValues = defaultSpecMap.containsKey("specValues") ? defaultSpecMap.get("specValues")
                    : null;
        }
        List<String> goodsImgs = goodsImageMapper.selectBigByGoodsId(goodsId); // goods images
        List<Map<String, String>> servers = serverSupportMapper.selectByGoods(goodsId); // goods
        // server
        Cooker cooker = cookCertifiedMapper.selectByUser(goods.getCooker(), userId); // goods cooker
        if (cooker != null) {
            List<GoodsBase> cookerGoods = goodsInfoMapper.selectByCooker(goods.getCooker(), 4); // cooker
            // goods
            cooker.setGoodsBases(cookerGoods);
            goods.setCookerInfo(cooker);
        }
        List<JudgeBase> judges = goodsJudgeMapper.selectJudge(userId, goodsId, 3); // goods judge
        goods.setDefSpecIds(specIds);
        goods.setDefSpecValues(specValues);
        goods.setImagePaths(goodsImgs);
        goods.setServers(servers);
        goods.setJudges(judges);
        return new Result<>(goods);
    }
}
