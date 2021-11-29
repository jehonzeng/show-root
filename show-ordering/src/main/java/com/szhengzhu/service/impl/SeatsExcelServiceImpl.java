package com.szhengzhu.service.impl;

import com.szhengzhu.mapper.SeatsExcelMapper;
import com.szhengzhu.service.SeatsExcelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service("seatsExcelService")
public class SeatsExcelServiceImpl implements SeatsExcelService {

	@Resource
	private SeatsExcelMapper seatsExcelMapper;

	@Override
	public List<Map<String, Object>> seatsInfo(Date beginDate, Date lastDate) {
		return seatsExcelMapper.seatsInfo(beginDate, lastDate);
	}

	@Override
	public List<Map<String, Object>> seatsTypeInfo(Date beginDate, Date lastDate) {
		return seatsExcelMapper.seatsTypeInfo(beginDate, lastDate);
	}
}
