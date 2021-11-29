package com.szhengzhu.bean.member.param;

import com.szhengzhu.bean.member.SignDetail;
import com.szhengzhu.core.Contacts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

/**
 * @author makejava
 * @since 2021-06-07 16:15:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInfoParam implements Serializable {
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

    /* 签到时间列表 */
    private List<String> signList;

    /* 签到时间（最后一次签到） */
    private Date signTime;

    /* 签到领取详细信息 */
    private List<SignDetail> signDetailList;

    /* 上月连续签到天数 */
    private Integer lastMonthNum;

    public List<String> getSignList() {
        String[] data = this.sign.split("");
        List<String> list = new LinkedList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals(Contacts.SIGN_IN)) {
                list.add(month + "-" + (i + 1));
            }
        }
        return list;
    }

//    public Integer getLastMonthNum() {
//        String[] data = this.sign.split("");
//        Integer lastMonthNum = 0;
//        for (int i = 0; i < data.length; i++) {
//            if (data[i].equals(Contacts.SIGN_IN)) {
//                lastMonthNum += 1;
//            } else {
//                lastMonthNum = 0;
//            }
//        }
//        return lastMonthNum;
//    }
}
