package com.szhengzhu.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.szhengzhu.util.StringUtils;

public class UserIntegralProvider {
    
    public String selectUserTotal(Map<String, String> integralMap) {
        
        return new SQL() {
            {
                String nickName = integralMap.get("nickName");
                String phone = integralMap.get("phone");
                StringBuilder WHERE_SQL = new StringBuilder();
                SELECT("user_id, u.nick_name, u.gender, u.phone, SUM(" + 
                        "        IF (integral_limit > 0, integral_limit, 0)) AS total," + 
                        "    SUM(IF (integral_limit < 0, integral_limit, 0)) AS used," + 
                        "    SUM(integral_limit) AS remain ");
                FROM("t_user_integral i LEFT JOIN t_user_info u ON user_id=u.mark_id");
                
                if (!StringUtils.isEmpty(nickName)) {
                    WHERE_SQL.append("AND u.nick_name LIKE CONCAT(CONCAT('%', " + nickName + "), '%') ");
                }
                if (!StringUtils.isEmpty(phone)) {
                    WHERE_SQL.append("AND u.phone = " + phone);
                }
                WHERE(" 1 = 1 " + WHERE_SQL);
                GROUP_BY(" user_id");
            }
        }.toString();
    }
}
