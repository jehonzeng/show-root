package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.GoodsJudge;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsJudgeVo extends GoodsJudge {

    private static final long serialVersionUID = 1945277499780755531L;
    
    private String specList;
    
    private String contact;//联系人
    
    private String phone;
    
    private String deliveryArea;
    
    private String deliveryAddress;

}
