package com.szhengzhu.bean.ordering.param;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class IndexParam implements Serializable{

	private static final long serialVersionUID = 1L;

	List<Map<String, Object>> todayTimeSlotIncome;
	
	List<Map<String, Object>> yesterdayTimeSlotIncome;
	
	BigDecimal todayNetReceipts;
	
	BigDecimal yesterdayNetReceipts;
	
}
