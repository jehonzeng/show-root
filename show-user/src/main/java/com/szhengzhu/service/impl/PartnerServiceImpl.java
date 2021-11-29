package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.user.PartnerInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.PartnerInfoMapper;
import com.szhengzhu.service.PartnerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("partnerService")
public class PartnerServiceImpl implements PartnerService {

    @Resource
    private PartnerInfoMapper partnerInfoMapper;

    @Override
    public PartnerInfo addPartner(PartnerInfo base) {
        int count = partnerInfoMapper.repeatRecords(base.getName(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setAddTime(DateUtil.date());
        partnerInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    public PartnerInfo modify(PartnerInfo base) {
        int count = partnerInfoMapper.repeatRecords(base.getName(), base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        partnerInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<PartnerInfo> getPartnerPage(PageParam<PartnerInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<PartnerInfo> list = partnerInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<PartnerInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public void deletePartner(String markId) {
        partnerInfoMapper.deleteByPrimaryKey(markId);
    }

    @Override
    public List<Combobox> listCombobox() {
        return partnerInfoMapper.selectCombobox();
    }
}
