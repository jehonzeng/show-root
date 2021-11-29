package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.IndentRemark;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.IndentRemarkService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * @author makejava
 * @since 2021-03-01 10:46:05
 */
@Validated
@RestController
@RequestMapping("/indent/remark")
public class IndentRemarkController {
    @Resource
    private IndentRemarkService indentRemarkService;

    /**
     * 通过主键查询评论信息
     *
     * @param markId 主键
     * @return 单条评论信息
     */
    @GetMapping(value = "/{markId}")
    public IndentRemark selectOne(@PathVariable("markId") @NotBlank String markId) {
        return indentRemarkService.queryById(markId);
    }

    /**
     * 通过实体作为筛选条件查询并分页
     *
     * @param param 分页对象
     * @return 分页列表
     */
    @PostMapping(value = "/query")
    public PageGrid<IndentRemark> query(@RequestBody PageParam<IndentRemark> param) {
        return indentRemarkService.queryByPage(param);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param indentRemark 实例对象
     * @return 订单评论列表
     */
    @PostMapping(value = "/query/indentId")
    public List<IndentRemark> queryByIndentId(@RequestBody IndentRemark indentRemark) {
        return indentRemarkService.queryByIndentId(indentRemark);
    }

    /**
     * 添加单条数据
     *
     * @param indentRemark 实例对象
     * @return map对象
     */
    @PostMapping(value = "/add")
    public Map<String, Object> add(@RequestBody IndentRemark indentRemark) {
        return indentRemarkService.add(indentRemark);
    }

    /**
     * 修改单条数据
     *
     * @param indentRemark 实例对象
     */
    @PatchMapping(value = "/modify")
    public void modify(@RequestBody IndentRemark indentRemark) {
        indentRemarkService.modify(indentRemark);
    }
}
