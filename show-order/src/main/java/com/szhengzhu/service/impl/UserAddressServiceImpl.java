package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.UserAddressMapper;
import com.szhengzhu.service.UserAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jehon Zeng
 */
@Service("userAddressService")
public class UserAddressServiceImpl implements UserAddressService {

    @Resource
    private UserAddressMapper userAddressMapper;

    @Override
    public PageGrid<UserAddress> pageAddress(PageParam<UserAddress> addressPage) {
        PageMethod.startPage(addressPage.getPageIndex(), addressPage.getPageSize());
        PageMethod.orderBy(addressPage.getSidx() + " " + addressPage.getSort());
        PageInfo<UserAddress> pageInfo = new PageInfo<>(
                userAddressMapper.selectByExampleSelective(addressPage.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public List<UserAddress> listByUser(String userId) {
        return userAddressMapper.selectByUser(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UserAddress address) {
        if (ObjectUtil.isNull(address.getDefaultOr())) { address.setDefaultOr(false); }
        int countDefault = userAddressMapper.countDefault(address.getUserId());
        if (countDefault > 0 && Boolean.TRUE.equals(address.getDefaultOr())) { userAddressMapper.updateNoDefaultByUser(address.getUserId()); }
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        address.setCreateTime(DateUtil.date());
        address.setMarkId(snowflake.nextIdStr());
        userAddressMapper.insertSelective(address);
    }

    @Override
    public void modify(UserAddress address) {
        if (Boolean.TRUE.equals(address.getDefaultOr())) { userAddressMapper.updateNoDefaultByUser(address.getUserId()); }
        userAddressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public UserAddress getDefByUser(String userId) {
        return userAddressMapper.selectDefByUser(userId);
    }

    @Override
    public UserAddress getInfo(String addressId) {
        return userAddressMapper.selectByPrimaryKey(addressId);
    }

}
