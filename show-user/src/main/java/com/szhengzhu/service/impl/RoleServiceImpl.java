package com.szhengzhu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.user.RoleInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.UserBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.RoleInfoMapper;
import com.szhengzhu.mapper.UserRoleMapper;
import com.szhengzhu.service.RoleService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleInfoMapper roleInfoMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public Result<RoleInfo> saveRole(RoleInfo roleInfo) {
        if (roleInfo == null || StringUtils.isEmpty(roleInfo.getRoleName())) 
            return new Result<>(StatusCode._4004);
        int count = roleInfoMapper.countRole(roleInfo.getRoleName(), "0");
        if (count > 0) 
            return new Result<>(StatusCode._4007);
        roleInfo.setMarkId(IdGenerator.getInstance().nexId());
        roleInfoMapper.insertSelective(roleInfo);
        return new Result<>(roleInfo);
    }

    @Override
    public Result<RoleInfo> updateRole(RoleInfo roleInfo) {
        if (roleInfo == null || roleInfo.getMarkId() == null) 
            return new Result<>(StatusCode._4004);
        int count = roleInfoMapper.countRole(roleInfo.getRoleName(), roleInfo.getMarkId());
        if (count > 0) 
            return new Result<>(StatusCode._4007);
        roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
        return new Result<>(roleInfo);
    }

    @Override
    public Result<RoleInfo> getRoleById(String markId) {
        RoleInfo roleInfo = roleInfoMapper.selectByPrimaryKey(markId);
        return new Result<>(roleInfo);
    }

    @Override
    public Result<PageGrid<RoleInfo>> pageRole(PageParam<RoleInfo> rolePage) {
        PageHelper.startPage(rolePage.getPageIndex(), rolePage.getPageSize());
        PageHelper.orderBy(rolePage.getSidx() + " " + rolePage.getSort());
        PageInfo<RoleInfo> pageInfo = new PageInfo<>(roleInfoMapper.selectByExampleSelective(rolePage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<List<RoleInfo>> listRole(RoleInfo roleInfo) {
        List<RoleInfo> roleInfos = roleInfoMapper.selectByExampleSelective(roleInfo);
        return new Result<>(roleInfos);
    }

    @Override
    public Result<?> deleteRoleUsers(String roleId, String[] userIds) {
        if (StringUtils.isEmpty(roleId) || userIds.length < 1) 
            return new Result<>(StatusCode._4004);
        userRoleMapper.deleteRoleUsers(roleId, userIds);
        return new Result<>();
    }
    
    @Override
    public Result<?> saveRoleUsers(String roleId, String[] userIds) {
        if (StringUtils.isEmpty(roleId) || userIds.length < 1) 
            return new Result<>(StatusCode._4004);
        userRoleMapper.insertRoleUsers(roleId, userIds);
        return new Result<>();
    }

    @Override
    public Result<?> getListByCode(String roleCode) {
        List<Combobox> list = userRoleMapper.selectListByCode(roleCode);
        return new Result<>(list);
    }

    @Override
    public List<UserBase> getListByRole(String roleId) {
        return userRoleMapper.selectUsersByRoleId(roleId);
    }
}
