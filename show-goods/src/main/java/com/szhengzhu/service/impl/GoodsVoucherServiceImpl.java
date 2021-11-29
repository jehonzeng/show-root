package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVoucherVo;
import com.szhengzhu.bean.wechat.vo.*;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.GoodsVoucherService;
import com.szhengzhu.util.ShowUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 */
@Service("goodsVoucherService")
public class GoodsVoucherServiceImpl implements GoodsVoucherService {

    @Resource
    private GoodsVoucherMapper goodsVoucherMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private GoodsImageMapper goodsImageMapper;

    @Resource
    private GoodsJudgeMapper goodsJudgeMapper;

    @Resource
    private CookCertifiedMapper cookCertifiedMapper;

    @Resource
    private ServerSupportMapper serverSupportMapper;

    @Resource
    private Redis redis;

    private final ConcurrentHashMap<String, Boolean> lockIdMap = new ConcurrentHashMap<>();

    @Override
    public GoodsVoucher addGoodsVoucher(GoodsVoucher base) {
        int count = goodsVoucherMapper.repeatRecords(base.getVoucherName(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        base.setMarkId(markId);
        base.setServerStatus(false);
        base.setVoucherNo(ShowUtils.createGoodsNo(1, base.getMarkId()));
        goodsVoucherMapper.insertSelective(base);
        return base;
    }

    private void voucherChanger(String voucherId) {
        redis.del("goods:voucher:detail:" + voucherId);
    }

    @Override
    public GoodsVoucher editGoodsVoucher(GoodsVoucher base) {
        String name = StrUtil.isEmpty(base.getVoucherName()) ? "" : base.getVoucherName();
        int count = goodsVoucherMapper.repeatRecords(name, base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        goodsVoucherMapper.updateByPrimaryKeySelective(base);
        voucherChanger(base.getMarkId());
        return base;
    }

    @Override
    public PageGrid<GoodsVoucherVo> getCouponPage(PageParam<GoodsVoucher> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVoucherVo> page = new PageInfo<>(goodsVoucherMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public List<Combobox> listCombobox() {
        return goodsVoucherMapper.selectCombobox();
    }

    @Override
    public GoodsVoucher getGoodsVoucherInfo(String markId) {
        return goodsVoucherMapper.selectByPrimaryKey(markId);
    }

    @Override
    public GoodsDetail getVoucherDetail(String voucherId, String userId) {
        GoodsDetail goods;
        try {
            Object goodsObj = redis.get("goods:voucher:detail:" + voucherId);
            if (ObjectUtil.isNotNull(goodsObj)) {
                goods = JSON.parseObject(JSON.toJSONString(goodsObj), GoodsDetail.class);
                return goods;
            }
            // 防止数据库因请求量过大雪崩而做的拦截
            boolean lock = lockIdMap.put(voucherId, true) == null;
            ShowAssert.checkTrue(!lock, StatusCode._5010);
            goodsObj = redis.get("goods:voucher:detail:" + voucherId);
            if (goodsObj != null) {
                goods = JSON.parseObject(JSON.toJSONString(goodsObj), GoodsDetail.class);
                return goods;
            }
            GoodsVoucher voucher = goodsVoucherMapper.selectById(voucherId);
            ShowAssert.checkNull(voucher, StatusCode._4004);
            goods = new GoodsDetail();
            goods.setGoodsId(voucherId);
            goods.setSalePrice(voucher.getPrice());
            goods.setGoodsName(voucher.getVoucherName());
            goods.setContent(voucher.getContent());
            goods.setProductType(1);
            // 商品图片
            List<String> goodsImgs = goodsImageMapper.selectBigByGoodsId(voucher.getProductId());
            goods.setImagePaths(goodsImgs);
            // 商品服务支持
            List<Map<String, String>> servers = serverSupportMapper.selectByGoods(voucher.getProductId());
            goods.setServers(servers);
            // 当前商品厨师
            Cooker cooker = cookCertifiedMapper.selectByUser(goods.getCooker(), userId);
            if (ObjectUtil.isNotNull(cooker)) {
                // 推荐厨师菜品
                List<GoodsBase> cookerGoods = goodsInfoMapper.selectByCooker(goods.getCooker(), 4);
                cooker.setGoodsBases(cookerGoods);
                goods.setCookerInfo(cooker);
            }
            List<JudgeBase> judges = goodsJudgeMapper.selectJudge(userId, voucher.getProductId(), 3);
            goods.setJudges(judges);
            redis.set("goods:voucher:detail:" + voucherId, goods, 2 * 60 * 60L);
            return goods;
        } finally {
            lockIdMap.remove(voucherId);
        }
    }

    @Override
    public List<JudgeBase> listVoucherJudge(String voucherId, String userId) {
        return goodsVoucherMapper.selectVoucherJudge(voucherId, userId);
    }

    @Override
    public StockBase getStockInfo(String voucherId) {
        GoodsVoucher voucher = goodsVoucherMapper.selectByPrimaryKey(voucherId);
        return StockBase.builder().goodsId(voucherId).salePrice(voucher.getPrice())
                .basePrice(voucher.getPrice()).isDelivery(true).currentStock(voucher.getCurrentStock()).build();
    }

    @Override
    public void subStock(String voucherId, int quantity) {
        goodsVoucherMapper.subStock(voucherId, quantity);
    }

    @Override
    public void addStock(String voucherId, int quantity) {
        goodsVoucherMapper.addStock(voucherId, quantity);
    }
}
