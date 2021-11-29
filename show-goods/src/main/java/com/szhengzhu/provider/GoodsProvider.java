package com.szhengzhu.provider;

import java.util.Map;

/**
 * @author Administrator
 */
public class GoodsProvider {

    public String selectGoodsImageList(Map<String, String> map) {
        String goodsId = map.get("goodsId");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT mark_id AS markId,goods_id AS goodsId,");
        sql.append("specification_ids AS specificationIds,image_path AS imagePath,");
        sql.append("server_type AS serverType,sort AS sort FROM t_goods_image ");
        sql.append("WHERE goods_id = '" + goodsId + "' ");
        sql.append("ORDER BY server_type , sort ");
        return sql.toString();
    }

    public String selectSpecListById(Map<String, String> map) {
        String markId = map.get("markId");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s.mark_id AS markId,s.attr_name AS attrName,");
        sql.append("s.attr_value AS attrValue FROM t_specification_info s ");
        sql.append("LEFT JOIN t_type_specification t ON s.mark_id=t.specification_id ");
        sql.append("WHERE s.server_status = 1 AND t.type_id IN ");
        sql.append("(SELECT type_id FROM t_goods_info ");
        sql.append("WHERE mark_id = '" + markId + "' ) ");
        sql.append("ORDER BY s.sort ");
        return sql.toString();
    }

    public String selectListNotInColumn() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT g.mark_id AS code,g.goods_name AS name FROM t_goods_info g ");
        sql.append("LEFT JOIN t_column_goods i ON i.goods_id = g.mark_id WHERE NOT EXISTS ( ");
        sql.append("SELECT c.mark_id FROM t_column_info c WHERE c.mark_id = i.column_id ) ");
        sql.append("AND g.server_status IN ('ZT01', 'ZT02') ORDER BY g.sort");
        return sql.toString();
    }

    public String selectListNotInLabel(Map<String, String> map) {
        String labelId = map.get("labelId");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT g.mark_id AS code,g.goods_name AS name ");
        sql.append("FROM t_goods_info g ");
        sql.append("WHERE NOT EXISTS ( SELECT i.mark_id FROM t_label_goods i ");
        sql.append("WHERE i.goods_id = g.mark_id AND i.label_id = '" + labelId + "' ) ");
        sql.append("AND g.server_status IN ('ZT01','ZT02') ORDER BY g.sort ");
        return sql.toString();
    }

    public String selectListNotSpecial() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT mark_id AS code ,goods_name AS `name` FROM t_goods_info g ");
        sql.append("WHERE g.server_status IN ('ZT01','ZT02') AND NOT EXISTS ( ");
        sql.append("SELECT i.mark_id FROM t_special_item i ");
        sql.append("WHERE i.goods_id = g.mark_id ) ORDER BY g.sort ");
        return sql.toString();
    }

    public String selectListNotIcon() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT mark_id AS code ,goods_name AS `name` FROM t_goods_info g ");
        sql.append("WHERE g.server_status IN ('ZT01','ZT02') AND NOT EXISTS ( ");
        sql.append("SELECT i.mark_id FROM t_icon_item i ");
        sql.append("WHERE i.goods_id = g.mark_id ) ORDER BY g.sort ");
        return sql.toString();
    }

    public String selectNameByGoodsId(Map<String, String> map) {
        String goodsId = map.get("goodsId");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT(attr_name) AS attrName ");
        sql.append("FROM t_specification_info s ");
        sql.append("LEFT JOIN t_type_specification t ON t.specification_id = s.mark_id ");
        sql.append("WHERE s.server_status = 1 AND t.type_id IN ( ");
        sql.append("SELECT type_id FROM t_goods_info WHERE mark_id ='" + goodsId + "' )");
        return sql.toString();
    }

    public String selectByCategoryId(Map<String, String> map) {
        String productId = map.get("productId");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE a.`level` WHEN 0 THEN a.`name` WHEN 1 ");
        sql.append("THEN b.`name` WHEN 2 THEN c.`name` ELSE '超限定' END  AS `name` ");
        sql.append("FROM t_category_info a ");
        sql.append("LEFT JOIN t_category_info b ON a.super_id = b.mark_id ");
        sql.append("LEFT JOIN t_category_info c ON b.super_id = c.mark_id ");
        sql.append("WHERE a.mark_id IN (SELECT category_id FROM t_goods_info ");
        sql.append("WHERE mark_id = '" + productId + "')");
        return sql.toString();
    }
}
