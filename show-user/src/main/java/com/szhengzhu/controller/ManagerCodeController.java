package com.szhengzhu.controller;

import com.szhengzhu.bean.user.ManagerCode;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.ManagerCodeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/managerCode")
public class ManagerCodeController {

	@Resource
	private ManagerCodeService managerCodeService;

	@PostMapping(value = "/page")
	public PageGrid<ManagerCode> page(@RequestBody PageParam<ManagerCode> pageParam) {
		return managerCodeService.page(pageParam);
	}

	@GetMapping(value = "/reflush")
	public void reflush() {
		managerCodeService.reflush();
	}

	@PatchMapping(value = "/modify")
	public void modify(@RequestBody @Validated ManagerCode code) {
		managerCodeService.modify(code);
	}

	@GetMapping(value = "/getByCode")
	public ManagerCode getInfoByCode(@RequestParam("code") @NotBlank String code) {
		return managerCodeService.getInfoByCode(code);
	}
}
