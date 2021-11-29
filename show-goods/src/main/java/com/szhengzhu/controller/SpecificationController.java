package com.szhengzhu.controller;

import cn.hutool.core.util.StrUtil;
import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.SpecBatchVo;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.SpecificationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "specifications")
public class SpecificationController {

    @Resource
    private SpecificationService specificationService;

    @PostMapping(value = "/add")
    public SpecificationInfo addSpecification(@RequestBody @Validated SpecificationInfo base) {
        return specificationService.addSpecification(base);
    }

    @PatchMapping(value = "/modify")
    public SpecificationInfo modifySpecification(@RequestBody @Validated SpecificationInfo base) {
        return specificationService.modifySpecification(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<SpecificationInfo> getSpecificationPage(
            @RequestBody PageParam<SpecificationInfo> base) {
        return specificationService.getPage(base);
    }
    
    @GetMapping(value = "/list")
    public List<SpecChooseBox> listSpecification(@RequestParam("goodsId") @NotBlank String goodsId) {
        return specificationService.getSpecList(goodsId);
    }
    
    @PostMapping(value = "/addBatch")
    public SpecBatchVo addBatchSpecification(@RequestBody SpecBatchVo base) {
        return specificationService.insertBatchSpec(base);
    }
    
    @PostMapping(value = "/page/in")
    public PageGrid<SpecificationInfo> pageInByType(@RequestBody PageParam<SpecificationInfo> base) {
        ShowAssert.checkTrue(base.getData() == null
                || StrUtil.isEmpty(base.getData().getTypeId()), StatusCode._4004);
        return specificationService.pageInByType(base);
    }
    
    @PostMapping(value = "/page/notin")
    public PageGrid<SpecificationInfo> pageSpecNotInByType(@RequestBody PageParam<SpecificationInfo> base) {
        ShowAssert.checkTrue(base.getData() == null
                || StrUtil.isEmpty(base.getData().getTypeId()), StatusCode._4004);
        return specificationService.pageNotInByType(base);
    }
}
