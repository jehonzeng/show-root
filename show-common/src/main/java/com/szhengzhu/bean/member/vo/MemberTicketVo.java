package com.szhengzhu.bean.member.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.szhengzhu.bean.ordering.vo.UserTicketVo;

import lombok.Data;

@Data
public class MemberTicketVo implements Serializable {

    private static final long serialVersionUID = 2475690685765185806L;

    private String accountId;
    
    private String userId;
    
    private String accountNo;
    
    private String name;
    
    private Integer gender;
    
    private String phone;

    private Date birthday;

    private BigDecimal memberTotal;
    
    private Integer integralTotal;
    
    private List<UserTicketVo> ticketList;
}
