package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 会员账户
 * @author Administrator
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAccount implements Serializable {

    private static final long serialVersionUID = -7730199554392915267L;

    private String markId;

    private String accountNo;

    @NotBlank
    private String  userId;

    private String name;

    private Integer gender;

    @NotBlank
    private String phone;

    private Date birthday;

    private BigDecimal totalAmount;

    private Date createTime;

    private Date modifyTime;

    private String gradeId;

    private Integer consumeAmount;
    /**
     * 最后一次充值时间
     */
    private Date time;

    private MemberGrade memberGrade;

    /**
     * 会员等级详情展示
     */
    private List<MemberGradeShow> memberGradeShows;
}
