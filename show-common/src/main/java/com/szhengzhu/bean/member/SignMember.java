package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @since 2021-06-07 16:15:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignMember implements Serializable {
    private static final long serialVersionUID = -38157455181716652L;

    /* 主键 */
    private String markId;

    /* 用户id */
    @NotBlank
    private String userId;

    /* 年份 */
    private Integer year;

    /* 月份 */
    private Integer month;

    /* 连续签到数 */
    private Integer continueSign;

    /* 签到 */
    private String sign;

    /* 签到时间（最后签到时间） */
    private Date signTime;

    /* 上月连续签到天数 */
    private Integer lastMonthNum;

    /* 签到时间列表 */
//    private Map<Integer, String> signMap;

//    /* 签到详细信息 */
//    private List<SignDetail> signDetailList;

//    public Map<Integer, String> getSignMap() {
//        String[] data = this.sign.split("");
//        Map<Integer, String> map = new HashMap<>();
//        for (int i = 0; i < data.length; i++) {
//            map.put(i + 1, data[i]);
//        }
//        return map;
//    }
}
