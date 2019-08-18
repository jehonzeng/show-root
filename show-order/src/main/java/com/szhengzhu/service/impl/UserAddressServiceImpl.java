package com.szhengzhu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.UserAddressMapper;
import com.szhengzhu.service.UserAddressService;
import com.szhengzhu.util.GklifeUtils;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("userAddressService")
public class UserAddressServiceImpl implements UserAddressService {

    @Resource
    private UserAddressMapper userAddressMapper;

    @Override
    public Result<PageGrid<UserAddress>> pageAddress(PageParam<UserAddress> addressPage) {
        PageHelper.startPage(addressPage.getPageIndex(), addressPage.getPageSize());
        PageHelper.orderBy(addressPage.getSidx() + " " + addressPage.getSort());
        PageInfo<UserAddress> pageInfo = new PageInfo<>(
                userAddressMapper.selectByExampleSelective(addressPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<List<UserAddress>> listByUser(String userId) {
        if (StringUtils.isEmpty(userId))
            return new Result<>(StatusCode._4004);
        List<UserAddress> addresses = userAddressMapper.selectByUser(userId);
        return new Result<>(addresses);
    }

    @Transactional
    @Override
    public Result<?> add(UserAddress address) {
        if (address.getDefaultOr() == null)
            address.setDefaultOr(false);
        int countDefault = userAddressMapper.countDefault(address.getUserId());
        if (countDefault > 0 && address.getDefaultOr())
            userAddressMapper.updateNoDefaultByUser(address.getUserId());
        address.setCreateTime(TimeUtils.today());
        address.setMarkId(IdGenerator.getInstance().nexId());
        String result = GklifeUtils.sendWithIn(address.getCity(), address.getUserAddress());
        JSONObject object = JSONObject.parseObject(result);
        String res = object.getString("result");
        if (res.equals("success")) {
            String info = object.getString("info");
            JSONObject infoJson = JSONObject.parseObject(info);
            int within = infoJson.getIntValue("within");
            if (within == 1) {
                address.setSendToday(true);
            }
            double lng = infoJson.getDoubleValue("lng");
            double lat = infoJson.getDoubleValue("lat");
            address.setLongitude(lng);
            address.setLatitude(lat);
        }
        userAddressMapper.insertSelective(address);
        return new Result<>();
    }

    @Override
    public Result<?> modify(UserAddress address) {
        if (address.getDefaultOr())
            userAddressMapper.updateNoDefaultByUser(address.getUserId());
        userAddressMapper.updateByPrimaryKeySelective(address);
        return new Result<>();
    }

    @Override
    public Result<UserAddress> getDefByUser(String userId) {
        UserAddress address = userAddressMapper.selectDefByUser(userId);
        return new Result<>(address);
    }

    @Override
    public Result<UserAddress> getInfo(String addressId) {
        UserAddress address = userAddressMapper.selectByPrimaryKey(addressId);
        if (address != null && address.getSendToday() == null) {
            String result = GklifeUtils.sendWithIn(address.getCity(), address.getUserAddress());
            JSONObject object = JSONObject.parseObject(result);
            String res = object.getString("result");
            if (res.equals("success")) {
                String info = object.getString("info");
                JSONObject infoJson = JSONObject.parseObject(info);
                int within = infoJson.getIntValue("within");
                boolean sendToday = false;
                if (within == 1)
                    sendToday = true;
                address.setSendToday(sendToday);
                double lng = infoJson.getDoubleValue("lng");
                double lat = infoJson.getDoubleValue("lat");
                address.setLongitude(lng);
                address.setLatitude(lat);
                userAddressMapper.updateByPrimaryKeySelective(address);
            }
        }
        return new Result<>(address);
    }

}
