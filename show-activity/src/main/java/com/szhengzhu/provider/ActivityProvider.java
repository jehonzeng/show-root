package com.szhengzhu.provider;

import cn.hutool.core.date.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Map;

public class ActivityProvider {

    public String selectHistoryByType(Map<String, Object> map) {
        String activityId = (String) map.get("activityId");
        String actGiftId = (String) map.get("actGiftId");
        String userId = (String) map.get("userId");
        String type = (String) map.get("type");
        StringBuilder sql;
        sql = new StringBuilder("SELECT COUNT(*) FROM t_activity_history ");
        sql.append("WHERE activity_id = '");
        sql.append(activityId);
        sql.append("' ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 每人
        if (type.equals("AR01")) {
            sql.append("and act_gift_id = '");
            sql.append(actGiftId);
            sql.append("' ");
        }
        // 每天
        if (type.equals("AR02")) {
            String time = DateUtil.today();
            sql.append("and add_time >= '");
            sql.append(time);
            sql.append(" 00:00:00' and add_time <= '");
            sql.append(time);
            sql.append(" 23:59:59' ");
        }
//        //每周
//        if (type.equals("AR04")) {
//        sql.append("and add_time >= '");
//        calendar.setFirstDayOfWeek(Calendar.MONDAY);
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//        sql.append(sdf.format(calendar.getTime()));
//        sql.append(" 00:00:00' and add_time <= '");
//        calendar = TimeUtils.calendar();
//        calendar.setFirstDayOfWeek(Calendar.MONDAY);
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//        sql.append(sdf.format(calendar.getTime()));
//        sql.append(" 23:59:59' ");
//        }
//        //每月
//        if (type.equals("AR05")) {
//        sql.append("and add_time >= '");
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        sql.append(sdf.format(calendar.getTime()));
//        sql.append(" 00:00:00' and add_time <= '");
//        calendar.add(Calendar.MONTH, 1);
//        calendar.set(Calendar.DAY_OF_MONTH, 0);
//        sql.append(sdf.format(calendar.getTime()));
//        sql.append(" 23:59:59' ");
//        }
        sql.append("and user_id = '" + userId + "' ");
        return sql.toString();
    }
    
    public String selectRelationByType(Map<String, Object> map) {
        String activityId = (String) map.get("activityId");
        String sonId = (String) map.get("sonId");
        String limited = (String) map.get("limited");
        StringBuilder sql;
        sql = new StringBuilder("SELECT COUNT(*) FROM t_participant_relation ");
        sql.append("WHERE activity_id = '");
        sql.append(activityId);
        sql.append("' ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (limited.equals("ZL02")) {
            String time = DateUtil.today();
            sql.append("and add_time >= '");
            sql.append(time);
            sql.append(" 00:00:00' and add_time <= '");
            sql.append(time);
            sql.append(" 23:59:59' ");
        }
        sql.append("and son_id = '");
        sql.append(sonId);
        sql.append("' ");
        return sql.toString();
    }
}
