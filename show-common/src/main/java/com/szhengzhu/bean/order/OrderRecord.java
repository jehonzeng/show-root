package com.szhengzhu.bean.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRecord implements Serializable {
    
    private static final long serialVersionUID = 6448038833957722763L;

    private String markId;

    private String orderNo;

    private String reason;
}