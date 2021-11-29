package com.szhengzhu.service;

import com.szhengzhu.bean.user.RoleInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.UserBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

/**
 * @author Jehon Zeng
 */
public interface RoleService {

    /**
     * 新增用户角色信息
     *
     * @date 2019年2月22日 上午10:35:23
     * @param roleInfo
     * @return
     */
    RoleInfo saveRole(RoleInfo roleInfo);

    /**
     * 修改用户角色信息
     *
     * @date 2019年2月22日 上午10:43:08
     * @param roleInfo
     * @return
     */
    RoleInfo updateRole(RoleInfo roleInfo);

    /**
     * 根据主键获取获取角色信息
     *
     * @date 2019年2月22日 上午10:47:39
     * @param markId
     * @return
     */
    RoleInfo getRoleById(String markId);

    /**
     * 获取角色分页列表
     *
     * @date 2019年2月22日 上午10:49:02
     * @param rolePage
     * @return
     */
    PageGrid<RoleInfo> pageRole(PageParam<RoleInfo> rolePage);

    /**
     * 获取角色列表
     *
     * @date 2019年3月1日 下午3:42:11
     * @param roleInfo
     * @return
     */
    List<RoleInfo> listRole(RoleInfo roleInfo);

    /**
     * 删除角色与用户关联
     * @date 2019年3月1日 下午5:00:23
     * @param roleId
     * @param userIds
     * @return
     */
    void deleteRoleUsers(String roleId, String[] userIds);

    /**
     * 添加角色与用户关联
     * @date 2019年3月1日 下午5:01:37
     * @param roleId
     * @param userIds
     * @return
     */
    void saveRoleUsers(String roleId, String[] userIds);

    /**
     * 根据角色码名获取用户下拉列表
     * @date 2019年3月18日 上午11:32:34
     * @param roleCode
     * @return
     */
    List<Combobox> getListByCode(String roleCode);

    /**
     * 根据角色Id获取用户列表
     *
     * @date 2019年6月25日 上午11:40:09
     * @param roleId
     * @return
     */
    List<UserBase> getListByRole(String roleId);
}
