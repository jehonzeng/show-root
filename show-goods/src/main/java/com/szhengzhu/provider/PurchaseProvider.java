package com.szhengzhu.provider;

import java.util.List;
import java.util.Map;

import com.szhengzhu.bean.vo.FoodCount;

public class PurchaseProvider {

    public String selectCountInGoods() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.food_id AS foodId,");
        sql.append("SUM(i.use_size * f.purchase_rate * m.quantity) AS totalCount ");
        sql.append("FROM db_order.t_order_info o LEFT JOIN db_order.t_order_item m ");
        sql.append("ON m.order_id = o.mark_id LEFT JOIN t_food_item i ");
        sql.append("ON (i.goods_id = m.product_id AND i.specification_ids = m.specification_ids) ");
        sql.append("LEFT JOIN t_food_info f ON i.food_id = f.mark_id ");
        sql.append("WHERE TO_DAYS(o.delivery_time) = TO_DAYS(NOW()) ");
        sql.append("AND o.order_status IN('OT02','OT03') ");
        sql.append("AND m.product_type = 0 GROUP BY i.food_id ");
        return sql.toString();
    }

    public String selectCountInMeal() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.food_id AS foodId,");
        sql.append("SUM(i.use_size * f.purchase_rate * t.quantity) AS totalCount ");
        sql.append("FROM db_order.t_order_info o ");
        sql.append("LEFT JOIN db_order.t_order_item t ON t.order_id = o.mark_id ");
        sql.append("LEFT JOIN t_meal_item m ON m.meal_id = t.product_id ");
        sql.append("LEFT JOIN t_food_item i ON (i.goods_id = m.goods_id ");
        sql.append("AND i.specification_ids = m.specification_ids) ");
        sql.append("LEFT JOIN t_food_info f ON f.mark_id = i.food_id ");
        sql.append("WHERE TO_DAYS(o.delivery_time)=TO_DAYS(NOW()) ");
        sql.append("AND o.order_status IN('OT02','OT03') ");
        sql.append("AND t.product_type = 2 GROUP BY i.food_id ");
        return sql.toString();
    }

    public String selectCountInAccessory() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.product_id AS foodId,SUM(i.quantity) AS totalCount ");
        sql.append("FROM db_order.t_order_info o ");
        sql.append("LEFT JOIN db_order.t_order_item i ON i.order_id = o.mark_id ");
        sql.append("WHERE TO_DAYS(o.delivery_time) = TO_DAYS(NOW()) ");
        sql.append("AND o.order_status IN('OT02','OT03') ");
        sql.append("AND i.product_type = 3 GROUP BY i.product_id ");
        return sql.toString();
    }

    public String selectCountInGoodsBySeckill() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.food_id AS foodId,");
        sql.append("SUM(i.use_size * f.purchase_rate * o.quantity) AS totalCount ");
        sql.append("FROM db_order.t_seckill_order o ");
        sql.append("LEFT JOIN t_food_item i ON (i.goods_id = o.product_id ");
        sql.append("AND i.specification_ids = o.specification_ids) ");
        sql.append("LEFT JOIN t_food_info f ON i.food_id = f.mark_id ");
        sql.append("WHERE TO_DAYS(o.delivery_time) = TO_DAYS(NOW()) ");
        sql.append("AND o.order_status IN('OT02','OT03') ");
        sql.append("AND o.product_type= 0 GROUP BY i.food_id ");
        return sql.toString();
    }

    public String selectCountInMealBySeckill() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.food_id AS foodId,");
        sql.append("SUM(i.use_size * f.purchase_rate * o.quantity) AS totalCount ");
        sql.append("FROM db_order.t_seckill_order o ");
        sql.append("LEFT JOIN t_meal_item m ON m.meal_id = o.product_id ");
        sql.append("LEFT JOIN t_food_item i ON (i.goods_id = m.goods_id ");
        sql.append("AND i.specification_ids = m.specification_ids) ");
        sql.append("LEFT JOIN t_food_info f ON f.mark_id = i.food_id ");
        sql.append("WHERE TO_DAYS(o.delivery_time) = TO_DAYS(NOW()) ");
        sql.append("AND o.order_status IN('OT02','OT03') ");
        sql.append("AND o.product_type = 2 GROUP BY i.food_id ");
        return sql.toString();
    }

    public String selectCountInGoodsByTeam() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.food_id AS foodId,");
        sql.append("SUM(i.use_size * f.purchase_rate * o.quantity) AS totalCount ");
        sql.append("FROM db_order.t_teambuy_item o ");
        sql.append("LEFT JOIN t_food_item i ON (i.goods_id = o.product_id ");
        sql.append("AND i.specification_ids = o.specification_ids) ");
        sql.append("LEFT JOIN t_food_info f ON i.food_id = f.mark_id ");
        sql.append("WHERE TO_DAYS(o.delivery_time) = TO_DAYS(NOW()) ");
        sql.append("AND o.order_status IN('OT02','OT03') ");
        sql.append("AND o.product_type= 0 GROUP BY i.food_id ");
        return sql.toString();
    }

    public String selectCountInMealByTeam() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT i.food_id as foodId,");
        sql.append("SUM(i.use_size * f.purchase_rate * o.quantity) AS totalCount ");
        sql.append("FROM db_order.t_teambuy_item o ");
        sql.append("LEFT JOIN t_meal_item m ON m.meal_id = o.product_id ");
        sql.append("LEFT JOIN t_food_item i ON (i.goods_id = m.goods_id ");
        sql.append("AND i.specification_ids = m.specification_ids) ");
        sql.append("LEFT JOIN t_food_info f ON i.food_id = f.mark_id ");
        sql.append("WHERE TO_DAYS(o.delivery_time) = TO_DAYS(NOW()) ");
        sql.append("AND o.order_status IN('OT02','OT03') ");
        sql.append("AND o.product_type= 2 GROUP BY i.food_id ");
        return sql.toString();
    }

    public String updateBatch(Map<String, List<FoodCount>> map) {
        List<FoodCount> foodCounts = map.get("list");
        FoodCount food;
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE t_purchase_info SET ");
        if (foodCounts.size() > 0) {
            for (int i = 0, len = foodCounts.size(); i < len; i++) {
                food = foodCounts.get(i);
                sql.append("purchase_total = CASE ");
                sql.append("WHEN (food_id = '" + food.getFoodId()
                        + "' AND TO_DAYS(buy_time) = TO_DAYS(now())) ");
                sql.append("THEN purchase_total = '" + food.getTotalCount() + "' ");
                sql.append("END, ");
                sql.append("reflash_time = CASE ");
                sql.append("WHEN (food_id = '" + food.getFoodId()
                        + "' AND TO_DAYS(buy_time) = TO_DAYS(now())) ");
                sql.append("THEN reflash_time = NOW() ");
                sql.append("END");
            }
        }
        return sql.toString();
    }
}
