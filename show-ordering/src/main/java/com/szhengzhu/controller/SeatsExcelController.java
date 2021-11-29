package com.szhengzhu.controller;

import com.szhengzhu.service.SeatsExcelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/excel")
public class SeatsExcelController {

	@Resource
	private SeatsExcelService seatsExcelService;

	/**
	 * 查询日期之间的不同座位类型的座位数
	 *
	 * @param beginDate 开始日期
	 * @param lastDate  结束日期
	 * @return
	 */
	@GetMapping(value = "/seats/info")
	public List<Map<String, Object>> seatsInfo(@RequestParam("beginDate") Date beginDate,
											   @RequestParam("lastDate") Date lastDate) {
		return seatsExcelService.seatsInfo(beginDate, lastDate);
	}

	/**
	 * 查询日期之间的不同座位类型的情况
	 *
	 * @param beginDate 开始日期
	 * @param lastDate  结束日期
	 * @return
	 */
	@GetMapping(value = "/seats/typeInfo")
	public List<Map<String, Object>> seatsTypeInfo(@RequestParam("beginDate") Date beginDate,
			@RequestParam("lastDate") Date lastDate) {
		return seatsExcelService.seatsTypeInfo(beginDate, lastDate);
	}
}
