package com.szhengzhu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVoucherVo;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.GoodsInfoVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.CookCertifiedMapper;
import com.szhengzhu.mapper.GoodsImageMapper;
import com.szhengzhu.mapper.GoodsInfoMapper;
import com.szhengzhu.mapper.GoodsJudgeMapper;
import com.szhengzhu.mapper.GoodsVoucherMapper;
import com.szhengzhu.mapper.ServerSupportMapper;
import com.szhengzhu.service.GoodsVoucherService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.StringUtils;

@Service("goodsVoucherService")
public class GoodsVoucherServiceImpl implements GoodsVoucherService {
    
    @Autowired
    private GoodsVoucherMapper goodsVoucherMapper;
    
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    
    @Autowired
    private GoodsImageMapper goodsImageMapper;
    
    @Autowired
    private GoodsJudgeMapper goodsJudgeMapper;
    
    @Autowired
    private CookCertifiedMapper cookCertifiedMapper;
    
    @Autowired
    private ServerSupportMapper serverSupportMapper;

    @Override
    public Result<?> addGoodsVoucher(GoodsVoucher base) {
        if(StringUtils.isEmpty(base.getVoucherName())) {
            return new Result<>(StatusCode._4004);
        }
        int count = goodsVoucherMapper.repeatRecords(base.getVoucherName(), "");
        if(count > 0 ) {
            return new Result<>(StatusCode._4007);
        }
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        base.setVoucherNo(ShowUtils.createGoodsNo(1, base.getMarkId()));
        goodsVoucherMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> editGoodsVoucher(GoodsVoucher base) {
        if(base == null || base.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        String name = base.getVoucherName() == null ? "" : base.getVoucherName();
        int count = goodsVoucherMapper.repeatRecords(name, base.getMarkId());
        if (count > 0) {
            return new Result<String>(StatusCode._4007);
        }
        goodsVoucherMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<GoodsVoucherVo>> getCouponPage(PageParam<GoodsVoucher> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<GoodsVoucherVo> page = new PageInfo<>(
                goodsVoucherMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<List<Combobox>> listCombobox() {
        List<Combobox> comboboxs = goodsVoucherMapper.selectCombobox();
        return new Result<>(comboboxs);
    }

    @Override
    public Result<?> getGoodsVoucherInfo(String markId) {
        return new Result<>(goodsVoucherMapper.selectByPrimaryKey(markId));
    }

    @Override
    public Result<GoodsInfoVo> getVoucherDetail(String voucherId, String userId) {
        GoodsVoucher voucher = goodsVoucherMapper.selectById(voucherId);
        if (voucher == null)
            return new Result<>(StatusCode._4004);
        GoodsInfoVo goods = new GoodsInfoVo();
        goods.setGoodsId(voucherId);
        goods.setSalePrice(voucher.getPrice());
        goods.setGoodsName(voucher.getVoucherName());
        goods.setContent(voucher.getContent());
        goods.setProductType(1);
        List<String> goodsImgs = goodsImageMapper.selectBigByGoodsId(voucher.getProductId());  // 商品图片
        goods.setImagePaths(goodsImgs);
        List<Map<String, String>> servers = serverSupportMapper.selectByGoods(voucher.getProductId());  // 商品服务支持
        goods.setServers(servers);
        Cooker cooker = cookCertifiedMapper.selectByUser(goods.getCooker(), userId);  // 当前商品厨师
        if (cooker != null) {
            List<GoodsBase> cookerGoods = goodsInfoMapper.selectByCooker(goods.getCooker(), 4); // 推荐厨师菜品
            cooker.setGoodsBases(cookerGoods);
            goods.setCookerInfo(cooker);
        }
        List<JudgeBase> judges = goodsJudgeMapper.selectJudge(userId, voucher.getProductId(), 3);
        goods.setJudges(judges);
        return new Result<>(goods);
    }

    @Override
    public Result<List<JudgeBase>> listVoucherJudge(String voucherId, String userId) {
        List<JudgeBase> judges = goodsVoucherMapper.selectVoucherJudge(voucherId, userId);
        return new Result<>(judges);
    }

    @Override
    public Result<StockBase> getStockInfo(String voucherId) {
        StockBase stockBase = new StockBase();
        GoodsVoucher voucher = goodsVoucherMapper.selectByPrimaryKey(voucherId);
        stockBase.setGoodsId(voucherId);
        stockBase.setSalePrice(voucher.getPrice());
        stockBase.setBasePrice(voucher.getPrice());
        stockBase.setIsDelivery(true);
        stockBase.setCurrentStock(voucher.getCurrentStock());
        return new Result<>(stockBase);
    }

}
