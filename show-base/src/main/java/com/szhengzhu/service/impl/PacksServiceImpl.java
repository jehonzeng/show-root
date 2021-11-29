package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.base.PacksInfo;
import com.szhengzhu.bean.base.PacksItem;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.PacksVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.CouponTemplateMapper;
import com.szhengzhu.mapper.PacksInfoMapper;
import com.szhengzhu.mapper.PacksItemMapper;
import com.szhengzhu.service.PacksService;
import com.szhengzhu.util.CouponUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
@Slf4j
@Service("packsService")
public class PacksServiceImpl implements PacksService {

    @Resource
    private PacksInfoMapper packsInfoMapper;

    @Resource
    private PacksItemMapper packsItemMapper;

    @Resource
    private CouponTemplateMapper couponTemplateMapper;

    @Override
    public List<UserCoupon> manualCoupon(String markId, String userId) {
        PacksInfo info = packsInfoMapper.selectByEnd(markId);
        ShowAssert.checkNull(info, StatusCode._4026);
        // 验证是否已经领取(查看用户拥有的券)
        List<String> templates = packsItemMapper.selectTemplates(markId);
        int count;
        for (String template : templates) {
            count = packsItemMapper.isExitsCoupon(userId, template);
            ShowAssert.checkTrue(count > 0, StatusCode._4007);
        }
        // 用户获取大礼包中所有券
        List<CouponTemplate> coupons = couponTemplateMapper.selectCouponTemplates(templates);
        List<UserCoupon> list = new LinkedList<>();
        ShowAssert.checkTrue(coupons.isEmpty(), StatusCode._5009);
        List<UserCoupon> userCoupons;
        for (CouponTemplate coupon : coupons) {
            userCoupons = CouponUtils.createCoupon(coupon, userId);
            list.addAll(userCoupons);
        }
        ShowAssert.checkTrue(list.isEmpty(), StatusCode._5009);
        return list;
    }

    @Override
    public PacksInfo addPacks(PacksInfo base) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        packsInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    public PacksInfo modifyPacks(PacksInfo base) {
        packsInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<PacksInfo> getPacksPage(PageParam<PacksInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<PacksInfo> list = packsInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<PacksInfo> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public PageGrid<PacksVo> getPacksItemPage(PageParam<PacksItem> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("i." + base.getSidx() + " " + base.getSort());
        List<PacksVo> list = packsItemMapper.selectByExampleSelective(base.getData());
        PageInfo<PacksVo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public void batchPacksTemplate(BatchVo base) {
        PacksItem item;
        List<PacksItem> items = new LinkedList<>();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (String templateId : base.getIds()) {
            item = PacksItem.builder().markId(snowflake.nextIdStr()).packsId(base.getCommonId()).serverStatus(false).templateId(templateId).build();
            items.add(item);
        }
        if (!items.isEmpty()) {
            packsItemMapper.insertBatch(items);
        }
    }

    @Override
    public PacksItem updatePacksTemplate(PacksItem base) {
        packsItemMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PacksInfo getPacksInfo(String markId) {
        return packsInfoMapper.selectByPrimaryKey(markId);
    }

}
