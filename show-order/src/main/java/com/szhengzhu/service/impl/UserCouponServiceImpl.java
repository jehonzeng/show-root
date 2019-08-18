package com.szhengzhu.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.order.UserVoucher;
import com.szhengzhu.bean.vo.CalcBase;
import com.szhengzhu.bean.vo.UserBase;
import com.szhengzhu.bean.wechat.vo.CouponBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.UserAddressMapper;
import com.szhengzhu.mapper.UserCouponMapper;
import com.szhengzhu.mapper.UserVoucherMapper;
import com.szhengzhu.service.UserCouponService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("userCouponService")
public class UserCouponServiceImpl implements UserCouponService {

    @Resource
    private UserCouponMapper userCouponMapper;
    
    @Resource
    private UserVoucherMapper userVoucherMapper;
    
    @Resource
    private UserAddressMapper userAddressMapper;

    @Override
    public Result<PageGrid<UserCoupon>> pageCoupon(PageParam<UserCoupon> couponPage) {
        PageHelper.startPage(couponPage.getPageIndex(), couponPage.getPageSize());
        PageHelper.orderBy(couponPage.getSidx() + " " + couponPage.getSort());
        PageInfo<UserCoupon> pageInfo = new PageInfo<>(
                userCouponMapper.selectByExampleSelective(couponPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<List<CouponBase>> listByUser(String userId, Integer type) {
        List<CouponBase> couponBases = userCouponMapper.selectByUser(userId, type);
        return new Result<>(couponBases);
    }

    @Override
    public Result<?> addUserCoupon(List<UserCoupon> base) {
        if (base == null || base.size() == 0)
            return new Result<>(StatusCode._5009);
        userCouponMapper.insertBatch(base);
        return new Result<>();
    }

    @Override
    public Result<?> addCouponByRole(List<UserCoupon> base, List<UserBase> userList) {
        List<UserCoupon> list = new LinkedList<>();
        IdGenerator idGenerator = IdGenerator.getInstance();
        UserCoupon temp = null;
        for (UserBase users : userList) {
            for (UserCoupon coupon : base) {
                coupon.setMarkId(idGenerator.nexId());
                coupon.setUserId(users.getMarkId());
                temp = new UserCoupon(coupon);
                list.add(temp);
                if (list.size() % 1000 == 0) {
                    userCouponMapper.insertBatch(list);
                    list = new LinkedList<>();
                }
            }
        }
        if (list.size() > 0)
            userCouponMapper.insertBatch(list);
        return new Result<>();
    }

    @Override
    public void clearOverdueCoupon() {
        userCouponMapper.updateOverdueCoupon();
    }

    @Override
    public Result<UserCoupon> getInfo(String couponId) {
        UserCoupon coupon = userCouponMapper.selectByPrimaryKey(couponId);
        return new Result<>(coupon);
    }

    @Override
    public Result<CalcBase> getCalcParam(String couponId, List<String> vouchers, String addressId) {
        CalcBase calc = new CalcBase();
        if (!StringUtils.isEmpty(couponId)) {
            UserCoupon coupon = userCouponMapper.selectByPrimaryKey(couponId);
            calc.setCoupon(coupon);
        }
        if (vouchers != null && vouchers.size() > 0) {
            Map<String, UserVoucher> voucherMap = new HashMap<>();
            for (int index = 0, size = vouchers.size(); index < size; index++) {
                UserVoucher voucher = userVoucherMapper.selectByPrimaryKey(vouchers.get(index));
                if (voucher != null)
                    voucherMap.put(voucher.getMarkId(), voucher);
            }
            calc.setVoucherMap(voucherMap);
        }
        if (StringUtils.isEmpty(addressId)) {
            UserAddress address = userAddressMapper.selectByPrimaryKey(addressId);
            calc.setAddress(address);
        }
        return new Result<>(calc);
    }
}