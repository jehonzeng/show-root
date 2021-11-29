package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.Pay;
import com.szhengzhu.bean.ordering.PayType;
import com.szhengzhu.bean.ordering.vo.PayBaseVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.PayMapper;
import com.szhengzhu.mapper.PayTypeMapper;
import com.szhengzhu.service.PayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("payService")
public class PayServiceImpl implements PayService {

    @Resource
    private PayMapper payMapper;

    @Resource
    private PayTypeMapper payTypeMapper;

    @Override
    public PageGrid<Pay> page(PageParam<Pay> pageParam) {
        String sidx = "mark_id".equals(pageParam.getSidx())? "create_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<Pay> pageInfo = new PageInfo<>(payMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public String add(Pay pay) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        pay.setMarkId(snowflake.nextIdStr());
        pay.setCreateTime(DateUtil.date());
        payMapper.insertSelective(pay);
        return pay.getMarkId();
    }

    @Override
    public void modify(Pay pay) {
        pay.setModifyTime(DateUtil.date());
        payMapper.updateByPrimaryKeySelective(pay);
    }

    @Override
    public void delete(String payId) {
        payMapper.updateStatus(payId, -1);
    }

    @Override
    public List<PayBaseVo> resListPay(String storeId) {
        return payMapper.selectResPay(storeId);
    }

    @Override
    public PageGrid<PayType> pageType(PageParam<PayType> pageParam) {
        String sidx = "mark_id".equals(pageParam.getSidx())? "create_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<PayType> pageInfo = new PageInfo<>(payTypeMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public String addType(PayType payType) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        payType.setMarkId(snowflake.nextIdStr());
        payType.setCreateTime(DateUtil.date());
        payTypeMapper.insertSelective(payType);
        return payType.getMarkId();
    }

    @Override
    public void modifyType(PayType payType) {
        payType.setModifyTime(DateUtil.date());
        payTypeMapper.updateByPrimaryKeySelective(payType);
    }

    @Override
    public void deleteType(String typeId) {
        payTypeMapper.updateStatus(typeId, -1);
    }

    @Override
    public List<Combobox> getTypeCombobox(String storeId) {
        return payTypeMapper.selectCombobox(storeId);
    }
}
