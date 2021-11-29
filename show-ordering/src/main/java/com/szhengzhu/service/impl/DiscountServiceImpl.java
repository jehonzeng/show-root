package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.DiscountInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.DiscountInfoMapper;
import com.szhengzhu.service.DiscountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("discountService")
public class DiscountServiceImpl implements DiscountService {

    @Resource
    private DiscountInfoMapper discountInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDiscount(DiscountInfo base) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setCreateTime(DateUtil.date());
        base.switchEmployIds(base.getEmploys());
        discountInfoMapper.insertSelective(base);
    }

    @Override
    public PageGrid<DiscountInfo> getPage(PageParam<DiscountInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<DiscountInfo> list = discountInfoMapper.selectByExampleSelective(base.getData());
        list.forEach(discountInfo -> discountInfo.switchEmploysArray(discountInfo.getEmployIds()));
        PageInfo<DiscountInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public DiscountInfo getDiscountInfo(String markId) {
        DiscountInfo discount = discountInfoMapper.selectByPrimaryKey(markId);
        ShowAssert.checkNull(discount, StatusCode._4004);
        discount.switchEmploysArray(discount.getEmployIds());
        return discount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyDiscount(DiscountInfo base) {
        base.setModifyTime(DateUtil.date());
        base.switchEmployIds(base.getEmploys());
        discountInfoMapper.updateByPrimaryKeySelective(base);
    }

    @Override
    public void modifyStatus(String[] discountIds, Integer status) {
        discountInfoMapper.updateBatchStatus(discountIds,status);
    }

    @Override
    public void deleteDiscount(String discountId) {
        discountInfoMapper.updateByDiscountId(discountId, -1);
    }

    @Override
    public List<Combobox> listCombobox(String employeeId, String storeId) {
        return discountInfoMapper.selectCombobox(employeeId, storeId);
    }

}
