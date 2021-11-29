package com.szhengzhu.provider;

import java.util.Map;

/**
 * @author Administrator
 */
public class JudgeProvider {
    
    public String selectByGoodsId(Map<String ,String> map) {
        String goodsId = map.get("goodsId");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT mark_id AS markId,goods_id AS goodsId,order_id AS orderId,");
        sql.append("server_status AS serverStatus,description AS description,");
        sql.append("commentator AS commentator,add_time AS addTime,star AS star ");
        sql.append("FROM t_goods_judge WHERE goods_id = '"+goodsId +"' ");
        sql.append("ORDER BY sort ASC,add_time DESC ");
        return sql.toString();
    }

}
