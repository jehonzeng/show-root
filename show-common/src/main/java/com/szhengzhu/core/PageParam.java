package com.szhengzhu.core;

import java.io.Serializable;

import com.szhengzhu.util.StringUtils;

import lombok.Data;

@Data
public class PageParam<T> implements Serializable {

    private static final long serialVersionUID = -2379170186595365952L;

    private Integer pageSize = 10;

    private Integer pageIndex = 1;

    private String sidx = "mark_id";

    private String sort = "desc"; // desc 倒序排序 asc 升序排序

    private T data;

    public String getSidx() {
        return StringUtils.camelToUnderline(sidx);
    }
}
