package com.szhengzhu.core;

import com.szhengzhu.util.StringUtils;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class PageParam<T> {

    private Integer pageSize = 10;

    private Integer pageIndex = 1;

    private String sidx;

    /** desc 倒序排序 asc 升序排序 */
    private String sort;

    private T data;

    public String getSidx() {
        if (StringUtils.isEmpty(sidx)) {
            return "mark_id";
        }
        return StringUtils.camelToUnderline(sidx);
    }

    public String getSort() {
        if (StringUtils.isEmpty(sort)) {
            sort = "desc";
        }
        return sort;
    }
}
