package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.excel.VoucherCodeExcel;
import com.szhengzhu.bean.ordering.Voucher;
import com.szhengzhu.bean.ordering.VoucherCode;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.VoucherCodeMapper;
import com.szhengzhu.mapper.VoucherMapper;
import com.szhengzhu.service.VoucherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Service("voucherService")
public class VoucherServiceImpl implements VoucherService {

    @Resource
    private VoucherMapper voucherMapper;

    @Resource
    private VoucherCodeMapper voucherCodeMapper;

    @Override
    public PageGrid<Voucher> page(PageParam<Voucher> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(param.getSidx() + " " + param.getSort());
        List<Voucher> list = voucherMapper.selectByExampleSelective(param.getData());
        PageInfo<Voucher> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Transactional
    @Override
    public void add(Voucher voucher) throws InterruptedException {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        voucher.setMarkId(snowflake.nextIdStr());
        voucher.setCreateTime(DateUtil.date());
        voucherMapper.insertSelective(voucher);
        List<VoucherCode> codes = new LinkedList<>();
        for (int i = 0; i < voucher.getQuantity(); i++) {
            VoucherCode voucherCode = VoucherCode.builder().code(snowflake.nextIdStr()).status(1).balance(voucher.getAmount())
                    .voucherId(voucher.getMarkId()).build();
            codes.add(voucherCode);
//            防止出现连续数据
            TimeUnit.MILLISECONDS.sleep(1);
        }
        voucherCodeMapper.insertBatch(codes);
    }

    @Override
    public void modify(Voucher voucher) {
        voucherMapper.updateByPrimaryKeySelective(voucher);
    }

    @Override
    public List<VoucherCodeExcel> listCode(String voucherId) {
        return voucherCodeMapper.selectVoucherCode(voucherId);
    }

    @Override
    public Voucher getCodeInfo(String code) {
        VoucherCode voucherCode = voucherCodeMapper.selectByPrimaryKey(code);
        ShowAssert.checkNull(voucherCode, StatusCode._4055);
        ShowAssert.checkTrue(ObjectUtil.isNotNull(voucherCode.getUseTime()) || voucherCode.getStatus() == 0, StatusCode._4055);
        Voucher voucher = voucherMapper.selectByPrimaryKey(voucherCode.getVoucherId());
        ShowAssert.checkNull(voucher, StatusCode._4055);
        ShowAssert.checkTrue(ObjectUtil.isNotNull(voucher.getStartTime()) && voucher.getStartTime().getTime() < System.currentTimeMillis(), StatusCode._4055);
        ShowAssert.checkTrue(ObjectUtil.isNotNull(voucher.getStopTime()) && voucher.getStopTime().getTime() > System.currentTimeMillis(), StatusCode._4055);
        voucher.setCode(voucherCode.getCode());
        voucher.setBalance(voucherCode.getBalance());
        return voucher;
    }

    @Override
    public void useCode(String code, Integer amount) {
        VoucherCode voucherCode = voucherCodeMapper.selectByPrimaryKey(code);
        ShowAssert.checkTrue(ObjectUtil.isNotNull(voucherCode.getUseTime()) || voucherCode.getStatus() == 0, StatusCode._4055);
        if (voucherCode.getBalance() == null || voucherCode.getBalance().equals(amount)) {
            voucherCodeMapper.useByCode(code);
        } else {
            ShowAssert.checkTrue(voucherCode.getBalance() < amount, StatusCode._4035);
            voucherCode.setBalance(voucherCode.getBalance() - amount);
        }
    }
}
