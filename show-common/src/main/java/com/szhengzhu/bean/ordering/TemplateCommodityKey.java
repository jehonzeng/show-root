package com.szhengzhu.bean.ordering;

import java.io.Serializable;

import lombok.Data;

@Data
public class TemplateCommodityKey implements Serializable{
    /** */
    private static final long serialVersionUID = -5061582832948854747L;

    private String templateId;

    private String commodityId;

}