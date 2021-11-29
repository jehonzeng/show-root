package com.szhengzhu.code;

public interface DishStatus {
    //（0：已失效  1：成长中 2：可领取券  3：已领取券 ）
    Integer BE_INVALID = 0;
    
    Integer MATURE = 1;

    Integer RECEIVE_TICKET = 2;

    Integer RECEIVED_TICKET = 3;
}
