package com.szhengzhu.provider;

import java.util.Map;

import com.szhengzhu.util.StringUtils;

public class ImageProvider {

    public String goodsSpecImage(Map<String, Object> map) {
        String goodsId = (String) map.get("goodsId");
        Integer serverType = (Integer) map.get("serverType");
        String specIds = (String) map.get("specIds");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.mark_id AS markId,i.image_path AS imagePath,");
        sql.append("i.file_type AS fileType FROM db_goods.t_goods_image g ");
        sql.append("LEFT JOIN t_image_info i ON i.mark_id = g.image_path ");
        sql.append("WHERE g.goods_id = '" + goodsId + "' ");
        sql.append("AND g.server_type = '" + serverType + "' ");
        if (specIds != null && specIds.length() > 0) {
            sql.append("AND FIND_IN_SET('" + specIds + "',g.specification_ids) ");
        }
        sql.append("ORDER BY g.sort limit 1");
        return sql.toString();
    }

    public String selectGoodsSpecImage(Map<String, Object> map) {
        String goodsId = (String) map.get("goodsId");
        String specIds = (String) map.get("specIds");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.mark_id AS markId,i.image_path AS imagePath,");
        sql.append("i.file_type AS fileType FROM db_goods.t_goods_image g ");
        sql.append("LEFT JOIN t_image_info i ON i.mark_id = g.image_path ");
        sql.append("WHERE g.goods_id = '" + goodsId + "' ");
        if (!StringUtils.isEmpty(specIds)) {
            sql.append("AND ( ( g.server_type = 2  AND g.specification_ids = '" + specIds + "' ) ");
            sql.append("OR g.server_type = 0 ) ");
        } else {
            sql.append("AND ( ( g.server_type = 2 AND g.specification_ids IS NULL) ");
            sql.append("OR g.server_type = 0 ) ");
        }
        sql.append("ORDER BY g.specification_ids DESC,g.server_type DESC ,g.sort LIMIT 1");
        return sql.toString();
    }

    public String selectVoucharSpecImage(Map<String, Object> map) {
        String goodsId = (String) map.get("goodsId");
        String specIds = (String) map.get("specIds");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.mark_id AS markId,i.image_path AS imagePath,");
        sql.append("i.file_type AS fileType FROM db_goods.t_goods_image g ");
        sql.append("LEFT JOIN t_image_info i ON i.mark_id = g.image_path ");
        sql.append("WHERE g.goods_id = '" + goodsId + "' ");
        sql.append("AND g.server_type = 2 ");
        sql.append("AND g.specification_ids = '" + specIds + "' ");
        sql.append("ORDER BY g.sort limit 1");
        return sql.toString();
    }

    public String selectMealSmallImage(Map<String, Object> map) {
        String mealId = (String) map.get("mealId");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.mark_id AS markId,i.image_path AS imagePath,");
        sql.append("i.file_type AS fileType FROM db_goods.t_meal_image g ");
        sql.append("LEFT JOIN t_image_info i ON i.mark_id = g.image_path ");
        sql.append("WHERE g.meal_id = '" + mealId + "' ");
        sql.append("AND g.server_type = 0 ");
        sql.append("ORDER BY g.sort limit 1 ");
        return sql.toString();
    }

    public String selectAccessoryImage(Map<String, String> map) {
        String markId = map.get("accessoryId");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.mark_id AS markId,i.image_path AS imagePath,");
        sql.append("i.file_type AS fileType FROM db_goods.t_accessory_info a ");
        sql.append("LEFT JOIN t_image_info i ON i.mark_id = a.image_path ");
        sql.append(" WHERE a.mark_id = '"+markId+"' ");
        return sql.toString();
        
    }
}
