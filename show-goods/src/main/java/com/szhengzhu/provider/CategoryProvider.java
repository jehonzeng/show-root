package com.szhengzhu.provider;

import com.szhengzhu.util.StringUtils;

import java.util.Map;

/**
 * @author Administrator
 */
public class CategoryProvider {

    public String selectDownList(Map<String, String> map) {
        String serverStatus = map.get("serverStatus");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT mark_id AS code,name AS name ");
        sql.append("FROM t_category_info WHERE 1=1 ");
        if (!StringUtils.isEmpty(serverStatus)) {
            sql.append("AND server_status = '" + serverStatus + "' ");
        }
        sql.append("order by sort ");
        return sql.toString();
    }

}
