package com.szhengzhu.provider;

import java.util.Map;

public class UserProvider {

    public String selectListByCode(Map<String, String> map) {
        String roleCode = map.get("roleCode");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.mark_id AS code,u.nick_name AS name FROM t_user_info u ");
        sql.append("LEFT JOIN t_user_role r ON u.mark_id = r.user_id ");
        sql.append("LEFT JOIN t_role_info i ON  r.role_id = i.mark_id ");
        sql.append("WHERE u.wechat_status = 1 AND i.server_status = 1 ");
        sql.append("AND i.role_code = '" + roleCode + "' ");
        return sql.toString();
    }

    public String selectUsersByRoleId(Map<String, String> map) {
        String roleId = map.get("roleId");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.mark_id AS markId,");
        sql.append("u.nick_name AS nickName,u.phone AS phone,");
        sql.append("u.wopen_id AS wopenId,u.xopen_id AS xopenId,");
        sql.append("u.union_id AS unionId,r.role_name AS roleName,");
        sql.append("u.wechat_status AS wechatStatus,");
        sql.append("u.user_level AS userLevel FROM t_user_info u ");
        sql.append("LEFT JOIN t_user_role ur ON u.mark_id = ur.user_id ");
        sql.append("LEFT JOIN t_role_info r ON r.mark_id = ur.role_id ");
        sql.append("WHERE r.server_status = 1 AND u.wechat_status = 1 ");
        sql.append("AND ur.role_id = '" + roleId + "' ");
        return sql.toString();
    }
}
