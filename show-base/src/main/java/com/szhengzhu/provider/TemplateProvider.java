package com.szhengzhu.provider;

import java.util.Map;

public class TemplateProvider {

    public String selectByMark(Map<String, String> map) {
        String markId = map.get("templateId");
        StringBuilder sql = new StringBuilder();
        sql.append("select mark_id as markId, coupon_name as couponName,");
        sql.append("coupon_total as couponTotal,line_type as lineType, ");
        sql.append("coupon_type as couponType,coupon_price as couponPrice,");
        sql.append("coupon_discount as couponDiscount,limit_price as limitPrice,");
        sql.append("limit_count as limitCount,server_status as serverStatus,");
        sql.append("validity_type as validityType,validity_day as validityDay,");
        sql.append("start_time as startTime, stop_time as stopTime,");
        sql.append("range_type as rangeType, range_id as rangeId,");
        sql.append("CASE WHEN range_type='0' THEN NULL ");
        sql.append("WHEN range_type='1' THEN ");
        sql.append("(SELECT goods_name FROM db_goods.t_goods_info WHERE mark_id = range_id ) ");
        sql.append("WHEN range_type='2' THEN ");
        sql.append("(SELECT name FROM db_goods.t_category_info WHERE mark_id = range_id ) ");
        sql.append("END AS rangeName,");
        sql.append("description as description from t_coupon_template ");
        sql.append("where mark_id = '" + markId + "' ");
        return sql.toString();

    }
}
