package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ActRelation implements Serializable{

    private static final long serialVersionUID = 7437991392510320381L;
    
    private String activityId;
    
    private String fatherId;
    
    private String sonId;
 
    public ActRelation() {}
    
    public ActRelation(String activityId,String fatherId,String userId) {
        this.activityId =activityId;
        this.fatherId = fatherId;
        this.sonId = userId;
    }

}
