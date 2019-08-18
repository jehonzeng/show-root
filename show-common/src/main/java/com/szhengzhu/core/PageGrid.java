package com.szhengzhu.core;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageGrid<T> implements Serializable {

    private static final long serialVersionUID = -5570925668945804978L;

    // 总记录数
    private Long total = 0L;

    // 当前页
    private Integer pageIndex = 1;

    // 总页数
    private Integer records = 0;
    
    // 每页的数量
    private Integer pageSize;

    // 结果集
    private List<T> rows;

    public PageGrid(PageInfo<T> temp) {
        this.total = temp.getTotal();
        this.pageIndex = temp.getPageNum();
        this.records = temp.getPages();
        this.pageSize = temp.getPageSize();
        this.rows = temp.getList();
    }

}
