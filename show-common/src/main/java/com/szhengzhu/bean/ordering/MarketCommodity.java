package com.szhengzhu.bean.ordering;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MarketCommodity implements Serializable {
    
    private static final long serialVersionUID = -7476341754547942021L;

    private String markId;

    private Integer type;

    private String marketId;

    private String commodityId;

    private Integer sort;
}