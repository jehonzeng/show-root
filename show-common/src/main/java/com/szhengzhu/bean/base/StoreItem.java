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
public class StoreItem implements Serializable{

    private static final long serialVersionUID = -6780950487952931410L;

    private String storeId;

    private String userId;

}