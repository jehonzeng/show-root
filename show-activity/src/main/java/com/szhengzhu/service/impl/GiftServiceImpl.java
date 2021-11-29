package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.activity.GiftInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.GiftInfoMapper;
import com.szhengzhu.service.GiftService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("gift")
public class GiftServiceImpl implements GiftService {

    @Resource
    private GiftInfoMapper giftInfoMapper;

    @Override
    public PageGrid<GiftInfo> getGiftPage(PageParam<GiftInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<GiftInfo> page = new PageInfo<>(
                giftInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public GiftInfo addGift(GiftInfo base) {
        int count = giftInfoMapper.repeatRecoreds(base.getGiftName(),"");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        base.setMarkId(snowflake.nextIdStr());
        base.setGiftStatus(false);
        giftInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    public GiftInfo updateGift(GiftInfo base) {
        int count = giftInfoMapper.repeatRecoreds(base.getGiftName(),base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        giftInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public GiftInfo getGiftByMark(String markId) {
        return giftInfoMapper.selectByPrimaryKey(markId);
    }

    @Override
    public List<Combobox> getGiftCombobox() {
        return giftInfoMapper.selectGiftCombobox();
    }
}
