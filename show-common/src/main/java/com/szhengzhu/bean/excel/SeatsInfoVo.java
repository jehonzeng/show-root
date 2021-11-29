package com.szhengzhu.bean.excel;

import java.io.Serializable;

import com.szhengzhu.annotation.Excel;

public class SeatsInfoVo implements Serializable{

	private static final long serialVersionUID = 1290289474109693316L;
	
	@Excel(name = "桌台类型",sort = 1)
	private String seats;
	
	@Excel(name = "使用次数",sort = 2)
	private Integer seatNum;
	
	@Excel(name = "使用人数",sort = 3)
	private Integer peopleNum;
	
	@Excel(name = "平均用餐时长/（分钟）",sort = 4)
	private Integer avgMinute;
	
	@Excel(name = "平均客单价",sort = 5)
	private Integer avgPrice;
	
}
