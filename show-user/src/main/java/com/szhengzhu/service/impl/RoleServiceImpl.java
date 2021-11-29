package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.user.RoleInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.UserBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.RoleInfoMapper;
import com.szhengzhu.mapper.UserRoleMapper;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jehon Zeng
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleInfoMapper roleInfoMapper;

	@Resource
	private UserRoleMapper userRoleMapper;

	@Resource
	private Redis redis;

	@Override
	public RoleInfo saveRole(RoleInfo roleInfo) {
		int count = roleInfoMapper.countRole(roleInfo.getRoleName(), "0");
		ShowAssert.checkTrue(count > 0, StatusCode._4007);
		Snowflake snowflake = IdUtil.getSnowflake(1, 1);
		roleInfo.setMarkId(snowflake.nextIdStr());
		roleInfoMapper.insertSelective(roleInfo);
		return roleInfo;
	}

	@Override
	public RoleInfo updateRole(RoleInfo roleInfo) {
		int count = roleInfoMapper.countRole(roleInfo.getRoleName(), roleInfo.getMarkId());
		ShowAssert.checkTrue(count > 0, StatusCode._4007);
		roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
		return roleInfo;
	}

	@Override
	public RoleInfo getRoleById(String markId) {
		return roleInfoMapper.selectByPrimaryKey(markId);
	}

	@Override
	public PageGrid<RoleInfo> pageRole(PageParam<RoleInfo> rolePage) {
		PageMethod.startPage(rolePage.getPageIndex(), rolePage.getPageSize());
		PageMethod.orderBy(rolePage.getSidx() + " " + rolePage.getSort());
		PageInfo<RoleInfo> pageInfo = new PageInfo<>(roleInfoMapper.selectByExampleSelective(rolePage.getData()));
		return new PageGrid<>(pageInfo);
	}

	@Override
	public List<RoleInfo> listRole(RoleInfo roleInfo) {
		return roleInfoMapper.selectByExampleSelective(roleInfo);
	}

	@Override
	public void deleteRoleUsers(String roleId, String[] userIds) {
		userRoleMapper.deleteRoleUsers(roleId, userIds);
		redis.del("user:manage:list");
	}

	@Override
	public void saveRoleUsers(String roleId, String[] userIds) {
		userRoleMapper.insertRoleUsers(roleId, userIds);
		redis.del("user:manage:list");
	}

	@Override
	public List<Combobox> getListByCode(String roleCode) {
		return userRoleMapper.selectListByCode(roleCode);
	}

	@Override
	public List<UserBase> getListByRole(String roleId) {
		return userRoleMapper.selectUsersByRoleId(roleId);
	}
}
