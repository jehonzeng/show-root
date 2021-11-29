package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.activity.ActivityHistory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActHistoryVo extends ActivityHistory{

    private static final long serialVersionUID = -6151295933780027854L;
    
    private String nickName;//兑奖人昵称
    
    private String giftName;
}
