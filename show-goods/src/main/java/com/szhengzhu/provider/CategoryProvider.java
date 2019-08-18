package com.szhengzhu.provider;

import java.util.Map;

import com.szhengzhu.util.StringUtils;

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
