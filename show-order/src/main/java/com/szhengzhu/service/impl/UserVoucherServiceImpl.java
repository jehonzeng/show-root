package com.szhengzhu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.order.UserVoucher;
import com.szhengzhu.bean.wechat.vo.VoucherBase;
import com.szhengzhu.mapper.UserVoucherMapper;
import com.szhengzhu.service.UserVoucherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Service("userVoucherService")
public class UserVoucherServiceImpl implements UserVoucherService {

    @Resource
    private UserVoucherMapper userVoucherMapper;

    @Override
    public List<VoucherBase> listByUser(String userId) {
        return userVoucherMapper.selectByUser(userId);
    }

    @Override
    public Map<String, UserVoucher> mapByIds(List<String> vouchers) {
        Map<String, UserVoucher> voucherMap = new HashMap<>(4);
        for (String voucherId : vouchers) {
            UserVoucher voucher = userVoucherMapper.selectByPrimaryKey(voucherId);
            if (ObjectUtil.isNull(voucher)) {
                continue;
            }
            voucherMap.put(voucher.getMarkId(), voucher);
        }
        return voucherMap;
    }

    @Override
    public void addGoodsVoucher(String userId, GoodsVoucher voucher) {
        UserVoucher userVoucher = new UserVoucher(userId, voucher);
        userVoucherMapper.insertSelective(userVoucher);
    }
}
