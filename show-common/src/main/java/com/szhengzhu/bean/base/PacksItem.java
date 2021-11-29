package com.szhengzhu.bean.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PacksItem implements Serializable{
    
    private static final long serialVersionUID = 6866052609172302642L;

    private String markId;

    private String packsId;

    private String templateId;

    private Boolean serverStatus;

}