package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.Identity;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface IdentityService {

    /**
     * 获取角色分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<Identity> page(PageParam<Identity> pageParam);

    /**
     * 获取角色详细信息
     *
     * @param identityId
     * @return
     */
    Identity getInfo(String identityId);

    /**
     * 添加角色
     *
     * @param identity
     * @return
     */
    void add(Identity identity);

    /**
     * 修改角色
     *
     * @param identity
     * @return
     */
    void modify(Identity identity);

    /**
     * 删除角色
     *
     * @param identityId
     * @return
     */
    void delete(String identityId);

    /**
     * 添加或删除角色用户
     *
     * @param employeeIds
     * @param identityId
     * @return
     */
    void optEmployee(String[] employeeIds, String identityId);

    /**
     * 查询角色用户id列表
     *
     * @param identityId
     * @return
     */
    String[] listEmployee(String identityId);

    /**
     * 获取用户角色权限
     *
     * @param employeeId
     * @param storeId
     * @return
     */
    String getAuth(String employeeId, String storeId);

    /**
     * 获取角色键值对
     *
     * @param storeId
     * @return
     */
    List<Combobox> listCombobox(String storeId);
}
