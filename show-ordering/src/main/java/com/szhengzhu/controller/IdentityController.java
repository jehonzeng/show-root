package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.Identity;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.IdentityService;
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
@RequestMapping("/identity")
public class IdentityController {

    @Resource
    private IdentityService identityService;

    @PostMapping(value = "/page")
    public PageGrid<Identity> page(@RequestBody PageParam<Identity> pageParam) {
        return identityService.page(pageParam);
    }

    @GetMapping(value = "/{identityId}")
    public Identity getInfo(@PathVariable("identityId") @NotBlank String identityId) {
        return identityService.getInfo(identityId);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated Identity identity) {
        identityService.add(identity);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated Identity identity) {
        identityService.modify(identity);
    }

    @DeleteMapping(value = "/{identityId}")
    public void delete(@PathVariable("identityId") @NotBlank String identityId) {
        identityService.delete(identityId);
    }

    @PostMapping(value = "/employee/{identityId}")
    public void optEmployee(@RequestBody String[] employeeIds, @PathVariable("identityId") @NotBlank String identityId) {
        identityService.optEmployee(employeeIds, identityId);
    }

    @GetMapping(value = "/employee/{identityId}")
    public String[] listEmployee(@PathVariable("identityId") @NotBlank String identityId) {
        return identityService.listEmployee(identityId);
    }

    @GetMapping(value = "/auth")
    public Result<String> getAuth(@RequestParam("employeeId") @NotBlank String employeeId, @RequestParam("storeId") @NotBlank String storeId) {
        return new Result<>(identityService.getAuth(employeeId, storeId));
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listCombobox(@RequestParam("storeId") @NotBlank String storeId) {
        return identityService.listCombobox(storeId);
    }
}
